import { useState } from 'react';
import styled, { css } from 'styled-components';

import TabLink, { SidebarTabStyle } from '@/components/sidebar/TabLink';
import TabIcon from '@/components/sidebar/TabIcon';

export const ToggleButton = styled.button`
  ${SidebarTabStyle}
  width: 100%;
`;

const ListItem = styled.li<{ visible: boolean }>`
  margin-left: 32px;
  ${({ visible }) => {
    if (!visible) {
      return css`
        display: none;
      `;
    }
  }}
`;

export class SidebarTab {
  name: string;
  path: string;

  constructor(name: string, path: string) {
    this.name = name;
    this.path = path;
  }
}

interface Props {
  groupName: string;
  tabGroup: SidebarTab[];
}

const TabGroup = ({ groupName, tabGroup }: Props) => {
  const [toggle, setToggle] = useState(true);

  const handleClick = () => {
    setToggle((prev) => !prev);
  };

  return (
    <>
      <li>
        <ToggleButton onClick={handleClick}>
          <TabIcon open={toggle} />
          {groupName}
        </ToggleButton>
      </li>
      {tabGroup.map((tab, idx) => {
        return (
          <ListItem key={idx} visible={toggle}>
            <TabLink path={tab.path}>{tab.name}</TabLink>
          </ListItem>
        );
      })}
    </>
  );
};

export default TabGroup;
