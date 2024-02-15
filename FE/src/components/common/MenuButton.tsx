import styled from 'styled-components';

import { S } from '@/components/common/Text';

const Text = styled(S)<{ $name: string }>`
  ${({ $name }) => {
    if ($name === '삭제') {
      return 'color: var(--color-red);';
    }
  }}
`;

const ListItem = styled.li`
  width: 100%;
`;

const Button = styled.button`
  height: var(--menu-button-size);
  width: 100%;
  padding: 8px;
  border-radius: var(--border-radius-small);
  display: flex;
  align-items: center;
  &:hover {
    background-color: var(--menu-hover-color);
  }
`;

const IconBox = styled.div`
  margin-right: 8px;
`;

interface Props {
  children: React.ReactNode;
  name: string;
  onClick: () => void;
}

const MenuButton = ({ children, name, onClick }: Props) => {
  return (
    <ListItem>
      <Button onClick={onClick}>
        <IconBox>{children}</IconBox>
        <Text $name={name}>{name}</Text>
      </Button>
    </ListItem>
  );
};

export default MenuButton;
