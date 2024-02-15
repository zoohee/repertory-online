import { ReactNode } from 'react';
import ReactDOM from 'react-dom';
import styled from 'styled-components';

// eslint-disable-next-line react-refresh/only-export-components
const DialogOverlay = styled.div`
  z-index: 30000;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
`;

// eslint-disable-next-line react-refresh/only-export-components
const DialogBox = styled.div`
  background-color: black;
  padding: 20px;
  border-radius: 10px;
  width: 500px;
  max-width: 100%;
`;
interface DialogProps {
  open: boolean;
  onClose: (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
  children: ReactNode;
}

export const Dialog = ({ open, onClose, children }: DialogProps) => {
  if (!open) return null;

  return ReactDOM.createPortal(
    <DialogOverlay>
      <DialogBox
        onClick={(e: React.MouseEvent<HTMLDivElement, MouseEvent>) =>
          e.stopPropagation()
        }
      >
        {children}
      </DialogBox>
    </DialogOverlay>,
    document.body
  );
};
