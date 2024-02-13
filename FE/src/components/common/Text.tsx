import styled from 'styled-components';

const M = styled.div`
  max-height: 100%;
  max-width: 100%;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
`;

const S = styled(M)`
  font-size: var(--font-size-s);
`;

const L = styled(M)`
  font-size: var(--font-size-l);
`;

const XL = styled(M)`
  font-size: var(--font-size-xl);
  line-height: var(--font-size-xl);
`;

const Secondary = styled(S)`
  color: var(--text-secondary-dark-mode);
`;

export { M, S, L, XL, Secondary };
