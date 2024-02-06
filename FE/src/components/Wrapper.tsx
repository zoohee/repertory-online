import styled from 'styled-components';

const Wrapper = styled.div<{ $margin: string }>`
  margin: ${({$margin}) => $margin};
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
`;

export default Wrapper;
