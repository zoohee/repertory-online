import { ChangeEvent, MutableRefObject, useRef } from 'react';
import styled from 'styled-components';

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
const UploadZone = styled.div``;

const ProjectView = () => {
  const fileInput = useRef<HTMLInputElement>(null);

  const handleButtonClick = () => {
    if (fileInput.current) fileInput.current.click();
  };

  const handleFileUpload = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      const file = event.target.files[0];
      console.log(file);
    }
  };
  return (
    <>
      <HiddenInput type='file' ref={fileInput} onChange={handleFileUpload} />
      <UploadZone>
        <UploadButton type='button' onClick={handleButtonClick}>
          +
        </UploadButton>
      </UploadZone>
    </>
  );
};

export default ProjectView;
