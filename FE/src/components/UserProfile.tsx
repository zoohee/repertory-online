import { ReactNode } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

import ProfileImage from '@/components/common/Image';
import Text, { TextStyle } from '@/components/common/Text';

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
  member: {
    id: number;
    name: string;
    profileImage: string;
  };
  textStyle: TextStyle;
}

const UserProfile = ({ children, imageSize, member, textStyle }: Props) => {
  return (
    <>
      <Link to={`/community/m/${member.id}`}>
        <ProfileImage
          size={imageSize}
          isRound={true}
          src={member.profileImage}
        />
      </Link>
      <Box>
        <Link
          to={`/community/m/${member.id}`}
          style={{ textDecorationLine: 'none' }}
        >
          <Text size={textStyle.size} color={textStyle.color}>
            {member.name}
          </Text>
        </Link>
        {children}
      </Box>
    </>
  );
};

export default UserProfile;
