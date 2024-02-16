import { useState } from 'react';
import styled from 'styled-components';

import Dance from '@/components/dance/Dance';
import { Secondary as Text } from '@/components/common/Text';
import DanceHover from '@/components/dance/sources/SourceHover';
import { Source } from '@/types';

const ListItem = styled.li`
  position: relative;
  cursor: pointer;
`;

interface Props {
  source: Source;
}

const SourceItem = ({ source }: Props) => {
  const [isHovering, setIsHovering] = useState(false);

  const handleMouseOver = () => {
    setIsHovering(true);
  };

  const handleMouseOut = () => {
    setIsHovering(false);
  };

  return (
    <ListItem onMouseOver={handleMouseOver} onMouseOut={handleMouseOut}>
      {isHovering && <DanceHover id={source.sourceId} />}
      <Dance thumbnail={source.sourceThumbnailUrl} title={source.sourceName}>
        <Text>{source.tagNameList.map((tag) => `#${tag}`).join(' ')}</Text>
      </Dance>
    </ListItem>
  );
};

export default SourceItem;
