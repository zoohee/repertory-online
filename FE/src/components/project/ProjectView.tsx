import {
  ChangeEvent,
  useEffect,
  // SetStateAction,
  // MutableRefObject,
  useRef,
  useState,
} from 'react';
import styled from 'styled-components';
import { formatMilliSecondsToTimeString } from '@/util';
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import PauseIcon from '@mui/icons-material/Pause';
import FileUploadIcon from '@mui/icons-material/FileUpload';
import SaveIcon from '@mui/icons-material/Save';
import { Dialog } from './Dialog';
import ImageSquare from '../common/ImageSquare';
import * as dance from '@/services/dance';
import * as project from '@/services/project';
// import { Title } from './Title';
const Tmp = styled.div`
  width: 80%;
  display: flex;
  justify-content: space-between;
`;
const FlexWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const PleaseUploadFile = styled.div`
  margin-top: 20%;
  margin-bottom: -22%;
  height: auto;
  font-size: 1.7rem;
  position: relative;
  display: flex;
  text-align: center;
  justify-content: center;
  width: 100%;
`;
const ClearButton = styled.button<{ isDisabled: boolean }>`
  color: ${(props) => (props.isDisabled ? 'grey' : 'white')};
`;

const TRButton = styled.button<{ isDisabled: boolean }>`
  color: ${(props) => (props.isDisabled ? 'grey' : 'white')};
`;

const Title = styled.div`
  margin-top: -6px;
  height: 2.2rem;
  width: 100%;
  padding: 1.5rem;
  font-size: 1.8rem;
  color: white;
  text-align: left;
  display: flex;
  align-items: center;
  border-bottom: 0.6rem solid var(--background-color);
`;
const TitleName = styled.div`
  width: 100%;
  margin-bottom: 10px;
`;
const HiddenInput = styled.input`
  display: none;
`;
const TitleButton = styled.button`
  margin-top: 0.15rem;
  margin-left: 1.2rem;
  margin-right: 0.3rem;
  background: transparent;
  font-size: 1rem;
  &:hover {
    /* border-color: #9a9a9a; */
    color: #9a9a9a;
  }
  margin-bottom: 10px;
`;
const StyledVideo = styled.video`
  height: 70%;
  width: 100%;
`;
const StyledSlider = styled.input<SliderProps>`
  width: 95%;
  height: 100%;
  background: #ffffff;
  border-radius: 0.5rem;
  overflow: hidden;
  outline: none;
  -webkit-appearance: none;
  appearance: none;

  &::-webkit-slider-runnable-track {
    width: 100%;
    /* height: 30px; */
    cursor: pointer;
    border-radius: 1.3px;
  }
  &::-webkit-slider-thumb {
    height: 36px;
    width: 4px;
    border-radius: 6px;
    background: #515151;
    cursor: pointer;
    -webkit-appearance: none;
  }
  &::-moz-range-track {
    width: 100%;
    height: 36px;
    width: 4px;
    cursor: pointer;
    box-shadow: 1px 1px 1px #000000, 0px 0px 1px #0d0d0d;
    border-radius: 1.3px;
    border: 0.2px solid #010101;
  }
  &::-moz-range-thumb {
    box-shadow: 1px 1px 1px #000000, 0px 0px 1px #0d0d0d;
    border: 1px solid #000000;
    height: 36px;
    width: 4px;

    border-radius: 1.3px;
    background: #ffffff;
    cursor: pointer;
  }

  position: relative; // 가상 요소의 위치를 기준으로 설정

  &::before,
  &::after {
    content: ''; // 가상 요소의 내용을 비움
    position: absolute; // 가상 요소를 절대 위치로 설정
    top: 0;
    bottom: 0;
    width: 4px; // 세로선의 너비를 2px로 설정
  }

  &::before {
    left: ${(props) =>
      (props.startTime / props.duration) *
      100}%; // 시작 시간에 해당하는 위치로 설정
    background-color: #0000a7;
  }

  &::after {
    left: ${(props) =>
      (props.endTime / props.duration) *
      100}%; // 종료 시간에 해당하는 위치로 설정세로선의 색상을 파란색으로 설정
    background-color: #ff6161;
  }
`;
interface ITrimSection {
  start: number;
  end: number;
}
interface SliderProps {
  startTime: number;
  endTime: number;
  duration: number;
}

const StartTimeButton = styled.button<{ isActive: boolean }>`
  color: ${(props) => (props.isActive ? '#a7a7ff' : 'white')};
  &:hover {
    cursor: pointer;
  }
`;

const EndTimeButton = styled.button<{ isActive: boolean }>`
  color: ${(props) => (props.isActive ? '#ff9797' : 'white')};
  &:hover {
    cursor: pointer;
  }
`;
const Time = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 10px;
  padding-right: 24px;
  padding-left: 24px;
