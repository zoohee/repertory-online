import styled from 'styled-components';

import Text from '@/components/common/Text';
import { ReactNode } from 'react';

const ListItem = styled.li`
  width: 100%;
`;

const Button = styled.button`
  height: var(--menu-button-size);
  width: 100%;
  padding: 8px;
  border-radius: var(--border-radius-small);
  background-color: transparent;
  display: flex;
  align-items: center;
  &:hover {
    background-color: var(--menu-hover-color);
  }
`;

const IconBox = styled.div`
  margin-right: 8px;
`;

export class Menu {
  name: string;
  icon: JSX.Element;
  request: () => void;

  constructor(name: string, icon: JSX.Element, request: () => void) {
    this.name = name;
    this.icon = icon;
    this.request = request;
  }
}

interface Props {
  children: ReactNode;
  name: string;
  onClick: (e: React.MouseEvent<HTMLButtonElement>) => void;
}

const MenuButton = ({ children, name, onClick }: Props) => {
  const textColor = name === '삭제' ? 'r' : 'p';
  return (
    <ListItem>
      <Button onClick={onClick}>
        <IconBox>{children}</IconBox>
        <Text size="s" color={textColor}>
          {name}
        </Text>
      </Button>
    </ListItem>
  );
};

export default MenuButton;
