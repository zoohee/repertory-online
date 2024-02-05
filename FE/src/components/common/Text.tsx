import styled from 'styled-components';

import { fontSize } from '@/styles/font';

export class TextStyle {
  size: string;
  color: string;

  constructor(size: string, color: string) {
    this.size = size;
    this.color = color;
  }
}

const Text = styled.div<TextStyle>`
  ${({ size }) => {
    if (size === 's') {
      return fontSize.s;
    }
    if (size === 'xl') {
      return fontSize.xl;
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
