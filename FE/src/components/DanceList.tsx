import styled from 'styled-components';

import { fontSize } from '@/styles/font';
import { boxShadow } from '@/styles/shadow';
import Image from '@/components/Image';

const Dances = styled.ul`
  padding: 0 16px;
  width: 100%;
  display: grid;
  grid-template-columns: ${(props) => `repeat(${props.column}, 1fr)`};
  grid-gap: 16px;
`;

const Dance = styled.li`
  width: 100%;
  border-radius: 10px;
  ${boxShadow}
  overflow: hidden;
`;

const Title = styled.div`
  margin: 16px;
  ${fontSize.l}
`;

const Detail = styled.div`
  color: var(--rp-grey-300);
  ${fontSize.s}
  margin: 16px;
  margin-top: none;
`;

const DanceList = ({
  list,
  column,
}: {
  list: {
    imageUrl: string;
    title: string;
    detail: string;
  }[];
  column: number;
}) => {
  return (
    <Dances column={column}>
      {list.map((item, idx) => {
        return (
          <Dance key={idx}>
            <Image src={item.imageUrl}></Image>
            <Title>{item.title}</Title>
            <Detail>{item.detail}</Detail>
          </Dance>
        );
      })}
    </Dances>
  );
};

export default DanceList;
