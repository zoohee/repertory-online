import {
  ChangeEvent,
  // SetStateAction,
  // MutableRefObject,
  useRef,
} from 'react';
import styled from 'styled-components';
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
interface ITrimSection {
  start: number;
  end: number;
}
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

const StyledVideo = styled.video`
  height: 86%;
  width: 100%;
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
        ></StyledVideo>
      </ProjectViewWrapper>

      <button onClick={() => props.trimVideo({ start: 3000, end: 11000 })}>
        TR
      </button>
    </>
  );
};

export default ProjectView;
