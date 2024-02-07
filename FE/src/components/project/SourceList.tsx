import { getMySource, getMySourceClone } from '@/services/dance';
import { useQuery } from '@tanstack/react-query';
import { List } from 'lodash';
import Source from './Source';
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
const SourceList = () => {
  const { isLoading, data, isError, error } = useQuery<sourceInterface[]>({
    queryKey: ['getMySource'],
    queryFn: getMySource,
  });

  if (isLoading) return <>Loading...</>;
  if (isError) return <>{error.message}...</>;
  return (
    <>
      <h1>Sources</h1>
      <div>
        {data?.length === 0 ? (
          <h1>no data...</h1>
        ) : (
          data?.map((item: sourceInterface) => {
            return <Source sourceInfo={item} />;
          })
        )}
      </div>
    </>
  );
};

export default SourceList;
