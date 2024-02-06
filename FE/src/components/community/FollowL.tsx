import { useState } from 'react';
import styled, { css } from 'styled-components';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import PersonIcon from '@mui/icons-material/Person';
import { buttonStyles } from '@/components/common/Button';
import { postSubscriber, deleteSubscriber } from '@/services/community';

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
          color: var(--color-red);
        }
      }
    `;
  }}
  border-radius: var(--button-border-radius);
  padding: var(--button-padding);
  display: flex;
  align-items: center;
`;

interface IconProps {
  followed: boolean;
  hover: boolean;
}

const Icon = ({ followed, hover }: IconProps) => {
  const IconStyle = {
    marginRight: 'var(--button-icon-margin)',
  };

  const text = () => {
    if (!followed) {
      return 'follow';
    }
    if (hover) {
      return 'unfollow';
    }
    return 'following';
  };

  return (
    <>
      {!followed && <PersonAddIcon style={IconStyle} />}
      {followed && <PersonIcon style={IconStyle} />}
      <div style={{ width: '6rem' }}>{text()}</div>
    </>
  );
};

interface Props {
  isFollowed: boolean;
  memberId: number;
}

const Follow = ({ isFollowed, memberId }: Props) => {
  const [followed, setFollowed] = useState(isFollowed);
  const [hover, setHover] = useState(false);

  const handleMouseOver = () => {
    setHover(true);
  };

  const handleMouseOut = () => {
    setHover(false);
  };

  const handleClick = () => {
    if (followed) {
      deleteSubscriber(memberId).then(() => {
        setFollowed(false);
      });
    } else {
      postSubscriber(memberId).then(() => {
        setFollowed(true);
      });
    }
  };

  return (
    <Button
      onClick={handleClick}
      $isFollowed={followed}
      onMouseOver={handleMouseOver}
      onMouseOut={handleMouseOut}
    >
      <Icon followed={followed} hover={hover} />
    </Button>
  );
};

export default Follow;