`;
const ProjectViewWrapper = styled.div`
  /* background-color: red; */
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 73vh;
`;
interface Props {
  setVideo: React.Dispatch<React.SetStateAction<File | null>>;
  videoRef: React.RefObject<HTMLVideoElement>;
  trimVideo: (trimSection: ITrimSection) => Promise<void>;
}
const ProjectView = (props: Props) => {
  const [currentTime, setCurrentTime] = useState(0);
  const [duration, setDuration] = useState(0);
  const [startTime, setStartTime] = useState(0);
  const [endTime, setEndTime] = useState(0);
  const [isPlaying, setIsPlaying] = useState(true);
  const [open, setOpen] = useState(false);
  const fileInput = useRef<HTMLInputElement>(null);
  const [images, setImages] = useState({ start: '', middle: '', end: '' });
  const [imageFiles, setImageFiles] = useState({
    start: File,
    middle: File,
    end: File,
  });

  const [startPose, setStartPose] = useState('');
  const [endPose, setEndPose] = useState('');
  // const [video, setVideo] = useState<File | null>(null);
  useEffect(() => {
    const video = props.videoRef.current;

    const handlePlay = () => {
      setIsPlaying(true);
    };

    const handlePause = () => {
      setIsPlaying(false);
    };

    if (video) {
      video.addEventListener('play', handlePlay);
      video.addEventListener('pause', handlePause);
    }

    return () => {
      if (video) {
        video.removeEventListener('play', handlePlay);
        video.removeEventListener('pause', handlePause);
      }
    };
  }, [props.videoRef]);
  const handleTimeChange = (event: ChangeEvent<HTMLInputElement>) => {
    const time = Number(event.target.value);

    setCurrentTime(time);
    console.log(time);

    if (props.videoRef.current) {
      props.videoRef.current.currentTime = time;
    }
  };

  const handleTimeUpdate = () => {
    if (props.videoRef.current) {
      setCurrentTime(props.videoRef.current.currentTime);
    }
  };

  const handleDurationChange = () => {
    if (props.videoRef.current) {
      setDuration(props.videoRef.current.duration);
    }
  };

  const handleButtonClick = () => {
    console.log('btn click');
    if (fileInput.current) fileInput.current.click();
  };

  const playVideo = () => {
    setIsPlaying(true);
    if (props.videoRef.current) {
      props.videoRef.current.play();
    }
  };

  const pauseVideo = () => {
    setIsPlaying(false);
    if (props.videoRef.current) {
      props.videoRef.current.pause();
    }
  };

  const handleFileUpload = (event: ChangeEvent<HTMLInputElement>) => {
    pauseVideo();
    if (props.videoRef.current) {
      props.videoRef.current.src = '';
      props.setVideo(null);
      props.videoRef.current.load();
    }
    console.log('upload');
    if (event.target.files) {
      const file = event.target.files[0];
      props.setVideo(file);
      if (props.videoRef.current) {
        props.videoRef.current.src = URL.createObjectURL(file);
      }
    }
  };

  const captureImage = async (
    video: HTMLVideoElement,
    time: number
  ): Promise<File> => {
    video.currentTime = time;
    await new Promise((r) => setTimeout(r, 200));

    const canvas = document.createElement('canvas');
    canvas.width = video.videoWidth;
    canvas.height = video.videoHeight;

    const ctx = canvas.getContext('2d');
    if (ctx) {
      ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
    }

    const dataUrl = canvas.toDataURL('image/jpeg');
    const data = atob(dataUrl.split(',')[1]);
    const mime = dataUrl.split(';')[0].split(':')[1];
    const buf = new ArrayBuffer(data.length);
    const arr = new Uint8Array(buf);
    for (let i = 0; i < data.length; i++) {
      arr[i] = data.charCodeAt(i);
    }
    const blob = new Blob([arr], { type: mime });
    const file = new File([blob], 'capture.jpg', { type: 'image/jpeg' });

    return file;
  };

  const saveImages = async (videoRef: React.RefObject<HTMLVideoElement>) => {
    if (videoRef.current) {
      const video = videoRef.current;
      const duration = video.duration;
      const startTime = 0;
      const middleTime = duration / 2;
      const endTime = duration;

      const startImage = await captureImage(video, startTime);
      const middleImage = await captureImage(video, middleTime);
      const endImage = await captureImage(video, endTime);

      console.log(startImage, middleImage, endImage);
      setImages({
        start: URL.createObjectURL(startImage),
        middle: URL.createObjectURL(middleImage),
        end: URL.createObjectURL(endImage),
      });

      setImageFiles({
        start: imageFiles.start,
        middle: imageFiles.middle,
        end: imageFiles.end,
      });
      setOpen(true);
    }
  };

  const UploadSource = () => {
    const data = {
      sourceName: 'MySource',
      sourceLength: duration,
      tagNameList: ['tag1', 'tag2'],
      start: 'string',
      end: 'string',
    };
    const formData = new FormData();
    if (props.videoRef.current) {
      formData.append('sourceThumbnail', images.start);
      formData.append('sourceVideo', props.videoRef.current.src);
    }

    formData.append(
      'postSource',
      new Blob([JSON.stringify(data)], { type: 'application/json' })
    );
    dance.postSource(formData);
  };

  const DetectPose = () => {
    project
      .detectPose(
        new File([images.start], 'start.jpeg', {
          type: 'text/plain',
          lastModified: Date.now(),
        })
      )
      .then((res) => {
        setStartPose(res.data);
      });
    project
      .detectPose(
        new File([images.end], 'end.jpeg', {
          type: 'text/plain',
          lastModified: Date.now(),
        })
      )
      .then((res) => {
        setEndPose(res.data);
      });
  };

  // TRButton 클릭 이벤트
  const onTRButtonClick = () => {
    saveImages(props.videoRef);
    DetectPose();
  };
  return (
    <>
      <HiddenInput
        type='file'
        onChange={(e) => handleFileUpload(e)}
        ref={fileInput}
      />
      <ProjectViewWrapper>
        <Title>
          <TitleName>Project</TitleName>
          <TitleButton type='button' onClick={handleButtonClick}>
            <FileUploadIcon />
          </TitleButton>
          <TitleButton
            type='button'
            onClick={() => {
              if (props.videoRef.current === null) {
                alert('Upload Video First!');
              } else {
                setOpen(true);
              }
            }}
          >
            <SaveIcon />
          </TitleButton>
          <Dialog open={open} onClose={() => setOpen(false)}>
            <p>Save Source</p>
            <FlexWrapper>
              <ImageSquare src={images.start} size={140} />
              <ImageSquare src={images.middle} size={140} />
              <ImageSquare src={images.end} size={140} />
            </FlexWrapper>
            <input type='text' placeholder='Name' value='MySource' />
            <input type='text' placeholder='Length' value={duration} />
            <input type='text' placeholder='Start' value={startPose} />
            <input type='text' placeholder='End' value={endPose} />
            <FlexWrapper>
              <button onClick={() => UploadSource()}>Close</button>
              <button onClick={() => setOpen(false)}>Upload</button>
            </FlexWrapper>
          </Dialog>
        </Title>
        {props.videoRef.current?.src === '' && (
          <PleaseUploadFile>Upload File First</PleaseUploadFile>
        )}
        <StyledVideo
          ref={props.videoRef}
          // autoPlay
          // controls
          onLoadedData={(event) => event.currentTarget.play()}
          onTimeUpdate={handleTimeUpdate} // 비디오 재생 시간이 변경될 때마다 호출
          onLoadedMetadata={handleDurationChange} // 비디오 메타데이터가 로드되면 호출
        ></StyledVideo>

        <StyledSlider
          type='range'
          min='0'
          step='0.1'
          max={duration}
          value={currentTime}
          onChange={handleTimeChange}
          startTime={startTime} // startTime 상태를 전달
          endTime={endTime}
          duration={duration} // endTime 상태를 전달
        />
        <Time>
          <p>{formatMilliSecondsToTimeString(currentTime * 1000, 'minute')}</p>

          <p>{formatMilliSecondsToTimeString(duration * 1000, 'minute')}</p>
        </Time>

        <Tmp>
          <button onClick={onTRButtonClick}>Ready</button>
          <StartTimeButton
            isActive={startTime > 0}
            onClick={() => {
              setStartTime(currentTime);
              pauseVideo();
            }}
          >
            Start
          </StartTimeButton>

          <EndTimeButton
            isActive={endTime > 0}
            onClick={() => {
              setEndTime(currentTime);
              pauseVideo();
            }}
          >
            End
          </EndTimeButton>
          <button onClick={isPlaying ? pauseVideo : playVideo}>
            {isPlaying ? <PauseIcon /> : <PlayArrowIcon />}
          </button>
          <ClearButton
            isDisabled={startTime === 0 && endTime === 0}
            onClick={() => {
              setStartTime(0);
              setEndTime(0);
              console.log(endTime);
              console.log(endTime / duration);
            }}
            disabled={startTime === 0 && endTime === 0}
          >
            Clear
          </ClearButton>

          <TRButton
            isDisabled={startTime === 0 || endTime === 0}
            onClick={() => {
              props.trimVideo({
                start: startTime * 1000,
                end: endTime * 1000,
              });
              setStartTime(0);
              setEndTime(0);
              pauseVideo();
            }}
            disabled={startTime === 0 || endTime === 0}
          >
            TRIM
          </TRButton>
        </Tmp>
      </ProjectViewWrapper>
    </>
  );
};

export default ProjectView;
