import { Link } from 'react-router-dom';
import styled from 'styled-components';

import URL from '@/url';
import ProfileImage from '@/components/common/Image';
import Text, { TextStyle } from '@/components/common/Text';

const Box = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 8px;
  justify-content: center;
`;

const StyledLink = styled(Link)`
  text-decoration: none;
`;

interface Props {
  children: React.ReactNode;
  imageSize: number;
  member: {
    id: number;
    name: string;
    profileImage: string;
  };
  textStyle: TextStyle;
}

const UserProfile = ({ children, imageSize, member, textStyle }: Props) => {
  const marginBottom = textStyle.size === 's' ? '4px' : '8px';
  const stopBubbling = (e: React.MouseEvent<HTMLAnchorElement>) => {
    e.stopPropagation();
  };

  return (
    <div style={{ display: 'flex' }}>
      <Link to={`${URL.communityFeed}/${member.id}`} onClick={stopBubbling}>
        <ProfileImage
          size={imageSize}
          isRound={true}
          src={member.profileImage}
        />
      </Link>
      <Box>
        <StyledLink
          to={`${URL.communityFeed}/${member.id}`}
          onClick={stopBubbling}
        >
          <Text
            size={textStyle.size}
            color={textStyle.color}
            style={{ marginBottom: `${marginBottom}` }}
          >
            {member.name}
          </Text>
        </StyledLink>
        {children}
      </Box>
    </div>
  );
};

export default UserProfile;
