import styled from 'styled-components';
import { useState } from 'react';
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import FileDownloadDoneIcon from '@mui/icons-material/FileDownloadDone';

import { Community } from '@/types';
import { postSourceClone } from '@/services/community';

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

const Download = ({ feed, disable }: Props) => {
  const [count, setCount] = useState(feed.downloadCount);
  const [downloaded, setDownloaded] = useState(feed.isDownloaded);
  const blocked = downloaded || disable;
  const handleClick = () => {
    if (blocked) {
      return;
    }
    postSourceClone(feed.feedId).then(() => {
      setCount((prev) => prev + 1);
      setDownloaded(true);
    });
  };

  return (
    <Button as={blocked ? 'div' : 'button'} onClick={handleClick}>
      {!downloaded && <FileDownloadIcon />}
      {downloaded && <FileDownloadDoneIcon />}
      <div>{count}</div>
    </Button>
  );
};

export default Download;
