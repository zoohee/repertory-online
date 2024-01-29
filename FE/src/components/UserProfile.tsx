import { ReactNode } from 'react';
import styled from 'styled-components';

import ProfileImage from '@/components/Image';

const Box = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-left: 8px;
  justify-content: center;
`;

interface Props {
  children: ReactNode;
  imageSize: number;
  imageSrc: string;
}

const UserProfile = ({ children, imageSize, imageSrc }: Props) => {
  return (
    <>
      <ProfileImage size={imageSize} isRound={true} src={imageSrc} />
      <Box>{children}</Box>
    </>
  );
};

export default UserProfile;
