import { createContext, useState } from 'react';
import { useLoaderData } from 'react-router-dom';

import { Project } from '@/types';

interface ProjectContextType {
  projects: Project[];
  removeProject: (id: number) => void;
}

export const projectContext = createContext({} as ProjectContextType);

interface Props {
  children: React.ReactNode;
}

const ProjectContextProvider = ({ children }: Props) => {
  const [projects, setProjects] = useState(useLoaderData() as Project[]);

  const removeProject = (id: number) => {
    setProjects((prev) => prev.filter((project) => project.id !== id));
  };

  const value: ProjectContextType = {
    projects,
    removeProject,
  };
  return (
    <projectContext.Provider value={value}>{children}</projectContext.Provider>
  );
};

export default ProjectContextProvider;
