import { ReactNode } from 'react';
import styled from 'styled-components';

const GridBox = styled.ul<{ column: number }>`
  padding: 0 16px;
  width: 100%;
  display: grid;
  grid-template-columns: ${({ column }) =>
    `repeat(max(${column - 3}, 1), 1fr)`};
  grid-gap: 16px;

  @media (min-width: 640px) {
    grid-template-columns: ${({ column }) => `repeat(${column - 2}, 1fr)`};
  }
  @media (min-width: 768px) {
    grid-template-columns: ${({ column }) => `repeat(${column - 1}, 1fr)`};
  }
  @media (min-width: 1024px) {
    grid-template-columns: ${({ column }) => `repeat(${column}, 1fr)`};
  }
`;

interface Props {
  children: ReactNode;
  column: number;
}

const DanceGridBox = ({ children, column }: Props) => {
  return <GridBox column={column}>{children}</GridBox>;
};

export default DanceGridBox;
