import { createContext, useState } from 'react';

interface SourcesContextType {
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
    selectedTags,
    selectTag,
    unselectTag,
  };

  return (
    <sourcesContext.Provider value={value}>{children}</sourcesContext.Provider>
  );
};

export default SourcesContextProvider;
