import styled from 'styled-components';

import TagButton from '@/components/dance/TagButton';

const List = styled.ul`
  margin-top: 12px;
  border-radius: var(--border-radius-small);
  display: flex;
  flex-wrap: wrap;
  box-shadow: var(--box-shadow);
  padding: 8px;
`;

const TagList = () => {
  return (
    <List>
      <li>
        <TagButton name={'Tag #1'} />
      </li>
    </List>
  );
};

export default TagList;
