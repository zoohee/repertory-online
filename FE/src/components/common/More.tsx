import styled, { RuleSet } from 'styled-components';
import MoreVertIcon from '@mui/icons-material/MoreVert';

const Button = styled.button<{ css: RuleSet }>`
  height: var(--button-square);
  width: var(--button-square);
  border-radius: var(--border-radius-small);
  background-color: rgba(30, 30, 32, 0.7);
  ${({ css }) => css}
`;

interface Props {
  onClick: (e: React.MouseEvent<HTMLButtonElement>) => void;
  css: RuleSet;
}

const MoreButton = ({ onClick, css }: Props) => {
  return (
    <Button onClick={(e) => onClick(e)} css={css}>
      <MoreVertIcon />
    </Button>
  );
};

export default MoreButton;
