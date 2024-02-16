import { useContext } from 'react';
import styled from 'styled-components';

import { sourcesContext } from '@/store/sources-context';
import TagButton from '@/components/dance/sources/TagButton';

const List = styled.ul`
  margin-top: 12px;
  border-radius: var(--border-radius-small);
  display: flex;
  flex-wrap: wrap;
  box-shadow: var(--box-shadow);
  padding: 8px;
`;

const TagList = () => {
  const { tags } = useContext(sourcesContext);

  return (
    <List>
      {tags.map((tag) => (
        <li key={tag}>
          <TagButton name={tag} />
        </li>
      ))}
    </List>
  );
};

export default TagList;
