import { List } from 'lodash';
import Image from '../common/Image';
import { useDrag } from 'react-dnd';
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
      <div ref={drag}>
        <Image size={140} src={sourceInfo.sourceThumbnailUrl} />
      </div>
    </>
  );
};

export default Source;
