import { ReactNode } from 'react';
import styled from 'styled-components';

import { boxShadow } from '@/styles/shadow';
import { fontSize } from '@/styles/font';
import Thumbnail from '@/components/Image';

const Box = styled.div`
  width: 100%;
  border-radius: 10px;
  ${boxShadow}
  overflow: hidden;
`;

const Title = styled.div`
  margin: 16px;
  ${fontSize.xl}
`;

const Detail = styled.div`
  display: flex;
  margin: 16px;
  margin-top: none;
`;

interface Props {
  children: ReactNode;
  imageUrl: string;
  title: string;
}

const Dance = ({ children, imageUrl, title }: Props) => {
  return (
    <Box>
      <Thumbnail src={imageUrl}></Thumbnail>
      <Title>{title}</Title>
      <Detail>{children}</Detail>
    </Box>
  );
};

export default Dance;
