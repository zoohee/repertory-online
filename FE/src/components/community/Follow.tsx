import { useState } from 'react';
import styled, { css } from 'styled-components';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import PersonIcon from '@mui/icons-material/Person';
import { buttonStyles } from '@/components/common/Button';
import { postSubscriber, deleteSubscriber } from '@/services/community';

interface ButtonProps {
  $size: 'small' | 'medium';
  $followed: boolean;
}

const Button = styled.button<ButtonProps>`
  ${({ $followed }) => {
    if (!$followed) {
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
  padding: ${({ $size }) => {
    if ($size === 'small') {
      return 'var(--button-padding-small)';
    }
    return 'var(--button-padding)';
  }};
  display: flex;
  align-items: center;
`;

interface IconProps extends ButtonProps {
  hover: boolean;
}

const Text = styled.div<{ $size: string }>`
  margin-left: var(--button-icon-margin);
  ${({ $size }) => {
    if ($size === 's') {
      return css`
        width: 5rem;
        font-size: var(--font-size-s);
      `;
    }
    return css`
      width: 6rem;
    `;
  }}
`;

const Icon = ({ $size, $followed, hover }: IconProps) => {
  const text = () => {
    if (!$followed) {
      return 'follow';
    }
    if (hover) {
      return 'unfollow';
    }
    return 'following';
  };

  return (
    <>
      {!$followed && <PersonAddIcon fontSize={$size} />}
      {$followed && <PersonIcon fontSize={$size} />}
      <Text $size={$size}>{text()}</Text>
    </>
  );
};

interface Props extends ButtonProps {
  memberId: number;
}

const Follow = ({ $followed, memberId, $size }: Props) => {
  const [followed, setFollowed] = useState($followed);
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
      $size={$size}
      $followed={followed}
      onMouseOver={handleMouseOver}
      onMouseOut={handleMouseOut}
    >
      <Icon $size={$size} $followed={followed} hover={hover} />
    </Button>
  );
};

export default Follow;
