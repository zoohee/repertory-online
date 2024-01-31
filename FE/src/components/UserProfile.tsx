import { ReactNode } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

import URL from '@/url';
import ProfileImage from '@/components/common/Image';
import Text, { TextStyle } from '@/components/common/Text';

const Box = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 8px;
  margin-right: 24px;
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
      <Link to={`${URL.communityFeed}/${member.id}`}>
        <ProfileImage
          size={imageSize}
          isRound={true}
          src={member.profileImage}
        />
      </Link>
      <Box>
        <Link
          to={`${URL.communityFeed}/${member.id}`}
          style={{ textDecorationLine: 'none' }}
        >
          <Text
            size={textStyle.size}
            color={textStyle.color}
            style={{ marginBottom: '4px' }}
          >
            {member.name}
          </Text>
        </Link>
        {children}
      </Box>
    </>
  );
};

export default UserProfile;
