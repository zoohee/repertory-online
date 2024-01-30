import { ReactNode } from 'react';
import styled from 'styled-components';

import { boxShadow } from '@/styles/shadow';
import { fontSize } from '@/styles/font';
import Thumbnail from '@/components/common/Image';
import Detail from '@/components/Wrapper';

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
      <Detail margin="0 16px 16px 16px">{children}</Detail>
    </Box>
  );
};

export default Dance;
