import { useState } from 'react';
import styled from 'styled-components';

import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import { patchFeedLike, patchFeedNotLike } from '@/services/community';
import { Community } from '@/types';

const Button = styled.button`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-left: 16px;
`;

interface Props {
  feed: Community;
  disable?: boolean;
}

const Like = ({ feed, disable}: Props) => {
  const [isLiked, setIsLiked] = useState(feed.isLiked);
  const [count, setCount] = useState(feed.likeCount);

  const handleClick = () => {
    if (disable) {
      return;
    }
    if (isLiked) {
      patchFeedNotLike(feed.feedId).then(() => {
        setCount((prevCount) => prevCount - 1);
        setIsLiked(false);
      });
    } else {
      patchFeedLike(feed.feedId).then(() => {
        setCount((prevCount) => prevCount + 1);
        setIsLiked(true);
      });
    }
  };

  return (
    <Button as={disable ? 'div' : 'button'} onClick={handleClick}>
      {isLiked && <FavoriteIcon className="red" />}
      {!isLiked && <FavoriteBorderIcon />}
      <div>{count}</div>
    </Button>
  );
};

export default Like;
