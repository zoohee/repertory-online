import styled from 'styled-components';

import DownloadIcon from '@mui/icons-material/Download';

const Button = styled.button`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-left: 16px;
  background-color: transparent;
`;

const Download = ({ count }: { count: number }) => {
  return (
    <Button>
      <DownloadIcon />
      <div>{count}</div>
    </Button>
  );
};

export default Download;
