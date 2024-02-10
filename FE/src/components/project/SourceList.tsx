import { getMySource, getMySourceClone } from '@/services/dance';
import { useQuery } from '@tanstack/react-query';
import { List } from 'lodash';
import styled from 'styled-components';
import Source from './Source';
import { Title } from './Title';
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
const SourceListWrapper = styled.div`
  display: flex;
  flex-direction: column;
`
const GridBox = styled.ul`
  padding: 0 16px;
  width: 100%;
  height : 90vh;
  display: grid;
  grid-template-columns: repeat(
    var(--grid-column),
    calc(
      (100% - var(--grid-gap) * (var(--grid-column) - 1)) / var(--grid-column)
    )
  );
  grid-gap: var(--grid-gap);
  --grid-gap: 12px;
  --grid-column: 3;
`;
const SourceList = () => {
  const { isLoading, data, isError, error } = useQuery<sourceInterface[]>({
    queryKey: ['getMySource'],
    queryFn: getMySource,
  });

  // if (isLoading) return <>Loading...</>;
  // if (isError) return <>{error.message}...</>;
  return (
    <>
    <Title title={'Source'} />
     <SourceListWrapper>
        {data?.length === 0 ? (
          <h1>no data...</h1>
        ) : (
          <GridBox>
            {data?.map((item: sourceInterface) => {
              return <Source key={item.memberId} sourceInfo={item} />;
            })}
          </GridBox>
        )}
      </SourceListWrapper>
    </>
  );
};

export default SourceList;
