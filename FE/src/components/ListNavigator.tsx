import styled, { css } from 'styled-components';

import { primaryFont, fontSize } from '@/styles/font';
import Wrapper from '@/components/Wrapper';
import { ReactNode } from 'react';

const Nav = styled.nav`
  width: 100%;
  border-bottom: solid 1px var(--rp-grey-300);
`;

const NavItem = styled.button`
  ${primaryFont.bold}
  ${fontSize.l}
  padding: 16px;
  background-color: transparent;
  border: 0;
  ${(props) => {
    if (props.clicked) {
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

class Navigation {
  name: string;
  clicked: boolean;

  constructor(name: string, clicked: boolean) {
    this.name = name;
    this.clicked = clicked;
  }
}

export { Navigation };

const ListNavigator = ({
  children,
  navItems,
  onClickNav,
}: {
  children?: ReactNode;
  navItems: Navigation[];
  onClickNav?: (navName: string) => void;
}) => {
  return (
    <Wrapper margin="48px 0 0">
      <Nav>
        {navItems.map((item, idx) => {
          return (
            <NavItem
              key={idx}
              clicked={item.clicked}
              onClick={() => {
                if (onClickNav) {
                  onClickNav(item.name);
                }
              }}
            >
              {item.name}
            </NavItem>
          );
        })}
      </Nav>
      {children}
    </Wrapper>
  );
};

export default ListNavigator;
