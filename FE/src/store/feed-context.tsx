import { createContext, useState } from 'react';
import { useLoaderData } from 'react-router-dom';

import { Community, Profile, Modal } from '@/types';

interface FeedContextType {
  selectDance: (idx: number) => void;
  dances: Community[];
  profile: Profile;
  openModal: () => void;
  modal: Modal;
}

export const feedContext = createContext({} as FeedContextType);

interface Props {
  children: React.ReactNode;
}

const FeedContextProvider = ({ children }: Props) => {
  const { dances, profile } = useLoaderData() as {
    dances: Community[];
    profile: Profile;
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

  const modal: Modal = {
    isOpen: isModalOpen,
    closeModal,
    dances,
    index,
    nextDance,
    prevDance,
  };

  const value: FeedContextType = {
    selectDance,
    dances,
    profile,
    openModal,
    modal,
  };

  return <feedContext.Provider value={value}>{children}</feedContext.Provider>;
};

export default FeedContextProvider;
