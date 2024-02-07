import { useRef, useEffect } from 'react';

import styled from 'styled-components';
import CloseIcon from '@mui/icons-material/Close';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';

const Close = styled(CloseIcon)`
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

const Video = styled.div`
  height: 200px;
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

interface Props {
  open: boolean;
  onClose: () => void;
}

const FeedItemModal = ({ open, onClose }: Props) => {
  const ref = useRef<HTMLDialogElement>(null);
  useEffect(() => {
    if (open) {
      ref.current?.showModal();
    } else {
      ref.current?.close();
    }
  }, [open]);

  return (
    <Modal ref={ref} onClose={onClose}>
      <Container>
        <button>
          <ArrowBackIosIcon fontSize="large" />
        </button>
        <Content>
          <Close onClick={onClose} />
          <Video />
        </Content>
        <button>
          <ArrowForwardIosIcon fontSize="large" />
        </button>
      </Container>
    </Modal>
  );
};

export default FeedItemModal;
