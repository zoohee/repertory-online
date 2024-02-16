import { useContext } from 'react';
import styled from 'styled-components';

import { sourcesContext } from '@/store/sources-context';

const Box = styled.div`
  border-radius: var(--border-radius-small);
  height: var(--searchbar-height);
  box-shadow: var(--box-shadow);
  padding: 4px;
  margin: 0 8px;
`;

const Button = styled.button`
  padding: 0 8px;
  border-radius: var(--border-radius-small);
  text-align: center;
  height: 100%;
  &:hover {
    background-color: var(--rp-grey-700);
  }
  &:active,
  &.active {
    background-color: var(--rp-grey-600);
  }
`;

const SelectTagButton = () => {
  const { isTagOpen, openTag } = useContext(sourcesContext);

  const buttonStyle = isTagOpen ? 'active' : undefined;

  return (
    <Box>
      <Button onClick={openTag} className={buttonStyle}>
        태그 선택
      </Button>
    </Box>
  );
};

export default SelectTagButton;
