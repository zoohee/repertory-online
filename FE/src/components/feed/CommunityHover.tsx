import styled from 'styled-components';
import FavoriteIcon from '@mui/icons-material/Favorite';

const Hover = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  aspect-ratio: 1 / 1;
  z-index: 1;
  background-color: rgba(30, 30, 32, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
`;

const LikeCount = styled.div`
  margin-left: 4px;
  display: flex;
  align-items: center;
`;

interface Props {
  likeCount: number;
  onClick: () => void;
}

const CommunityHover = ({ likeCount, onClick }: Props) => {
  return (
    <Hover onClick={onClick}>
      <FavoriteIcon />
      <LikeCount>{likeCount}</LikeCount>
    </Hover>
  );
};

export default CommunityHover;
