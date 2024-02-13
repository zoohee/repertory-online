import { createContext, useState } from 'react';
import { useLoaderData } from 'react-router-dom';

import { Community } from '@/types';

interface FeedContextType {
  selectDance: (idx: number) => void;
  nextDance: () => void;
  prevDance: () => void;
  dances: Community[];
  index: number;
  isModalOpen: boolean;
  openModal: () => void;
  closeModal: () => void;
}

export const feedContext = createContext({} as FeedContextType);

interface Props {
  children: React.ReactNode;
}

const FeedContextProvider = ({ children }: Props) => {
  const dances = useLoaderData() as Community[];
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

  const value: FeedContextType = {
    selectDance,
    nextDance,
    prevDance,
    dances,
    index,
    isModalOpen,
    openModal,
    closeModal,
  };

  return <feedContext.Provider value={value}>{children}</feedContext.Provider>;
};

export default FeedContextProvider;
