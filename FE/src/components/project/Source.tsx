import { List } from 'lodash';
import Image from '../common/Image';
import { useDrag } from 'react-dnd';
import styled from 'styled-components';
const Box = styled.div`
  background-color: white;
  padding : 6px;
  border-radius: 10px;
  margin : 10px;
`
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
 
interface sourceProps {
  sourceInfo: sourceInterface;
}
const SourceLabel = styled.div`
  display: flex;
  flex-direction: column;
  width : parent;
  background-color: var(--background-color);
`
const Source = ({ sourceInfo }: sourceProps) => {
  const [{ isDragging }, drag] = useDrag({
    type: 'source',
    item: sourceInfo,
    collect: (monitor) => ({
      isDragging: !!monitor.isDragging(),
    }),
  });

  console.log('Is dragging: ', isDragging);
  return (
    <>
      <Box ref={drag}>
        <Image size={140} src={sourceInfo.sourceThumbnailUrl} />
        <SourceLabel>
          <div>{sourceInfo.sourceName}</div>
          <div>{sourceInfo.sourceStart}</div>
          <div>{sourceInfo.sourceEnd}</div>
        </SourceLabel>
      </Box>
    </>
  );
};

export default Source;
