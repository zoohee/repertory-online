import {
  ChangeEvent,
  // SetStateAction,
  // MutableRefObject,
  useRef,
  useState,
} from 'react';
import styled from 'styled-components';
import { formatMilliSecondsToTimeString } from '@/util';
// import { Title } from './Title';
const Title = styled.div`
  height: auto;
  width: 100%;
  padding: 1.5rem;
  font-size: 1.8rem;
  color: white;
  text-align: left;
  display: flex;
  border-bottom: 0.6rem solid var(--background-color);
`;
const TitleName = styled.div`
  width: 100%;
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
`;
const StyledVideo = styled.video`
  height: 70%;
  width: 100%;
`;
const StyledSlider = styled.input`
  width: 100%;
  height: 16%px;
  background: #ddd;
  outline: none;
  -webkit-appearance: none;

  &::-webkit-slider-runnable-track {
    width: 100%;
    height: 30px;
    cursor: pointer;
    animate: 0.2s;
    /* box-shadow: 1px 1px 1px #000000, 0px 0px 1px #0d0d0d; */
    /* background: #3071a9; */
    border-radius: 1.3px;
  }
  &::-webkit-slider-thumb {
    height: 30px;
    width: 2px;
    border-radius: 3px;
    background: black;
    cursor: pointer;
    -webkit-appearance: none;
    margin-top: 0px;
  }
  /* &:focus::-webkit-slider-runnable-track {
    background: #367ebd;
  } */
  &::-moz-range-track {
    width: 100%;
    height: 10px;
    cursor: pointer;
    animate: 0.2s;
    box-shadow: 1px 1px 1px #000000, 0px 0px 1px #0d0d0d;
    background: #3071a9;
    border-radius: 1.3px;
    border: 0.2px solid #010101;
  }
  &::-moz-range-thumb {
    box-shadow: 1px 1px 1px #000000, 0px 0px 1px #0d0d0d;
    border: 1px solid #000000;
    height: 10px;
    width: 10px;
    border-radius: 3px;
    background: #ffffff;
    cursor: pointer;
  }
`;
interface ITrimSection {
  start: number;
  end: number;
}

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
  height: 60vh;
`;
// const TitleWrapper = styled.div`
//   width: 100%;
//   display: flex;
// `;
const UploadZone = styled.div``;
// const VideoWrapper = styled.div`
//   z-index: 3000;
//   background-color: red;
//   width: 100%;
//   height: 100%; // 부모 컴포넌트의 높이에 맞춤
//   display: flex;
//   align-items: center; // 수직 정렬
//   justify-content: center; // 수평 정렬
// `;

interface Props {
  setVideo: React.Dispatch<React.SetStateAction<File | null>>;
  videoRef: React.RefObject<HTMLVideoElement>;
  trimVideo: (trimSection: ITrimSection) => Promise<void>;
}
const TmpTimeLine = styled.div`
  display: flex;
  gap: 10px;
`;
const ProjectView = (props: Props) => {
  const [currentTime, setCurrentTime] = useState(0);
  const [duration, setDuration] = useState(0);
  const fileInput = useRef<HTMLInputElement>(null);

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

  const handleGoTo = (seconds: number) => {
    if (props.videoRef.current) {
      props.videoRef.current.currentTime = seconds;
    }
  };

  const handleFileUpload = (event: ChangeEvent<HTMLInputElement>) => {
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
            Upload
          </UploadButton>
        </Title>
        <StyledVideo
          ref={props.videoRef}
          autoPlay
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
        />
        <Time>
          <p>{formatMilliSecondsToTimeString(currentTime * 1000, 'minute')}</p>
          <p>{formatMilliSecondsToTimeString(duration * 1000, 'minute')}</p>
        </Time>
      </ProjectViewWrapper>

      <button onClick={() => props.trimVideo({ start: 3000, end: 11000 })}>
        TR
      </button>
    </>
  );
};

export default ProjectView;
