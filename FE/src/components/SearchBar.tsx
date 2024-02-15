import { useRef } from 'react';
import styled from 'styled-components';
import SearchIcon from '@mui/icons-material/Search';

const Wrapper = styled.div`
  height: var(--searchbar-height);
  background-color: var(--background-color);
  display: flex;
  align-items: center;
  padding: 4px;
  box-shadow: var(--box-shadow);
  border-radius: 5px;

  @media (min-width: 1045px) {
    width: var(--searchbar-width);
  }
`;

const Input = styled.input`
  width: 100%;
  margin: 4px 8px;
  font-size: 1rem;
  border: 0;

  &:focus {
    outline: none;
  }

  &::placeholder {
    font-family: var(--font-family-primary);
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

interface Props {
  search: (keyword: string) => void;
}

const SearchBar = ({ search }: Props) => {
  const ref = useRef<HTMLInputElement>(null);

  const handleClick = () => {
    if (!ref.current || !ref.current.value) {
      return;
    }
    search(ref.current.value);
    ref.current.value = '';
  };

  return (
    <Wrapper>
      {/* TODO: 검색 기준 넣기 */}
      <Input type="text" placeholder="search" ref={ref} />
      <Button onClick={handleClick}>
        <SearchIcon />
      </Button>
    </Wrapper>
  );
};

export default SearchBar;
