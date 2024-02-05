import { $axios, $auth } from './config';

// 구독자 수 조회 -OK
const getSubscribersCount = async () => {
  const response = await $axios().get('/community/subscribers');
  console.log(response);
  return response;
};

// 다른 유저 구독 - 500
const postSubscriber = async (memberId: number) => {
  const data = {
    selectedMemberId: memberId,
  };
  const response = await $axios().post('/community/subscribe', data);
  console.log(response);
  return response;
};

// 다른 유저 구독 취소 - 500
const deleteSubscriber = async (memberId: number) => {
  const data = {
    selectedMemberId: memberId,
  };
  const response = await $axios().post('/community/subscribe', data);
  console.log(response);
  return response;
};

export { getSubscribersCount, postSubscriber, deleteSubscriber };
