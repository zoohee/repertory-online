import {
  // JSXElementConstructor,
  // Key,
  // ReactElement,
  // ReactNode,
  // ReactPortal,
  useState,
} from 'react';
import Source from './Source';
import { useDrop } from 'react-dnd';
// import { List } from 'lodash';
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
  const [{ canDrop, isOver }, drop] = useDrop({
    accept: 'source',
    drop: (item: ISource) => {
      setSources((prevItems) => [...prevItems, item]); // 드롭한 아이템을 리스트에 추가
      return { name: 'Workbench' };
    },
    collect: (monitor) => ({
      isOver: monitor.isOver(),
      canDrop: monitor.canDrop(),
    }),
  });
  // console.log('Is over: ', isOver); // 로깅
  // console.log('Can drop: ', canDrop); // 로깅
  console.log(sources); // 로깅

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
