import {
  ChangeEvent,
  // SetStateAction,
  // MutableRefObject,
  useRef,
} from 'react';
import styled from 'styled-components';
import { Title } from './Title';
const HiddenInput = styled.input`
  display: none;
`;
const UploadButton = styled.button`
  background: transparent;
  width: 300px;
  height: 200px;
  border: 4px dotted white;
  font-size: 3rem;

  &:hover {
    /* border-color: #9a9a9a; */
    color: #9a9a9a;
  }
`;
interface ITrimSection {
  start: number;
  end: number;
}
const ProjectViewWrapper = styled.div`
  display: flex;
  align-items: center;
  height: 60vh;
`;
const UploadZone = styled.div``;
const VideoWrapper = styled.div`
  height: 80%; // 부모 컴포넌트의 높이에 맞춤
  display: flex;
  align-items: center; // 수직 정렬
  justify-content: center; // 수평 정렬
`;

const StyledVideo = styled.video`
  max-height: 100%; // VideoWrapper의 높이를 넘지 않도록
  width: auto; // 원본 비디오의 비율 유지
`;
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
  const fileInput = useRef<HTMLInputElement>(null);

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
        console.log('initial ref');
        props.videoRef.current.srcObject = new Blob([file], {
          type: file.type,
        });
      }
    }
  };
  return (
    <>
      <Title title={'Project'} />
      <HiddenInput type='file' onChange={handleFileUpload} ref={fileInput} />
      <ProjectViewWrapper>
        {props.videoRef.current ? (
          <>
            <UploadZone>
              <UploadButton type='button' onClick={handleButtonClick}>
                +
              </UploadButton>
            </UploadZone>
          </>
        ) : (
          <>
            <VideoWrapper>
              <StyledVideo
                ref={props.videoRef}
                autoPlay
                controls
                onLoadedData={(event) => event.currentTarget.play()}
              ></StyledVideo>
            </VideoWrapper>
            <button
              onClick={() => props.trimVideo({ start: 10000, end: 11000 })}
            >
              Trim Video
            </button>
            <TmpTimeLine>
              {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((minute) => (
                <button onClick={() => handleGoTo(minute)}>{minute}</button>
              ))}
            </TmpTimeLine>
            <button
              onClick={() => {
                if (props.videoRef.current) {
                  props.videoRef.current.play();
                }
              }}
            >
              Play
            </button>
            <button
              onClick={() => {
                if (props.videoRef.current) {
                  props.videoRef.current.pause();
                }
              }}
            >
              Pause
            </button>
          </>
        )}
      </ProjectViewWrapper>
    </>
  );
};

export default ProjectView;
