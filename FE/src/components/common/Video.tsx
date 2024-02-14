import styled from 'styled-components';

const Player = styled.video`
  width: 100%;
  max-height: calc(100vh * 0.5);
  background-color: black;
`;

interface Props {
  src: string;
}

const Video = ({ src }: Props) => {
  return (
    <Player controls autoPlay muted loop crossOrigin="anonymous" key={src}>
      <source src={src} type="video/mp4" />
    </Player>
  );
};

export default Video;
