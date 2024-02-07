// import { MouseEvent } from 'react';
import { useState } from 'react';
import styled from 'styled-components';

import LockOpenIcon from '@mui/icons-material/LockOpen';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

import MoreButton from '@/components/common/More';
import MenuBox from '@/components/common/MenuList';
import MenuButton, { Menu } from '@/components/common/MenuButton';
import Text from '@/components/common/Text';

const Hover = styled.div`
  z-index: 1;
  width: 100%;
  height: 100%;
  cursor: pointer;
  position: relative;
`;

const Box = styled.div`
  background-color: rgba(30, 30, 32, 0.6);
  height: calc(var(--font-size-l) * 2);
  box-sizing: content-box;
  width: calc(100% - 32px);
  padding: 16px;
  position: absolute;
  bottom: 0;
`;

const menus = [
  new Menu('이름 수정', <EditIcon fontSize='small' />, () => {}),
  new Menu('삭제', <DeleteIcon fontSize='small' className='red' />, () => {}),
];

const MyFeedHover = () => {
  const [clicked, setClicked] = useState(false);

  const handleClick = (e: React.MouseEvent<HTMLDivElement>) => {
    if (clicked) {
      e.stopPropagation();
      setClicked((prev) => !prev);
    }
  };

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
    <Hover onClick={handleClick}>
      <MoreButton onClick={handleClickButton} />
      {clicked && (
        <MenuBox>
          <MenuButton name='공개로 변경' onClick={() => {}}>
            <LockOpenIcon fontSize='small' />
          </MenuButton>

          {menuItems}
        </MenuBox>
      )}
      <Box>
        <Text size='l' color='p'>
          제목 입니다. 제목 입니다. 제목 입니다. 제목 입니다. sdfa sdfasaa as
          dfasdf asdfafasdfa
        </Text>
      </Box>
    </Hover>
  );
};

export default MyFeedHover;
