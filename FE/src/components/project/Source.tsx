// import { List } from 'lodash';
import Image from '../common/Image';
import { useDrag } from 'react-dnd';
import styled from 'styled-components';
import { ISource } from '@/services/interface';
interface BoxProps {
  width: number;
}
const Box = styled.div<BoxProps>`
  background-color: white;
  border-radius: 10px;
  margin: 3px;
  min-width: 100px;
  height: 100px;
  overflow: hidden;
`;
interface sourceProps {
  target: string;
  sourceInfo: ISource;
}
const SourceLabel = styled.div`
  display: flex;
  flex-direction: column;
  width: parent;
  background-color: var(--background-color);
`;
const Source = ({ sourceInfo, target }: sourceProps) => {
  const [
    ,
    // { isDragging }
    drag,
  ] = useDrag({
    type: 'source',
    item: sourceInfo,
    collect: (monitor) => ({
      isDragging: !!monitor.isDragging(),
    }),
  });

  return (
    <>
      <Box ref={drag} width={100}>
        <Image size={100} src={sourceInfo.sourceThumbnailUrl} />
        {target === 'sourceList' && (
          <SourceLabel>
            <div>{sourceInfo.sourceName}</div>
            <div>{sourceInfo.sourceStart}</div>
            <div>{sourceInfo.sourceEnd}</div>
          </SourceLabel>
        )}
      </Box>
    </>
  );
};

export default Source;
