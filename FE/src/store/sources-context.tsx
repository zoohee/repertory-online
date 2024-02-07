import { createContext, useState, useEffect } from 'react';

import { Tag } from '@/types';
import { getTags } from '@/services/dance';

interface SourcesContextType {
  tags: Tag[];
  selectedTags: string[];
  selectTag: (tag: string) => void;
  unselectTag: (tag: string) => void;
}

export const sourcesContext = createContext<SourcesContextType>(
  {} as SourcesContextType
);

interface Props {
  children: React.ReactNode;
}

const SourcesContextProvider = ({ children }: Props) => {
  const [tags, setTags] = useState<Tag[]>([]);

  useEffect(() => {
    getTags().then((response) => {
      setTags(response.data);
    });
  }, []);

  const [selectedTags, setSelectedTags] = useState<string[]>([]);

  const selectTag = (tag: string) => {
    setSelectedTags((prev) => {
      return [...prev, tag];
    });
  };

  const unselectTag = (tag: string) => {
    setSelectedTags((prev) => {
      return prev.filter((t) => t !== tag);
    });
  };

  const value: SourcesContextType = {
    tags,
    selectedTags,
    selectTag,
    unselectTag,
  };

  return (
    <sourcesContext.Provider value={value}>{children}</sourcesContext.Provider>
  );
};

export default SourcesContextProvider;
