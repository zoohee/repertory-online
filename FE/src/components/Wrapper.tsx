import styled from 'styled-components';

const Wrapper = styled.div<{ margin: string }>`
  margin: ${(props) => props.margin};
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export default Wrapper;
