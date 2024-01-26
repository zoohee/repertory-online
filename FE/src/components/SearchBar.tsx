import styled from 'styled-components';
import SearchIcon from '@mui/icons-material/Search';

import { primaryFont, secondaryFont } from '@/styles/font';

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  border: solid 2px var(--rp-white);
  border-radius: 5px;
`;

const Input = styled.input`
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

  svg:hover {
    color: var(--rp-yellow);
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
