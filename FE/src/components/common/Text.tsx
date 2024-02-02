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
  max-height: 100%;
  max-width: 100%;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;

  ${({ size }) => {
    if (size === 's') {
      return fontSize.s;
    }
    if (size === 'l') {
      return fontSize.l;
    }
    if (size === 'xl') {
      return fontSize.xl;
    }
    return fontSize.m;
  }};

  color: ${({ color }) => {
    if (color === 'r') {
      return 'var(--color-red)';
    }
    if (color === 's') {
      return 'var(--text-secondary-dark-mode)';
    }
    return 'var(--text-primary-dark-mode)';
  }};
`;

export default Text;
