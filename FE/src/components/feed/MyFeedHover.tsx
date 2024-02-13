import { useState } from 'react';
import styled from 'styled-components';

import LockOpenIcon from '@mui/icons-material/LockOpen';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

import MoreButton from '@/components/common/More';
import MenuBox from '@/components/common/MenuList';
import MenuButton, { Menu } from '@/components/common/MenuButton';
import { L as Text } from '@/components/common/Text';
import Like from '@/components/community/Like';
import { Community } from '@/types';

const Hover = styled.div`
  z-index: 1;
  width: 100%;
  height: 100%;
  cursor: pointer;
  position: relative;
`;

const Box = styled.div`
  background-color: rgba(30, 30, 32, 0.6);
  display: flex;
  justify-content: space-between;
  width: 100%;
  padding: 16px;
  position: absolute;
  bottom: 0;
`;

const menus = [
  new Menu('이름 수정', <EditIcon fontSize="small" />, () => {}),
  new Menu('삭제', <DeleteIcon fontSize="small" className="red" />, () => {}),
];

interface Props {
  feed: Community;
}

const MyFeedHover = ({ feed }: Props) => {
  feed.isLiked = true;
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
          <MenuButton name="공개로 변경">
            <LockOpenIcon fontSize="small" />
          </MenuButton>

          {menuItems}
        </MenuBox>
      )}
      <Box>
        <Text>{feed.feedName}</Text>
        <Like feed={feed} disable />
      </Box>
    </Hover>
  );
};

export default MyFeedHover;
