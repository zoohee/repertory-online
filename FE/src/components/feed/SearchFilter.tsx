import { useState } from 'react';
import styled from 'styled-components';

const Menu = styled.div`
  width: 4rem;
  margin-right: 4px;
  display: flex;
  flex-direction: column;
`;

const Box = styled.div`
  background-color: var(--rp-grey-800);
  border-radius: var(--border-radius-small);
  box-shadow: var(--box-shadow);
  padding: 4px;
`;

const Button = styled.button`
  font-size: var(--font-size-s);
  display: block;
  padding: 8px;
  border-radius: var(--border-radius-small);
  text-align: center;
  width: 100%;
  &:hover {
    background-color: var(--rp-grey-600);
  }
  &:active,
  &.active {
    background-color: var(--rp-grey-500);
  }
`;

const SearchFilter = () => {
  const [menu, setMenu] = useState('동영상');
  const [menuOpen, setMenuOpen] = useState(false);

  const openMenu = () => {
    setMenuOpen((prev) => !prev);
  };

  const clickVideo = () => {
    setMenu('동영상');
    setMenuOpen(false);
  };

  const clickDancer = () => {
    setMenu('댄서');
    setMenuOpen(false);
  };

  return (
    <Menu>
      <Box>
        <Button onClick={openMenu} className={menuOpen ? 'active' : undefined}>
          {menu}
        </Button>
      </Box>

      {menuOpen && (
        <Box as="ul">
          <li>
            <Button onClick={clickVideo}>동영상</Button>
          </li>
          <li>
            <Button onClick={clickDancer}>댄서</Button>
          </li>
        </Box>
      )}
    </Menu>
  );
};

export default SearchFilter;
