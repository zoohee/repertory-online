import styled from 'styled-components';
import MoreVertIcon from '@mui/icons-material/MoreVert';

const Button = styled.button`
  height: var(--button-square);
  width: var(--button-square);
  border-radius: var(--border-radius-small);
  background-color: rgba(30, 30, 32, 0.7);
  position: absolute;
  top: var(--button-square-margin);
  right: var(--button-square-margin);
`;

interface Props {
  onClick: (e: React.MouseEvent<HTMLButtonElement>) => void;
}

const MoreButton = ({ onClick }: Props) => {
  return (
    <Button onClick={(e) => onClick(e)}>
      <MoreVertIcon />
    </Button>
  );
};

export default MoreButton;
