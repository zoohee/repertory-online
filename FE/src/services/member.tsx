import { $axios } from './config';

interface ILoginData {
  memberLoginId : string,
  memberPassword : string
}

interface IJoinData{
  memberLoginId: string,
  memberPassword: string,
  memberName: string,
  memberEmail: string,
}
// 회원가입
const postMember = async ({ memberLoginId, memberPassword, memberName, memberEmail }: IJoinData) => {
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
const loginMember = async ({ memberLoginId, memberPassword }:ILoginData) => {
  const loginData = new FormData();
  loginData.append('memberLoginId', memberLoginId)
  loginData.append('memberPassword', memberLoginId);
  const response = await $axios().post('/member/login', loginData,{
    headers:{
      "Content-Type": 'multipart/form-data',
    }
  });
  console.log(response.headers);
  const token = response.headers['authorization']
  console.log(token);
  localStorage.setItem('token', token);
  return response;
};

export { postMember, loginMember };
