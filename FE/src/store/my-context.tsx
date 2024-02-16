import { createContext, useState } from 'react';
import { useLoaderData } from 'react-router-dom';

import { Community, Tab, Modal } from '@/types';

interface MyContextType {
  selectDance: (idx: number) => void;
  dances: Community[];
  openModal: () => void;
  modal: Modal;
  tabs: Tab[];
  selectAll: () => void;
  selectPublic: () => void;
  selectPrivate: () => void;
  filter: string;
  setFilterName: (name: string) => void;
}

export const myContext = createContext({} as MyContextType);

interface Props {
  children: React.ReactNode;
}

const MyContextProvider = ({ children }: Props) => {
  const feed = useLoaderData() as Community[];
  const source = feed.filter((item) => item.feedType === 'SOURCE');
  const repertory = feed.filter((item) => item.feedType === 'REPERTORY');

  const [feedClicked, setFeedClicked] = useState(true);
  const [sourceClicked, setSourceClicked] = useState(false);
  const [repertoryClicked, setRepertoryClicked] = useState(false);

  const [dances, setDances] = useState(feed);

  const [filter, setFilter] = useState('Filter');

  const clickFeed = () => {
    setDances(feed);
    setFeedClicked(true);
    setSourceClicked(false);
    setRepertoryClicked(false);
    setFilter('Filter');
  };

  const clickSource = () => {
    setDances(source);
    setFeedClicked(false);
    setSourceClicked(true);
    setRepertoryClicked(false);
    setFilter('Filter');
  };

  const clickRepertory = () => {
    setDances(repertory);
    setFeedClicked(false);
    setSourceClicked(false);
    setRepertoryClicked(true);
    setFilter('Filter');
  };

  const [index, setIndex] = useState(0);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const selectDance = (idx: number) => {
    setIndex(idx);
  };

  const nextDance = () => {
    if (index < dances.length - 1) {
      setIndex((prev) => prev + 1);
    }
  };

  const prevDance = () => {
    if (index > 0) {
      setIndex((prev) => prev - 1);
    }
  };

  const selectAll = () => {
    if (sourceClicked) {
      setDances(source);
    } else if (repertoryClicked) {
      setDances(repertory);
    } else {
      setDances(feed);
    }
  };

  const selectPublic = () => {
    let items = feed;
    if (sourceClicked) {
      items = source;
    } else if (repertoryClicked) {
      items = repertory;
    }
    setDances(items.filter((item) => !item.feedDisable));
  };

  const selectPrivate = () => {
    let items = feed;
    if (sourceClicked) {
      items = source;
    } else if (repertoryClicked) {
      items = repertory;
    }
    setDances(items.filter((item) => item.feedDisable));
  };

  const setFilterName = (name: string) => {
    setFilter(name);
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const tabs = [
    new Tab('Feed', feedClicked, clickFeed),
    new Tab('Repertory', repertoryClicked, clickRepertory),
    new Tab('Source', sourceClicked, clickSource),
  ];

  const modal: Modal = {
    isOpen: isModalOpen,
    closeModal,
    dances,
    index,
    nextDance,
    prevDance,
  };

  const value: MyContextType = {
    selectDance,
    dances,
    openModal,
    modal,
    tabs,
    selectAll,
    selectPublic,
    selectPrivate,
    filter,
    setFilterName,
  };

  return <myContext.Provider value={value}>{children}</myContext.Provider>;
};

export default MyContextProvider;
