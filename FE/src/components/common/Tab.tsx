import { ReactNode } from 'react';
import styled, { css } from 'styled-components';

import Wrapper from '@/components/Wrapper';
import { Tab } from '@/types';

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

interface Props {
  children?: ReactNode;
  margin: string;
  tabs: Tab[];
}

const Tabs = ({ children, margin, tabs }: Props) => {
  const tabItems = tabs.map((tab, idx) => (
    <li key={idx}>
      <Button $clicked={tab.clicked} onClick={tab.onClick}>
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
