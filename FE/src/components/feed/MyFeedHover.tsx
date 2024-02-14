import { useState } from 'react';
import styled from 'styled-components';

import LockIcon from '@mui/icons-material/Lock';
import LockOpenIcon from '@mui/icons-material/LockOpen';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

import MoreButton from '@/components/common/More';
import MenuBox from '@/components/common/MenuList';
import MenuButton from '@/components/common/MenuButton';
import { L as Text } from '@/components/common/Text';
import Like from '@/components/community/Like';
import { Community } from '@/types';
import { feedSetPublic, feedSetPrivate } from '@/services/community';

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

const Delete = styled(DeleteIcon)`
  color: var(--color-red);
`;

interface Props {
  feed: Community;
}

const MyFeedHover = ({ feed }: Props) => {
  feed.isLiked = true;
  const [isPrivate, setPrivate] = useState(feed.feedDisable);
  const [menuClicked, setMenuClicked] = useState(false);

  const toggleMenu = (e: React.MouseEvent<HTMLElement>) => {
    e.stopPropagation();
    setMenuClicked((prev) => !prev);
  };

  const handleClick = (e: React.MouseEvent<HTMLElement>) => {
    if (menuClicked) {
      toggleMenu(e);
    }
  };

  const setFeedPrivate = () => {
    feedSetPrivate(feed.originId, feed.feedType).then(() => {
      setPrivate(false);
    });
  };

  const setFeedPublic = () => {
    feedSetPublic(feed.originId, feed.feedType).then(() => {
      setPrivate(false);
    });
  };

  return (
    <Hover onClick={handleClick}>
      <MoreButton onClick={toggleMenu} />
      {menuClicked && (
        <MenuBox>
          {isPrivate && (
            <MenuButton name="공개로 변경" onClick={setFeedPublic}>
              <LockOpenIcon fontSize="small" />
            </MenuButton>
          )}
          {!isPrivate && (
            <MenuButton name="비공개로 변경" onClick={setFeedPrivate}>
              <LockIcon fontSize="small" />
            </MenuButton>
          )}
          <MenuButton name="이름 수정" onClick={() => {}}>
            <EditIcon fontSize="small" />
          </MenuButton>
          <MenuButton name="삭제" onClick={() => {}}>
            <Delete fontSize="small" />
          </MenuButton>
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
