import { $axios } from './config';

const postMember = async () => {
  const data = {
    memberLoginId: 'rlagudwls3469',
    memberPassword: 'Qwer1234@',
    memberName: '김형진',
    memberEmail: 'rlagudwls3469@gmail.com',
  };
  const response = await $axios().post('/member/join', data);
  console.log(response);
  return response;
};

const loginMember = async () => {
  const params = {
    memberLoginId: 'rlagudwls3469',
    memberPassword: 'Qwer1234@',
  };
  const response = await $axios().post('/member/join', params);
  console.log(response);
  return response;
};

export { postMember, loginMember };
