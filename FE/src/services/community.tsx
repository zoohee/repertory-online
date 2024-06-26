import { $auth } from './config';
interface IPage {
  page: number;
  pageSize: number;
}
interface IfeedData {
  originId: number;
  feedType: string;
  feedDisable: 0 | 1;
}

// 구독자 수 조회 -OK
const getSubscribersCount = async () => {
  const response = await $auth().get('/community/subscribers');
  console.log(response);
  return response;
};

// 다른 유저 구독 - 500
const postSubscriber = async (memberId: number) => {
  const data = {
    selectedMemberId: memberId,
  };
  const response = await $auth().post('/community/subscribe', data);
  console.log(response);
  return response;
};

// 다른 유저 구독 취소 - 500
const deleteSubscriber = async (memberId: number) => {
  const data = {
    selectedMemberId: memberId,
  };
  const response = await $auth().delete('/community/subscribe', { data });
  console.log(response);
  return response;
};

// 피드 좋아요
const patchFeedLike = async (feedId: number) => {
  const response = await $auth().patch(`/community/feed/${feedId}/like`);
  console.log(response);
  return response;
};

// 피드 좋아요 취소
const patchFeedNotLike = async (feedId: number) => {
  const response = await $auth().patch(`/community/feed/${feedId}/notlike`);
  console.log(response);
  return response;
};

// 소스 클론
const postSourceClone = async (feedId: number) => {
  const response = await $auth().post(`/community/source/${feedId}/clone`);
  console.log(response);
  return response;
};
// 구독한 사람 피드 조회-OK
const getSubscribersFeed = async ({ page, pageSize }: IPage) => {
  const response = await $auth().get(
    `/community/feed/subscribe/${page}/${pageSize}`,
    {
      params: { page, pageSize },
    }
  );
  console.log(response);
  return response;
};

// 피드목록 조회
const getCommunityFeed = async ({ page, pageSize }: IPage) => {
  const response = await $auth().get(`/community/feed/${page}/${pageSize}`, {
    params: { page, pageSize },
  });
  console.log(response);
  return response;
};

// 유저 피드 공개
const feedSetPublic = async (originId: number, feedType: string) => {
  const data = {
    originId: originId,
    feedType: feedType,
  };
  const response = await $auth().post('/community/feed/disable', data);
  console.log(response);
  return response;
};
// 유저 피드 비공개
const feedSetPrivate = async (originId: number, feedType: string) => {
  const data = {
    originId: originId,
    feedType: feedType,
  };
  const response = await $auth().patch('/community/feed/disable', data);
  console.log(response);
  return response;
};
// 커뮤니티 동영상 조회
const getFeedVideo = async (feedId: number) => {
  const response = await $auth().get(`/community/feed/detail/${feedId}`);
  console.log(response.data);
  return response;
};
// 피드 데이터 저장
const saveFeed = async () => {
  const feedData: IfeedData = {
    originId: 29,
    feedType: 'REPERTORY',
    feedDisable: 1,
  };
  const response = await $auth().post(`/community/feed`, feedData);
  console.log(response.data);
  return response;
};
// 내가 구독한 유저 목록 조회
const getSubscribersList = async () => {
  const response = await $auth().get(`/community/subscribe/list`);
  console.log(response.data);
  return response;
};
// 유저 피드 프로필 조회
const getFeedList = async (memberId: number) => {
  const response = await $auth().get(`/community/feed/profile/${memberId}`);
  console.log(response.data);
  return response;
};
const getFeedProfile = async (memberId: number) => {
  const response = await $auth().get(
    `/community/feed/profile/subscriber/${memberId}`
  );
  console.log(response.data);
  return response;
};
// 동영상 검색
const getFeedSearch = async (keyword: string) => {
  const response = await $auth().get(`/community/search/tag/${keyword}`);
  console.log(response.data);
  return response;
};

export {
  getSubscribersCount,
  postSubscriber,
  deleteSubscriber,
  patchFeedLike,
  patchFeedNotLike,
  postSourceClone,
  getSubscribersFeed,
  getCommunityFeed,
  feedSetPublic,
  feedSetPrivate,
  getFeedVideo,
  saveFeed,
  getSubscribersList,
  getFeedList,
  getFeedProfile,
  getFeedSearch,
};
