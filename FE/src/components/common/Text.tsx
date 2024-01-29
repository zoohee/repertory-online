import styled from 'styled-components';

import { fontSize } from '@/styles/font';

const Text = styled.div<{ size: string; color: string }>`
  ${({ size }) => {
    if (size === 's') {
      return fontSize.s;
    }
    return fontSize.m;
  }};

  ${({ color }) => {
    if (color === 's') {
      return 'color: var(--text-secondary-dark-mode)';
    }
    return 'color: var(--text-primary-dark-mode)';
  }};
`;

export default Text;
