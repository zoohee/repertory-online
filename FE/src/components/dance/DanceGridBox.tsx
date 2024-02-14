import { ReactNode } from 'react';
import styled from 'styled-components';

const GridBox = styled.ul<{ $column: number }>`
  width: 100%;
  display: grid;
  grid-template-columns: repeat(
    var(--grid-column),
    calc(
      (100% - var(--grid-gap) * (var(--grid-column) - 1)) / var(--grid-column)
    )
  );
  grid-gap: var(--grid-gap);

  --grid-gap: 16px;
  --grid-column: ${({ $column }) => {
    const size = $column - 3;
    if (size > 0) {
      return size;
    }
    return 1;
  }};
  @media (min-width: 640px) {
    --grid-column: ${({ $column }) => $column - 2};
  }
  @media (min-width: 768px) {
    --grid-column: ${({ $column }) => $column - 1};
  }
  @media (min-width: 1024px) {
    --grid-column: ${({ $column }) => $column};
  }
`;

interface Props {
  children: ReactNode;
  column: number;
}

const DanceGridBox = ({ children, column }: Props) => {
  return <GridBox $column={column}>{children}</GridBox>;
};

export default DanceGridBox;
