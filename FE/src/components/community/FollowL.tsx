import { useState } from 'react';
import styled, { css } from 'styled-components';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import PersonIcon from '@mui/icons-material/Person';
import { buttonStyles } from '@/components/common/Button';
import axios from 'axios';
import { API } from '@/url';

const Button = styled.button<{ isFollowed: boolean }>`
  ${({ isFollowed }) => {
    if (!isFollowed) {
      return buttonStyles.default;
    }
    return css`
      background-color: var(--text-secondary-dark-mode);

      &:hover {
        background-color: var(--rp-white);
        * {
          color: var(--background-color);
        }
      }
    `;
  }}
  border-radius: var(--button-border-radius);
  padding: var(--button-padding);
  display: flex;
  align-items: center;
`;

const Icon = (followed: boolean) => {
  const IconStyle = {
    marginRight: 'var(--button-icon-margin)',
  };

  const text = followed ? 'following' : 'follow';

  return (
    <>
      {!followed && <PersonAddIcon style={IconStyle} />}
      {followed && <PersonIcon style={IconStyle} />}
      <div style={{ width: '6rem' }}>{text}</div>
    </>
  );
};

const Follow = ({ isFollowed, id }: { isFollowed: boolean; id: number }) => {
  const [followed, setFollowed] = useState(isFollowed);

  const handleClick = () => {
    if (followed) {
      // 언팔 api 보내기
    } else {
      // 팔로우 api 보내기
      axios
        .post(`${API.follow}`, { selectedMemberId: id }, {
          withCredentials: true
        })
        .then((response) => {
          console.log('성공');
        })
        .catch((error) => {
          console.log('실패');
        });
    }
    setFollowed((prev) => !prev);
  };

  return (
    <Button onClick={handleClick} isFollowed={followed}>
      {Icon(followed)}
    </Button>
  );
};

export default Follow;
