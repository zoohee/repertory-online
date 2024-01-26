import { ReactNode } from 'react';
import styled from 'styled-components';

const Dances = styled.ul`
  padding: 0 16px;
  width: 100%;
  display: grid;
  grid-template-columns: ${(props) =>
    `repeat(max(${props.column - 3}, 1), 1fr)`};
  grid-gap: 16px;

  @media (min-width: 640px) {
    grid-template-columns: ${(props) => `repeat(${props.column - 2}, 1fr)`};
  }
  @media (min-width: 768px) {
    grid-template-columns: ${(props) => `repeat(${props.column - 1}, 1fr)`};
  }
  @media (min-width: 1024px) {
    grid-template-columns: ${(props) => `repeat(${props.column}, 1fr)`};
  }
`;

const DanceList = ({
  children,
  column,
}: {
  children: ReactNode;
  column: number;
}) => {
  return <Dances column={column}>{children}</Dances>;
};

export default DanceList;
