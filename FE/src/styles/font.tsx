import { css } from 'styled-components';

const primaryFont = {
  light: css`
    font-family: 'YdestreetL';
  `,
  bold: css`
    font-family: 'YdestreetB';
  `,
};

const secondaryFont = {
  light: css`
    font-family: 'Pretendard';
    font-weight: 100;
  `,
};

const fontSize = {
  s: css`
    font-size: 0.8rem;
  `,
  m: css`
    font-size: 1rem;
  `,
  l: css`
    font-size: 1.2rem;
  `,
  xl: css`
    font-size: 1.5rem;
    line-height: 24px;
  `,
};

export { primaryFont, secondaryFont, fontSize };
