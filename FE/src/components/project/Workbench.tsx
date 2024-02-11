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
const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
`;

interface sourceInterface {
  memberId: number;
  sourceCount: number;
  sourceId: number;
  sourceName: string;
  sourceStart: string;
  sourceEnd: string;
  sourceLength: string;
  sourceUrl: string;
  sourceThumbnailUrl: string;
  tagList: List<string>;
}
const Workbench = () => {
  const [sources, setSources] = useState<sourceInterface[]>([]);
  const [{ canDrop, isOver }, drop] = useDrop({
    accept: 'source',
    drop: (item: sourceInterface) => {
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
            sources.map((item: sourceInterface) => {
              return <Source sourceInfo={item} />;
            })
          }
        </Wrapper>
  );
};

export default Workbench;
