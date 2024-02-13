import { createContext, useState } from 'react';
import { useLoaderData } from 'react-router-dom';

import { Community, Tab, Modal } from '@/types';

interface MyContextType {
  selectDance: (idx: number) => void;
  dances: Community[];
  openModal: () => void;
  modal: Modal;
  tabs: Tab[];
}

export const myContext = createContext({} as MyContextType);

interface Props {
  children: React.ReactNode;
}

const MyContextProvider = ({ children }: Props) => {
  const feed = useLoaderData() as Community[];
  const source = feed.filter((item) => item.feedType === 'SOURCE');
  const repertory = feed.filter((item) => item.feedType === 'REPETORY');

  const [feedClicked, setFeedClicked] = useState(true);
  const [sourceClicked, setSourceClicked] = useState(false);
  const [repertoryClicked, setRepertoryClicked] = useState(false);

  const [dances, setDances] = useState(feed);

  const clickFeed = () => {
    setDances(feed);
    setFeedClicked(true);
    setSourceClicked(false);
    setRepertoryClicked(false);
  };

  const clickSource = () => {
    setDances(source);
    setFeedClicked(false);
    setSourceClicked(true);
    setRepertoryClicked(false);
  };

  const clickRepertory = () => {
    setDances(repertory);
    setFeedClicked(false);
    setSourceClicked(false);
    setRepertoryClicked(true);
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
  };

  return <myContext.Provider value={value}>{children}</myContext.Provider>;
};

export default MyContextProvider;
