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
// import { Title } from './Title';
const Tmp = styled.div`
  width: 80%;
  display: flex;
  justify-content: space-between;
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
const UploadButton = styled.button`
  margin-top: 0.15rem;
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
  width: 100%;
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
    height: 60px;
    width: 4px;
    border-radius: 5px;
    background: #515151;
    cursor: pointer;
    -webkit-appearance: none;
  }
  &::-moz-range-track {
    width: 100%;
    height: 30px;
    cursor: pointer;
    box-shadow: 1px 1px 1px #000000, 0px 0px 1px #0d0d0d;
    border-radius: 1.3px;
    border: 0.2px solid #010101;
  }
  &::-moz-range-thumb {
    box-shadow: 1px 1px 1px #000000, 0px 0px 1px #0d0d0d;
    border: 1px solid #000000;
    height: 30px;
    width: 10px;
    border-radius: 3px;
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
const Tmp2 = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
`;
const Time = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 4px;
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
  const fileInput = useRef<HTMLInputElement>(null);

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
  // const handleGoTo = (seconds: number) => {
  //   if (props.videoRef.current) {
  //     props.videoRef.current.currentTime = seconds;
  //   }
  // };

  const handleFileUpload = (event: ChangeEvent<HTMLInputElement>) => {
    pauseVideo();
    if (props.videoRef.current) {
      props.videoRef.current.src = '';
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
          <UploadButton type='button' onClick={handleButtonClick}>
            <FileUploadIcon />
          </UploadButton>
        </Title>
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
            TR
          </TRButton>
        </Tmp>
      </ProjectViewWrapper>
    </>
  );
};

export default ProjectView;
