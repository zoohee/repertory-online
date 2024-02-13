import { useRef, useEffect, useContext } from 'react';
import styled from 'styled-components';
import CloseIcon from '@mui/icons-material/Close';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';

import { feedContext } from '@/store/feed-context';
import Video from '@/components/common/Video';

const Close = styled(CloseIcon)`
  z-index: 100;
  position: absolute;
  right: 16px;
  top: 16px;
  color: var(--text-secondary-dark-mode);

  &:hover {
    color: var(--text-primary-dark-mode);
    cursor: pointer;
  }
`;

const Container = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
  align-items: center;
  justify-content: space-evenly;
`;

const ButtonBox = styled.div`
  width: 40px;
`;

const Content = styled.div`
  position: relative;
  background-color: var(--background-color);
  border-radius: 10px;
  width: 90%;
  @media (min-width: 768px) {
    width: 80%;
  }

  @media (min-width: 1024px) {
    width: 70%;
  }
`;

const Modal = styled.dialog`
  width: 100%;
  height: 100%;
  padding: 0;
  background-color: transparent;
  border: none;

  &:focus-visible {
    outline: 0;
  }

  &::backdrop {
    background: rgba(0, 0, 0, 0.7);
  }
`;

const FeedItemModal = () => {
  const { isModalOpen, closeModal, prevDance, nextDance, dances, index } =
    useContext(feedContext);

  const ref = useRef<HTMLDialogElement>(null);
  useEffect(() => {
    if (isModalOpen) {
      ref.current?.showModal();
    } else {
      ref.current?.close();
    }
  }, [isModalOpen]);

  return (
    <Modal ref={ref} onClose={closeModal}>
      <Container>
        <ButtonBox>
          {index !== 0 && (
            <button onClick={prevDance}>
              <ArrowBackIosIcon fontSize="large" />
            </button>
          )}
        </ButtonBox>
        <Content>
          <Close onClick={closeModal} />
          <Video src={dances[index].feedUrl} />
        </Content>
        <ButtonBox>
          {index !== dances.length - 1 && (
            <button onClick={nextDance}>
              <ArrowForwardIosIcon fontSize="large" />
            </button>
          )}
        </ButtonBox>
      </Container>
    </Modal>
  );
};

export default FeedItemModal;
