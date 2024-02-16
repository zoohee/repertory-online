import { Link } from 'react-router-dom';
import styled from 'styled-components';
import AddBoxIcon from '@mui/icons-material/AddBox';

import { buttonStyles } from '../common/Button';

const Button = styled(Link)`
  ${buttonStyles.default}
  padding: var(--button-padding);
  border-radius: var(--button-border-radius);
  display: flex;
  align-items: center;
  height: 36px;
  text-decoration: none;
  white-space: nowrap;
`;

interface Props {
  to: string;
  target?: string;
}

const CreateButton = ({ to, target }: Props) => {
  return (
    <Button to={to} target={target}>
      <AddBoxIcon style={{ marginRight: '4px' }} />
      생성하기
    </Button>
  );
};

export default CreateButton;
