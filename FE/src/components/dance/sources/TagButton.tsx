import { useContext } from 'react';
import styled from 'styled-components';

import { sourcesContext } from '@/store/sources-context';

const Tag = styled.button`
  border-radius: var(--button-border-radius);
  background-color: var(--rp-grey-700);
  padding: 8px 12px;
  margin: 4px;
  &:hover {
    background-color: var(--rp-grey-600);
  }
  &:active,
  &.active {
    background-color: var(--rp-grey-500);
  }
`;

interface Props {
  name: string;
}

const TagButton = ({ name }: Props) => {
  const { selectTag, unselectTag, selectedTags } = useContext(sourcesContext);

  const clicked = selectedTags.includes(name);

  const handleClick = () => {
    if (clicked) {
      unselectTag(name);
      return;
    }
    selectTag(name);
  };

  const active = clicked ? 'active' : undefined;

  return (
    <Tag className={active} onClick={handleClick}>
      {`#${name}`}
    </Tag>
  );
};

export default TagButton;
