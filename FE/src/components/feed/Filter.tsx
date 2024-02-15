import { useState, useContext } from 'react';
import styled from 'styled-components';

import { myContext } from '@/store/my-context';

const Menu = styled.div`
  position: absolute;
  z-index: 2;
  top: calc(var(--menu-tab-margin-top) + 8px);
  right: 16px;
  background-color: var(--rp-grey-800);
  display: flex;
  flex-direction: column;
  width: 5.5rem;
`;

const Box = styled.div`
  margin-top: 4px;
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

const FilterButton = styled(Button)`
  background-color: var(--rp-grey-700);
  width: 5.5rem;
`;

const Filter = () => {
  const { filter, setFilterName, selectAll, selectPublic, selectPrivate } =
    useContext(myContext);
  const [menuOpen, setMenuOpen] = useState(false);

  const openMenu = () => {
    setMenuOpen((prev) => !prev);
  };

  const clickAll = () => {
    setFilterName('All');
    selectAll();
    setMenuOpen(false);
  };

  const clickPublic = () => {
    setFilterName('Public');
    selectPublic();
    setMenuOpen(false);
  };

  const clickPrivate = () => {
    setFilterName('Private');
    selectPrivate();
    setMenuOpen(false);
  };

  return (
    <Menu>
      <FilterButton
        onClick={openMenu}
        className={menuOpen ? 'active' : undefined}
      >
        {filter}
      </FilterButton>

      {menuOpen && (
        <Box as="ul">
          <li>
            <Button onClick={clickAll}>All</Button>
          </li>
          <li>
            <Button onClick={clickPublic}>Public</Button>
          </li>
          <li>
            <Button onClick={clickPrivate}>Private</Button>
          </li>
        </Box>
      )}
    </Menu>
  );
};

export default Filter;
