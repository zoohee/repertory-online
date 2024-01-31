import { useState } from 'react';
import styled from 'styled-components';

import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';

const Button = styled.button`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-left: 16px;
  background-color: transparent;
`;

interface Props {
  likeCount: number;
  liked: boolean;
  // TODO: feedId 추가
}

const Like = ({ likeCount, liked }: Props) => {
  const [isLiked, setIsLiked] = useState(liked);
  const [count, setCount] = useState(likeCount);

  const handleClick = () => {
    if (isLiked) {
      // TODO: 좋아요 취소 api 보내기
      setCount((prevCount) => prevCount - 1);
    } else {
      // TODO: 좋아요 api 보내기
      setCount((prevCount) => prevCount + 1);
    }
    setIsLiked((prev) => !prev);
  };

  return (
    <Button onClick={handleClick}>
      {isLiked && <FavoriteIcon style={{ color: 'red' }} />}
      {!isLiked && <FavoriteBorderIcon />}
      <div>{count}</div>
    </Button>
  );
};

export default Like;
