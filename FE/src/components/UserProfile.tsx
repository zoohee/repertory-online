import { Link } from 'react-router-dom';
import styled from 'styled-components';

import URL from '@/url';
import ProfileImage from '@/components/common/ImageSquare';
import { Member } from '@/types';

const Box = styled.div`
  display: flex;
`;

const InnerBox = styled(Box)`
  display: flex;
  flex-direction: column;
  margin-left: 8px;
  justify-content: center;
`;

const StyledLink = styled(Link)`
  text-decoration: none;
  margin-bottom: 4px;
`;

interface Props {
  children: React.ReactNode;
  imageSize: number;
  member: Member;
  name: JSX.Element;
}

const UserProfile = ({ children, imageSize, member, name }: Props) => {
  const stopBubbling = (e: React.MouseEvent<HTMLAnchorElement>) => {
    e.stopPropagation();
  };

  return (
    <Box>
      <Link
        to={`${URL.communityFeed}/${member.memberId}`}
        onClick={stopBubbling}
      >
        <ProfileImage
          size={imageSize}
          isRound={true}
          src={member.memberProfile}
        />
      </Link>
      <InnerBox>
        <StyledLink
          to={`${URL.communityFeed}/${member.memberId}`}
          onClick={stopBubbling}
        >
          {name}
        </StyledLink>
        {children}
      </InnerBox>
    </Box>
  );
};

export default UserProfile;
