import { useState } from 'react';
import styled from 'styled-components';

import Dance from '@/components/dance/Dance';
import { Secondary as Text } from '@/components/common/Text';
import DanceHover from '@/components/dance/projects/ProjectHover';
import { Project } from '@/types';
import { deriveDaysAgo } from '@/services/util';

const ListItem = styled.li`
  position: relative;
  cursor: pointer;
`;

interface Props {
  project: Project;
}

const ProjectItem = ({ project }: Props) => {
  const [isHovering, setIsHovering] = useState(false);

  const handleMouseOver = () => {
    setIsHovering(true);
  };

  const handleMouseOut = () => {
    setIsHovering(false);
  };

  return (
    <ListItem onMouseOver={handleMouseOver} onMouseOut={handleMouseOut}>
      {isHovering && <DanceHover id={project.id} />}
      <Dance
        thumbnail={project.projectThumbnailUrl}
        title={project.projectName}
      >
        <Text>{deriveDaysAgo(project.projectDate)}</Text>
      </Dance>
    </ListItem>
  );
};

export default ProjectItem;
