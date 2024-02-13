import { ReactNode } from 'react';
import styled from 'styled-components';

import { fontSize } from '@/styles/font';
import Thumbnail from '@/components/common/Image';

const Box = styled.div`
  width: 100%;
  border-radius: 10px;
  box-shadow: var(--box-shadow);
  overflow: hidden;
`;

const Title = styled.div`
  margin: 16px;
  ${fontSize.xl}
`;

const Detail = styled.div`
  padding: 0 16px 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
`;

interface Props {
  children: ReactNode;
  thumbnail: string;
  title: string;
}

const Dance = ({ children, thumbnail, title }: Props) => {
  return (
    <Box>
      <Thumbnail src={thumbnail}></Thumbnail>
      <Title>{title}</Title>
      <Detail>{children}</Detail>
    </Box>
  );
};

export default Dance;
