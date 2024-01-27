import { ReactNode } from 'react';
import styled, { css } from 'styled-components';

import { fontSize } from '@/styles/font';
import Wrapper from '@/components/Wrapper';

const Tabs = styled.ul`
  width: 100%;
  display: flex;
  border-bottom: solid 1px var(--rp-grey-300);
`;

const TabButton = styled.button<{ clicked: boolean }>`
  ${fontSize.l}
  padding: 16px;
  background-color: transparent;
  border: 0;
  ${({ clicked }) => {
    if (clicked) {
      return css`
        border-bottom: solid 1px var(--rp-white);
        &:hover {
          cursor: none;
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
  tabs: Tab[];
  onClickTab?: (navName: string) => void;
}

const TabComponent = ({ children, tabs, onClickTab }: Props) => {
  const tabButtons = tabs.map((tab, idx) => {
    return (
      <li>
        <TabButton
          key={idx}
          clicked={tab.clicked}
          onClick={() => {
            if (onClickTab) {
              onClickTab(tab.name);
            }
          }}
        >
          {tab.name}
        </TabButton>
      </li>
    );
  });

  return (
    <Wrapper as="nav" margin="48px 0 0">
      <Tabs>{tabButtons}</Tabs>
      {children}
    </Wrapper>
  );
};

export default TabComponent;
