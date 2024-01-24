import styled from 'styled-components';

const ImageContainer = styled.div`
  height: ${(props) => props.size + 'px'};
  width: ${(props) => props.size + 'px'};

  img {
    object-fit: cover;
    border-radius: 50%;
    height: 100%;
    width: 100%;
  }
`;

const ProfileImage = ({ size, src }) => {
  return (
    <ImageContainer size={size}>
      <img src={src} alt="profile-image" />
    </ImageContainer>
  );
};

export default ProfileImage;
