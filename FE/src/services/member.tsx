import { $axios } from './config';

// 회원가입
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

// 로그인
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
