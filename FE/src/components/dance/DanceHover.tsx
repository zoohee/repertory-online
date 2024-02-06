import { useState } from 'react';
import styled from 'styled-components';

import MoreButton from '@/components/common/More';
import MenuList from '@/components/common/MenuList';
import MenuButton, { Menu } from '@/components/common/MenuButton';

const Hover = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 1;
`;

const DanceHover = ({ menus }: { menus: Menu[] }) => {
  const [clicked, setClicked] = useState(false);

  const handleClickButton = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    setClicked((prev) => !prev);
  };

  const menuItems = menus.map((menu) => (
    <MenuButton
      name={menu.name}
      onClick={(e) => {
        handleClickButton(e);
        menu.request();
      }}
    >
      {menu.icon}
    </MenuButton>
  ));

  return (
    <Hover>
      <MoreButton onClick={handleClickButton} />
      {clicked && <MenuList>{menuItems}</MenuList>}
    </Hover>
  );
};

export default DanceHover;
