import { ReactNode } from 'react';
import { NavLink } from 'react-router-dom';
import styled, { css } from 'styled-components';

import TabIcon from '@/components/sidebar/TabIcon';

export const SidebarTabStyle = css`
  height: var(--sidebar-nav-height);
  display: flex;
  align-items: center;
  background-color: transparent;
  padding: var(--sidebar-nav-padding);
  &:hover {
    border-radius: 10px;
    background-color: var(--sidebar-nav-hover);
  }

  &:active {
    background-color: var(--sidebar-nav-active);
  }
`;

const Link = styled(NavLink)`
  ${SidebarTabStyle}
  text-decoration-line: none;

  &.active {
    color: var(--rp-yellow);
    svg {
      color: var(--rp-yellow);
    }
  }
`;

interface Props {
  children: ReactNode;
  path: string;
}

export const TabLink = ({ children, path }: Props) => {
  return (
    <li>
      <Link
        to={path}
        className={({ isActive }) => (isActive ? 'active' : undefined)}
      >
        <TabIcon />
        {children}
      </Link>
    </li>
  );
};

export default TabLink;
