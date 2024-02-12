import { $axios, $auth} from './config';

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
  loginData.append('memberPassword', memberPassword);
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

const getIdValidation = async ( memberLoginId :string) => {
  const params ={
    memberLoginId : 'rlagudwls3469'
  }
  const response = await $axios().get('/member/id-validation', {params});
  console.log(response);
  console.log(response.data);
  return response;
};

const logoutMember = (async ()=>{
  const response = await $auth().post('/member/logout')
  console.log(response)
  console.log(localStorage.getItem('token'))
  return response;
})

const getMemberInfo = async()=>{
  const response = await $auth().get('/member/memberinfo')
  console.log(response)
  return response
}
export { postMember, loginMember, getIdValidation, logoutMember,getMemberInfo };
