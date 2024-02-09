import { useState } from 'react';
import styled from 'styled-components';

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
  background-color: transparent;
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

interface Props {
  openTagList: () => void;
}

const SelectTagButton = ({ openTagList }: Props) => {
  const [clicked, setClicked] = useState(false);

  const buttonStyle = clicked ? 'active' : undefined;

  const handleClick = () => {
    setClicked((prev) => !prev);
    openTagList();
  };

  return (
    <Box>
      <Button onClick={handleClick} className={buttonStyle}>
        태그 선택
      </Button>
    </Box>
  );
};

export default SelectTagButton;
