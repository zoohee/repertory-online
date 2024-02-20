import {
  // JSXElementConstructor,
  // Key,
  // ReactElement,
  // ReactNode,
  // ReactPortal,
  useState,
} from 'react';
import Source from './Source';
import styled from 'styled-components';
import { ISource } from '@/services/interface';
const Wrapper = styled.div`
  width: 100%;
  height: 80%;
  display: flex;
  overflow-x: auto;
`;
const Btn = styled.button`
  background-color: transparent;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
`;
interface WorkbenchProps {
  concatVideos: (videos: ISource[]) => Promise<void>;
}
const Workbench = (props: WorkbenchProps) => {
  const [sources, setSources] = useState<ISource[]>([]);

  return (
    <>
      <Container>
        <Btn onClick={() => props.concatVideos(sources)}>CONCAT</Btn>
        <Wrapper ref={drop}>
          {
            // 드롭한 아이템들을 리스트로 출력
            sources.map((item: ISource, index) => {
              return (
                <Source sourceInfo={item} target={'workbench'} key={index} />
              );
            })
          }
        </Wrapper>
      </Container>
    </>
  );
};

export default Workbench;
