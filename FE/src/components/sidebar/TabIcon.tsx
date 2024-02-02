import styled from 'styled-components';
import KeyboardArrowRightIcon from '@mui/icons-material/KeyboardArrowRight';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';

const IconBox = styled.div`
  margin-right: var(--sidebar-nav-padding);
`;

const TabIcon = ({ open }: { open?: boolean }) => {
  return (
    <IconBox>
      {!open && <KeyboardArrowRightIcon />}
      {open && <KeyboardArrowDownIcon />}
    </IconBox>
  );
};

export default TabIcon;
