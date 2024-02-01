import styled from 'styled-components';
import LockIcon from '@mui/icons-material/Lock';

const Box = styled.span`
  height: var(--button-square);
  width: var(--button-square);
  border-radius: var(--border-radius-small);
  background-color: rgba(30, 30, 32, 0.7);
  cursor: default;
  position: absolute;
  top: var(--button-square-margin);
  left: var(--button-square-margin);
  z-index: 5;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Lock = () => {
  return (
    <Box>
      <LockIcon fontSize="small" />
    </Box>
  );
};

export default Lock;
