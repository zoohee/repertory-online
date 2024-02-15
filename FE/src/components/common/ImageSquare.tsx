import styled, { css } from 'styled-components';

const ImageContainer = styled.div<{ $size?: number; $isRound?: boolean }>`
  aspect-ratio: 1 / 1;
  ${({ $size }) => {
    if ($size) {
      return css`
        height: ${$size}px;
        width: ${$size}px;
      `;
    }
    return css`
      width: 100%;
    `;
  }};

  img {
    object-fit: cover;
    ${({ $isRound }) => {
      if ($isRound) {
        return css`
          border-radius: 50%;
        `;
      }
    }}
    height: 100%;
    width: 100%;
  }
`;

interface Props {
  size?: number;
  src: string;
  isRound?: boolean;
}

const Image = ({ size, src, isRound }: Props) => {
  return (
    <ImageContainer $size={size} $isRound={isRound}>
      <img src={src} alt='image' crossOrigin='anonymous' />
    </ImageContainer>
  );
};

export default Image;
