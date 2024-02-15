import { ReactNode } from 'react';
import styled from 'styled-components';

interface Props {
  children: ReactNode;
}

const List = styled.ul`
  display: flex;
  flex-direction: column;
  border-radius: var(--border-radius-small);
  padding: 8px;
  background-color: var(--menu-color);
  position: absolute;
  top: calc(var(--button-square) / 2 + var(--button-square-margin));
  right: calc(var(--button-square) / 2 + var(--button-square-margin));
  z-index: 2;
`;

const MenuList = ({ children }: Props) => {
  return <List>{children}</List>;
};

export default MenuList;
