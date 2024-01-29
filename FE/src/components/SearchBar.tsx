import styled from 'styled-components';
import SearchIcon from '@mui/icons-material/Search';

import { primaryFont, secondaryFont } from '@/styles/font';
import { boxShadow } from '@/styles/shadow';

const Wrapper = styled.div`
  width: 480px;
  margin: 24px;
  background-color: var(--background-dark-mode);
  display: flex;
  align-items: center;
  padding: 4px;
  ${boxShadow}
  border-radius: 5px;
`;

const Input = styled.input`
  width: 100%;
  margin: 4px;
  background-color: transparent;
  ${secondaryFont.light}
  font-size: 1rem;
  border: 0;

  &:focus {
    outline: none;
  }

  &::placeholder {
    ${primaryFont.light}
  }
`;

const Button = styled.button`
  margin-right: 4px;
  background-color: transparent;
  border: none;
  display: flex;
  justify-content: center;

  svg {
    color: var(--rp-grey-300);
  }

  svg:hover {
    color: var(--rp-white);
  }
`;

const SearchBar = () => {
  return (
    <Wrapper>
      {/* TODO: 검색 기준 넣기 */}
      <Input type="text" placeholder="search" />
      <Button>
        <SearchIcon />
      </Button>
    </Wrapper>
  );
};

export default SearchBar;
