import { useRef, useEffect } from 'react';

import styled from 'styled-components';
import CloseIcon from '@mui/icons-material/Close';

const Close = styled(CloseIcon)`
  color: var(--text-secondary-dark-mode);

  &:hover {
    color: var(--text-primary-dark-mode);
    cursor: pointer;
  }
`;

const Modal = styled.dialog`
  background-color: var(--background-color);
  border: none;
  border-radius: 10px;
  width: 90%;

  &:focus-visible {
    outline: 0;
  }

  &::backdrop {
    background: rgba(0, 0, 0, 0.6);
  }

  @media (min-width: 768px) {
    width: 80%;
  }

  @media (min-width: 1024px) {
    width: 70%;
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
      <Close onClick={onClose} />
    </Modal>
  );
};

export default FeedItemModal;
