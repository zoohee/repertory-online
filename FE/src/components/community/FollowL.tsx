import { useState } from 'react';
import styled, { css } from 'styled-components';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import PersonIcon from '@mui/icons-material/Person';

import Text from '@/components/common/Text';

const Button = styled.button<{ isFollowed: boolean }>`
  ${({ isFollowed }) => {
    if (!isFollowed) {
      return css`
        background-color: var(--rp-yellow);

        &:hover {
          background-color: var(--rp-orange);
          * {
            color: var(--rp-white);
          }
        }
      `;
    }
    return css`
      background-color: var(--text-secondary-dark-mode);

      &:hover {
        background-color: var(--rp-white);
      }
    `;
  }}
  border-radius: 20px;
  height: 2rem;
`;

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0 16px;
  * {
    color: var(--background-color);
  }
`;

const Icon = (followed: boolean) => {
  if (!followed) {
    return (
      <>
        <PersonAddIcon style={{ marginRight: '8px' }} />
        <Text size="m" color="p" style={{ width: '6rem' }}>
          follow
        </Text>
      </>
    );
  }
  return (
    <>
      <PersonIcon style={{ marginRight: '8px' }} />
      <Text size="m" color="p" style={{ width: '6rem' }}>
        following
      </Text>
    </>
  );
};

const Follow = ({ isFollowed }: { isFollowed: boolean }) => {
  const [followed, setFollowed] = useState(isFollowed);

  const handleClick = () => {
    if (followed) {
      // 언팔 api 보내기
    } else {
      // 팔로우 api 보내기
    }
    setFollowed((prev) => !prev);
  };

  return (
    <Button onClick={handleClick} isFollowed={followed}>
      <Container>{Icon(followed)}</Container>
    </Button>
  );
};

export default Follow;
