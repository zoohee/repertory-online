import { Link } from 'react-router-dom';
import styled from 'styled-components';

import URL from '@/url';
import ProfileImage from '@/components/common/ImageSquare';
import { Member } from '@/types';

const Box = styled.div`
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
    <div style={{ display: 'flex' }}>
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
      <Box>
        <StyledLink
          to={`${URL.communityFeed}/${member.memberId}`}
          onClick={stopBubbling}
        >
          {name}
        </StyledLink>
        {children}
      </Box>
    </div>
  );
};

export default UserProfile;
