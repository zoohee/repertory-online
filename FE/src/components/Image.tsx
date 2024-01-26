import styled, { css } from 'styled-components';

const ImageContainer = styled.div`
  ${(props) => {
    if (props.size) {
      return css`
        height: ${props.size}px;
        width: ${props.size}px;
        aspect-ratio: 1 / 1;
      `;
    }
    return css`
      width: 100%;
    `;
  }};

  img {
    object-fit: cover;
    ${(props) => {
      if (props.isRound) {
        return css`
          border-radius: 50%;
        `;
      }
    }}
    height: 100%;
    width: 100%;
  }
`;

const Image = ({
  size,
  src,
  isRound,
}: {
  size: number;
  src: string;
  isRound: boolean;
}) => {
  return (
    <ImageContainer size={size} isRound={isRound}>
      <img src={src} alt="profile-image" />
    </ImageContainer>
  );
};

export default Image;
