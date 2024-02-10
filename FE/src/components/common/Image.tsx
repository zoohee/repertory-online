import styled, { css } from 'styled-components';

const ImageContainer = styled.div<{ $size?: number; $isRound?: boolean }>`
  ${({ $size }) => {
    if ($size) {
      return css`
        height: ${$size}px;
        width: ${$size}px;
        aspect-ratio: 1 / 1;
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
      <img crossOrigin='anonymous' src={src} alt="profile-image" />
    </ImageContainer>
  );
};

export default Image;
