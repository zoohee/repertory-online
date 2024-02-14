import { createContext, useState } from 'react';
import { useLoaderData } from 'react-router-dom';

import { Source, MySource, Tab } from '@/types';

interface SourcesContextType {
  sources: Source[];
  tags: string[];
  selectedTags: string[];
  selectTag: (tag: string) => void;
  unselectTag: (tag: string) => void;
  isTagOpen: boolean;
  openTag: () => void;
  tabs: Tab[];
}

export const sourcesContext = createContext<SourcesContextType>(
  {} as SourcesContextType
);

interface Props {
  children: React.ReactNode;
}

const SourcesContextProvider = ({ children }: Props) => {
  const [selectedTags, setSelectedTags] = useState<string[]>([]);
  const [isTagOpen, setIsTagOpen] = useState(false);

  const { mine, clone } = useLoaderData() as {
    mine: MySource[];
    clone: Source[];
  };

  mine.forEach((source) => {
    source.tagNameList = source.tagList.map((tag) => tag.tagName);
  });

  const [mineClicked, setMineClicked] = useState(true);
  const [cloneClicked, setCloneClicked] = useState(false);
  const [sources, setSources] = useState<Source[]>(mine);

  const clickMine = () => {
    setSources(mine);
    setIsTagOpen(false);
    setSelectedTags([]);
    setMineClicked(true);
    setCloneClicked(false);
  };

  const clickClone = () => {
    setSources(clone);
    setIsTagOpen(false);
    setSelectedTags([]);
    setMineClicked(false);
    setCloneClicked(true);
  };

  const tabs: Tab[] = [
    new Tab('My Sources', mineClicked, clickMine),
    new Tab('Cloned Sources', cloneClicked, clickClone),
  ];

  const tags = [...new Set(sources.map((source) => source.tagNameList).flat())];

  const selectTag = (tag: string) => {
    setSelectedTags((prev) => {
      return [...prev, tag];
    });
  };

  const openTag = () => {
    setIsTagOpen((prev) => !prev);
  };

  const unselectTag = (tag: string) => {
    setSelectedTags((prev) => {
      return prev.filter((t) => t !== tag);
    });
  };

  const value: SourcesContextType = {
    sources,
    tags,
    selectedTags,
    selectTag,
    unselectTag,
    isTagOpen,
    openTag,
    tabs,
  };

  return (
    <sourcesContext.Provider value={value}>{children}</sourcesContext.Provider>
  );
};

export default SourcesContextProvider;
