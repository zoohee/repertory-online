import { ReactNode, useState } from 'react';
import { NavLink } from 'react-router-dom';
import styled, { css } from 'styled-components';

export const Tab = styled.li`
  background-color: transparent;
  text-align: center;
  padding: 16px;

  &:hover {
    border-radius: 10px;
    background-color: #353637;
  }

  &:active {
    color: var(--rp-yellow);
  }

  a {
    text-decoration-line: none;
  }

  a.active {
    color: var(--rp-yellow);
  }
`;

const ToggleTab = styled(Tab)<{ visible: boolean }>`
  margin-left: 32px;
  ${({ visible }) => {
    if (!visible) {
      return css`
        display: none;
      `;
    }
  }}
`;

interface TabLinkProps {
  children: ReactNode;
  path: string;
}

export const TabLink = ({ children, path }: TabLinkProps) => {
  return (
    <NavLink
      to={path}
      className={({ isActive }) => (isActive ? 'active' : undefined)}
    >
      {children}
    </NavLink>
  );
};

export class SidebarTab {
  name: string;
  path: string;

  constructor(name: string, path: string) {
    this.name = name;
    this.path = path;
  }
}

interface TabGroupProps {
  groupName: string;
  tabGroup: SidebarTab[];
}

const TabGroup = ({ groupName, tabGroup }: TabGroupProps) => {
  const [toggle, setToggle] = useState(true);

  const handleClick = () => {
    setToggle((prev) => !prev);
  };

  return (
    <>
      <Tab onClick={handleClick}>{groupName}</Tab>
      {tabGroup.map((tab, idx) => {
        return (
          <ToggleTab key={idx} visible={toggle}>
            <TabLink path={tab.path}>{tab.name}</TabLink>
          </ToggleTab>
        );
      })}
    </>
  );
};

export default TabGroup;
