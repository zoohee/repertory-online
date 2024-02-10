import { getMySource, getMySourceClone } from '@/services/dance';
import { useQuery } from '@tanstack/react-query';
import styled from 'styled-components';
import Source from './Source';
import { Title } from './Title';
import { ISource } from '@/services/interface';

const SourceListWrapper = styled.div`
  overflow: scroll;
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
  const { isLoading, data, isError, error } = useQuery<ISource[]>({
    queryKey: ['getMySource'],
    queryFn: getMySource,
  });

  let tmp = [];
  for (let i = 1; i <= 30; i++) {
    tmp.push({
      memberId: i,
      sourceCount: i + 1,
      sourceId: 1234 + i,
      sourceName: 'Sample' + i,
      sourceStart: 'pose' + i,
      sourceEnd: 'pose' + (i + 1),
      sourceLength: (20 + i).toString(),
      sourceUrl: 'URL' + i,
      sourceThumbnailUrl: '/public/images/mushroom.jpg',
      tagList: ['good', 'awesome', 'perfect']
    });
  }
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
              {tmp?.map((item: ISource) => {
              return <Source key={item.memberId} sourceInfo={item} />;
            })}
          </GridBox>
        )}
      </SourceListWrapper>
    </>
  );
};

export default SourceList;
