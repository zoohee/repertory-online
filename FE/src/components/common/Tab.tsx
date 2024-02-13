import { ReactNode } from 'react';
import styled, { css } from 'styled-components';

import Wrapper from '@/components/Wrapper';

const List = styled.ul`
  width: 100%;
  display: flex;
  border-bottom: solid 1px var(--rp-grey-300);
`;

const Button = styled.button<{ $clicked: boolean }>`
  font-size: var(--font-size-l);
  padding: 16px;
  border: 0;
  ${({ $clicked }) => {
    if ($clicked) {
      return css`
        border-bottom: solid 1px var(--rp-white);
        &:hover {
          cursor: default;
        }
      `;
    }
    return css`
      color: var(--rp-grey-300);
    `;
  }}
`;

export class Tab {
  name: string;
  clicked: boolean;

  constructor(name: string, clicked: boolean) {
    this.name = name;
    this.clicked = clicked;
  }
}

interface Props {
  children?: ReactNode;
  margin: string;
  tabs: Tab[];
  onClickTab?: (tab: Tab) => void;
}

const Tabs = ({ children, margin, tabs, onClickTab }: Props) => {
  const tabItems = tabs.map((tab, idx) => (
    <li key={idx}>
      <Button
        $clicked={tab.clicked}
        onClick={() => {
          if (onClickTab) {
            onClickTab(tab);
          }
        }}
      >
        {tab.name}
      </Button>
    </li>
  ));

  return (
    <Wrapper as="nav" $margin={margin} style={{ width: '100%' }}>
      <List>{tabItems}</List>
      {children}
    </Wrapper>
  );
};

export default Tabs;
