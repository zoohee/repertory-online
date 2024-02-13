import { useContext } from 'react';
import styled from 'styled-components';

import { sourcesContext } from '@/store/sources-context';

import TabButtons from '@/components/common/Tab';
import SearchBar from '@/components/SearchBar';
import SourceList from '@/components/dance/sources/SourceList';
import Wrapper from '@/components/Wrapper';
import CreateButton from '@/components/dance/CreateButton';
import SelectTagButton from '@/components/dance/sources/SelectTagButton';
import TagList from '@/components/dance/sources/TagList';

const Box = styled.div`
  margin: 24px 0;
  padding: 0 12px;
  width: 100%;
`;

const SourcesPage = () => {
  const { tabs, isTagOpen } = useContext(sourcesContext);

  return (
    <>
      <TabButtons tabs={tabs} margin="48px 0 0" />
      <Box>
        <Wrapper $margin="0">
          <div style={{ display: 'flex' }}>
            <SearchBar></SearchBar>
            <SelectTagButton />
          </div>
          <CreateButton to="/" />
        </Wrapper>
        {isTagOpen && <TagList />}
      </Box>
      <SourceList />
    </>
  );
};

export default SourcesPage;

import { getMySource, getMySourceClone } from '@/services/dance';

export const sourceLoader = async () => {
  const mine = await getMySource();
  const clone = await getMySourceClone();
  return { mine, clone };
};
