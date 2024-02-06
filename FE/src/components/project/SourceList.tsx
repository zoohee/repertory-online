import { getMySource, getMySourceClone } from '@/services/dance';
import { useQuery } from '@tanstack/react-query';

interface sourceInterface {
  sourceName: string;
  sourceUrl: string;
  sourceStart: string;
  sourceEnd: string;
  sourceLength: string;
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
            return (
              <div>
                {item.sourceName},{item.sourceUrl}
              </div>
            );
          })
        )}
      </div>
    </>
  );
};

export default SourceList;
