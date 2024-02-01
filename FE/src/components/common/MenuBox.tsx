import { ReactNode } from 'react';
import styled, { RuleSet } from 'styled-components';

interface Props {
  css: RuleSet;
  children: ReactNode;
}

const List = styled.menu<Props>`
  display: flex;
  flex-direction: column;
  border-radius: var(--border-radius-small);
  padding: 8px;
  background-color: var(--menu-color);
  ${({ css }) => css}
`;

const Menu = ({ css, children }: Props) => {
  return <List css={css}>{children}</List>;
};

export default Menu;
