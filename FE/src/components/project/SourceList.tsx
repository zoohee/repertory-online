import { getMySource, getMySourceClone } from '@/services/dance';
import { useQuery } from '@tanstack/react-query';
import styled from 'styled-components';
import Source from './Source';
import { Title } from './Title';
import { ISource } from '@/services/interface';
import { SetStateAction, useState } from 'react';
import Tabs from '../common/Tab';
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
const TabWapper = styled.div`
  width : 100%;
  display: flex;
  z-index: 1000;
  /* position: fixed; */
  justify-content: space-evenly;
`
const Tab = styled.div`
  width : 50%;
  z-index: 1000;
  border-radius: 10px;
  text-align: center;
  padding : 1.4rem;
    &:hover{
      cursor: pointer;
    }
`
const SourceList = () => {
  const [tabState, setTabState] = useState('mysource');

  const { isLoading: isLoadingMySource, data: mySourceData, isError: isErrorMySource, error: mySourceError } = useQuery<ISource[]>({
    queryKey: ['getMySource', tabState],
    queryFn: () => tabState === 'mysource' ? getMySource() : getMySourceClone(),
  });

  const handleTabClick = (tab: SetStateAction<string>) => {
    setTabState(tab);
  }
  console.log(tabState)
  let mySource = [];
  let clonedSource = [];
  for (let i = 1; i <= 10; i++) {
    mySource.push({
      memberId: i,
      sourceCount: i + 1,
      sourceId: 1234 + i,
      sourceName: 'Source' + i,
      sourceStart: 'pose' + i,
      sourceEnd: 'pose' + (i + 1),
      sourceLength: 3 + 0.1 * i,
      sourceUrl: 'URL' + i,
      sourceThumbnailUrl: '/images/pinky.jpg',
      tagList: ['good', 'awesome', 'perfect']
    });
  }
  for (let i = 1; i <= 10; i++) {
    clonedSource.push({
      memberId: i,
      sourceCount: i + 1,
      sourceId: 1234 + i,
      sourceName: 'Clone' + i,
      sourceStart: 'pose' + i,
      sourceEnd: 'pose' + (i + 1),
      sourceLength: 3 + 0.1 * i,
      sourceUrl: 'URL' + i,
      sourceThumbnailUrl: '/images/mushroom.jpg',
      tagList: ['good', 'awesome', 'perfect']
    });
  }

  // if (isLoading) return <>Loading...</>;
  // if (isError) return <>{error.message}...</>;
  return (
    <>
      <Title title={'Source'} />
        <TabWapper>
          <Tab onClick={() => handleTabClick('mysource')} >My Source</Tab>
          <Tab onClick={() => handleTabClick('clonedsource')}>Clone Source</Tab>
        </TabWapper>
        <SourceListWrapper>

        {/* {mySourceData?.length === 0 ? (
          <h1>no data...</h1>
        ) : (
          <GridBox>
            {tmp?.map((item: ISource) => {
              return <Source key={item.memberId} sourceInfo={item} target={'sourceList'} />;
            })}
          </GridBox>
        )} */}
        <GridBox>
         {tabState==='mysource'?(
            mySource?.map((item: ISource) => {
              return <Source key={item.memberId} sourceInfo={item} target={'sourceList'} />;
            })
         ):(
            clonedSource?.map((item: ISource) => {
              return <Source key={item.memberId} sourceInfo={item} target={'sourceList'} />;
            })
         )}
        </GridBox>
      </SourceListWrapper>
    </>
  );
};

export default SourceList;
