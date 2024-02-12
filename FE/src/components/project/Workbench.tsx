import {
  JSXElementConstructor,
  Key,
  ReactElement,
  ReactNode,
  ReactPortal,
  useState,
} from 'react';
import Source from './Source';
import { useDrop } from 'react-dnd';
import { List } from 'lodash';
import styled from 'styled-components';
import { ISource } from '@/services/interface';
const Wrapper = styled.div`
  background-color: red;
  width: 100%;
  height: 50%;
  display: flex;
  overflow: scroll;
`;
const Workbench = () => {
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
  console.log('Is over: ', isOver); // 로깅
  console.log('Can drop: ', canDrop); // 로깅
  console.log(sources); // 로깅
  
  return (
        <Wrapper ref={drop}>
          {
            // 드롭한 아이템들을 리스트로 출력
            sources.map((item: ISource, index) => {
              return <Source sourceInfo={item} target={'workbench'} key={index}/>;
            })
          }
        </Wrapper>
  );
};

export default Workbench;
