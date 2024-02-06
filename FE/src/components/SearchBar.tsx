import styled from 'styled-components';
import SearchIcon from '@mui/icons-material/Search';

import { primaryFont, secondaryFont } from '@/styles/font';

const Wrapper = styled.div`
  height: var(--searchbar-height);
  width: var(--searchbar-width);
  background-color: var(--background-color);
  display: flex;
  align-items: center;
  padding: 4px;
  box-shadow: var(--box-shadow);
  border-radius: 5px;
`;

const Input = styled.input`
  width: 100%;
  margin: 4px 8px;
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
