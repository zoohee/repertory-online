import { $axios, $auth } from './config';

interface ILoginData {
  memberLoginId: string;
  memberPassword: string;
}

interface IJoinData {
  memberLoginId: string;
  memberPassword: string;
  memberName: string;
  memberEmail: string;
}
// 회원가입
const postMember = async ({
  memberLoginId,
  memberPassword,
  memberName,
  memberEmail,
}: IJoinData) => {
  const data = {
    memberLoginId: memberLoginId,
    memberPassword: memberPassword,
    memberName: memberName,
    memberEmail: memberEmail,
  };
  const response = await $axios().post('/member/join', data);
  console.log(response);
  return response;
};

// 로그인
const loginMember = async ({ memberLoginId, memberPassword }: ILoginData) => {
  const loginData = new FormData();
  loginData.append('memberLoginId', memberLoginId);
  loginData.append('memberPassword', memberPassword);
  const response = await $axios().post('/member/login', loginData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  console.log(response.headers);
  console.log(response.data);
  const access = response.headers['authorization'];
  const refresh = response.headers['refresh'];
  console.log(access);
  console.log(refresh);
  localStorage.setItem('token', access);
  localStorage.setItem('refresh', refresh);
  // console.log(DecodeJwt(access));
  return response;
};

const getIdValidation = async (memberLoginId: string) => {
  const params = {
    memberLoginId: 'sdfsd',
  };
  const response = await $axios().get('/member/id-validation', { params });
  return response.status === 204;
};

const logoutMember = async () => {
  const response = await $auth().get('/member/logout');
  console.log(response);
  console.log(localStorage.getItem('token'));
  return response;
};

const getMemberInfo = async () => {
  const response = await $auth().get('/member/memberinfo/2');
  console.log(response);
  return response;
};
export {
  postMember,
  loginMember,
  getIdValidation,
  logoutMember,
  getMemberInfo,
};
