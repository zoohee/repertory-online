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

const Like = ({ count, clicked }: { count: number, clicked: boolean }) => {
    return (
        <Button>
      {clicked && <FavoriteIcon style={{ color: 'red' }} />}
      {!clicked && <FavoriteBorderIcon />}
      <div>{count}</div>
    </Button>
  );
};

export default Like;
