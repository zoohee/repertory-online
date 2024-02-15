import { useRef, useEffect } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import CloseIcon from '@mui/icons-material/Close';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';

import Video from '@/components/common/Video';
import * as Text from '@/components/common/Text';
import Download from '@/components/community/Download';
import Like from '@/components/community/Like';
import URL from '@/url';
import { Community, Modal } from '@/types';
import { deriveDaysAgo } from '@/services/util';

const Close = styled(CloseIcon)`
  z-index: 100;
  position: absolute;
  right: 16px;
  top: 16px;
  color: var(--text-secondary-dark-mode);

  &:hover {
    color: var(--text-primary-dark-mode);
    cursor: pointer;
  }
`;

const StyledLink = styled(Link)`
  text-decoration: none;
`;

const Detail = styled(Text.M)`
  margin-top: 8px;
  color: var(--text-secondary-dark-mode);
`;

const FlexBox = styled.div`
  display: flex;
  align-items: center;
`;

const Wrapper = styled(FlexBox)`
  width: 100%;
  justify-content: space-between;
  padding: 12px 16px;
`;

const ColumnBox = styled(FlexBox)`
  flex-direction: column;
  align-items: flex-start;
`;

const Container = styled(FlexBox)`
  width: 100%;
  height: 100%;
  justify-content: space-evenly;
`;

const ButtonBox = styled.div`
  width: 40px;
`;

const Content = styled(ColumnBox)`
  overflow: hidden;
  position: relative;
  background-color: var(--background-color);
  border-radius: 10px;
  width: 90%;
  @media (min-width: 768px) {
    width: 80%;
  }

  @media (min-width: 1024px) {
    width: 70%;
  }
`;

const Dialog = styled.dialog`
  width: 100%;
  height: 100%;
  padding: 0;
  background-color: transparent;
  border: none;

  &:focus-visible {
    outline: 0;
  }

  &::backdrop {
    background: rgba(0, 0, 0, 0.7);
  }
`;

const Title = ({ dance }: { dance: Community }) => {
  if (dance.feedDisable) {
    return <Text.XL>{dance.feedName}</Text.XL>;
  }
  return (
    <StyledLink to={`${URL.communityDetail}/${dance.feedId}`}>
      <Text.XL>{dance.feedName}</Text.XL>
    </StyledLink>
  );
};

interface Props {
  modal: Modal;
  disable?: boolean;
}

const FeedItemModal = ({ modal, disable }: Props) => {
  const { isOpen, closeModal, prevDance, nextDance, dances, index } = modal;

  const dance = dances[index];

  const ref = useRef<HTMLDialogElement>(null);
  useEffect(() => {
    if (isOpen) {
      ref.current?.showModal();
    } else {
      ref.current?.close();
    }
  }, [isOpen]);

  return (
    <Dialog ref={ref} onClose={closeModal}>
      <Container>
        <ButtonBox>
          {index !== 0 && (
            <button onClick={prevDance}>
              <ArrowBackIosIcon fontSize="large" />
            </button>
          )}
        </ButtonBox>
        <Content>
          <Close onClick={closeModal} />
          <Video src={dance.feedUrl} />
          <Wrapper>
            <ColumnBox>
              <Title dance={dance} />
              <Detail>{deriveDaysAgo(dance.feedDate)}</Detail>
            </ColumnBox>
            <FlexBox>
              {dance.feedType === 'SOURCE' && (
                <Download feed={dance} disable={disable} />
              )}
              <Like feed={dance} disable={disable} />
            </FlexBox>
          </Wrapper>
        </Content>
        <ButtonBox>
          {index !== dances.length - 1 && (
            <button onClick={nextDance}>
              <ArrowForwardIosIcon fontSize="large" />
            </button>
          )}
        </ButtonBox>
      </Container>
    </Dialog>
  );
};

export default FeedItemModal;
