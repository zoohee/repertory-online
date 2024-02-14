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
  // TODO: 다운로드 여부 추가
  const [downloaded, setDownloaded] = useState(false);
  const blocked = downloaded || disable;
  const handleClick = () => {
    if (blocked) {
      return;
    }
    postSourceClone(feed.feedId).then(() => {
      setCount(count + 1);
      setDownloaded(true);
    });
  };

  return (
    <Button as={blocked ? 'div' : 'button'} onClick={handleClick}>
      {!downloaded && <FileDownloadIcon />}
      {downloaded && <FileDownloadDoneIcon />}
      <div>{feed.downloadCount}</div>
    </Button>
  );
};

export default Download;
