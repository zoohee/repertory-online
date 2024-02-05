import styled from 'styled-components';
import AddBoxIcon from '@mui/icons-material/AddBox';

import { buttonStyles } from '../common/Button';

const Button = styled.button`
  ${buttonStyles.default}
  padding: var(--button-padding);
  border-radius: var(--button-border-radius);
  display: flex;
  align-items: center;
`;

const CreateButton = () => {
  return (
    <Button>
      <AddBoxIcon style={{marginRight: '4px'}}/>
      생성하기
    </Button>
  );
};

export default CreateButton;
