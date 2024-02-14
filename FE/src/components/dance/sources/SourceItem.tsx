import { useState } from 'react';
import styled from 'styled-components';

import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

import Dance from '@/components/dance/Dance';
import { Secondary as Text } from '@/components/common/Text';
import DanceHover from '@/components/dance/DanceHover';
import { Menu } from '@/components/common/MenuButton';
import { Source } from '@/types';

const ListItem = styled.li`
  position: relative;
  cursor: pointer;
`;

const menus = [
  new Menu('수정', <EditIcon fontSize="small" />, () => {}),
  new Menu('삭제', <DeleteIcon fontSize="small" className="red" />, () => {}),
];

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
      {isHovering && <DanceHover menus={menus} />}
      <Dance thumbnail={source.sourceThumbnailUrl} title={source.sourceName}>
        <Text>{source.tagNameList.map((tag) => `#${tag}`).join(' ')}</Text>
      </Dance>
    </ListItem>
  );
};

export default SourceItem;
