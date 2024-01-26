import { ReactNode } from 'react';
import styled from 'styled-components';

const Dances = styled.ul`
  padding: 0 16px;
  width: 100%;
  display: grid;
  grid-template-columns: ${(props) => `repeat(${props.column}, 1fr)`};
  grid-gap: 16px;
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
