import { useState } from 'react';
import styled, { css } from 'styled-components';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import PersonIcon from '@mui/icons-material/Person';
import { buttonStyles } from '@/components/common/Button';

const Button = styled.button<{ $isFollowed: boolean }>`
  ${({ $isFollowed }) => {
    if (!$isFollowed) {
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
  padding: var(--button-padding-small);
  display: flex;
  align-items: center;
`;

const Icon = (followed: boolean) => {
  const IconStyle = {
    marginRight: 'var(--button-icon-margin)',
  };

  const textStyle = {
    width: '5rem',
    fontSize: 'var(--font-size-s)',
  };

  const text = followed ? 'following' : 'follow';

  return (
    <>
      {!followed && <PersonAddIcon style={IconStyle} fontSize="small" />}
      {followed && <PersonIcon style={IconStyle} fontSize="small" />}
      <div style={textStyle}>{text}</div>
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
    <Button onClick={handleClick} $isFollowed={followed}>
      {Icon(followed)}
    </Button>
  );
};

export default Follow;
