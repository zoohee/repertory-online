import styled, { css } from 'styled-components';

import { Tab } from '@/types';

const List = styled.ul`
  display: flex;
`;

const Wrapper = styled.nav<{ $margin?: number }>`
  margin-top: var(--menu-tab-margin-top);
  ${({ $margin }) => $margin && `margin-bottom: ${$margin}px;`}
  width: 100%;
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
  marginBottom?: number;
  tabs: Tab[];
}

const Tabs = ({ marginBottom, tabs }: Props) => {
  const tabItems = tabs.map((tab, idx) => (
    <li key={idx}>
      <Button $clicked={tab.clicked} onClick={tab.onClick}>
        {tab.name}
      </Button>
    </li>
  ));

  return (
    <Wrapper $margin={marginBottom}>
      <List>{tabItems}</List>
    </Wrapper>
  );
};

export default Tabs;
