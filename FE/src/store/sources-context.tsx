import { createContext, useState } from 'react';
import { useLoaderData } from 'react-router-dom';

import { Source, Tab } from '@/types';

const TABS: Tab[] = [
  new Tab('My Sources', true),
  new Tab('Cloned Sources', false),
];

interface SourcesContextType {
  sources: Source[];
  tags: string[];
  selectedTags: string[];
  selectTag: (tag: string) => void;
  unselectTag: (tag: string) => void;
  isTagOpen: boolean;
  openTag: () => void;
  tabs: Tab[];
  selectTab: (tab: Tab) => void;
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
  const [tabs, setTabs] = useState<Tab[]>(TABS);

  const data = useLoaderData() as { mine: Source[]; clone: Source[] };

  const sources = tabs[0].clicked ? data.mine : data.clone;

  const tags = [
    ...new Set(
      sources.map((source) => source.tagList.map((tag) => tag.tagName)).flat()
    ),
  ];

  const selectTab = (clickedTab: Tab) => {
    if (clickedTab.clicked) {
      return;
    }
    setTabs(
      tabs.map((tab) => {
        const clicked: boolean = tab.name == clickedTab.name;
        return {
          ...tab,
          clicked,
        };
      })
    );
    setSelectedTags([]);
    setIsTagOpen(false);
  };

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
    selectTab,
  };

  return (
    <sourcesContext.Provider value={value}>{children}</sourcesContext.Provider>
  );
};

export default SourcesContextProvider;
