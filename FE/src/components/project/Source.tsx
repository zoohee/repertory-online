// import { List } from 'lodash';
import Image from '../common/ImageSquare';
import styled from 'styled-components';
import { ISource } from '@/services/interface';
import { useState } from 'react';
interface BoxProps {
  width: number;
}
interface sourceProps {
  target: string;
  sourceInfo: ISource;
}
const Box = styled.div<BoxProps>`
  position: relative;
  background-color: white;
  border-radius: 10px;
  margin: 3px;
  height: 170px;
  overflow: hidden;
`;

const SourceLabel = styled.div`
  position: absolute;
  bottom: 0;
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 50px;
  background-color: var(--background-color);
  padding: 0.4rem;
  font-size: 0.8rem;
`;
const SourceName = styled.div`
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;
const Popup = styled.div`
  position: absolute;
  top: 0;
  right: 0;
  background-color: white;
  padding: 10px;
  border: 1px solid black;
`;
const Source = ({ sourceInfo, target }: sourceProps) => {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <>
      <Box
        width={160}
        onMouseEnter={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)}
      >
        <Image size={140} src={sourceInfo.sourceThumbnailUrl} />
        {target === 'sourceList' && (
          <SourceLabel>
            <SourceName>{sourceInfo.sourceName}</SourceName>
            {/* <div>{sourceInfo.sourceStart}</div>
          <div>{sourceInfo.sourceEnd}</div> */}
          </SourceLabel>
        )}
        {isHovered && (
          <Popup>
            {sourceInfo.sourceName}
            {/* 여기에 더 많은 sourceInfo를 출력할 수 있습니다. */}
          </Popup>
        )}
      </Box>
    </>
  );
};

export default Source;
