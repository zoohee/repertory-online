import {create}from 'zustand';
import { loginMember } from '@/services/member';

type LoginState = {
    isLoggedin :boolean;
    login : Promise<boolean>;
    logout : ()=> void;
}
interface ILoginData {
    memberLoginId: string,
    memberPassword: string
}

export const LoginStore = create<LoginState>((set)=>{
    isLoggedin : Boolean(localStorage.getItem('token'))

    login: ({ memberLoginId, memberPassword }: ILoginData)=>{
        loginMember({memberLoginId,memberPassword}).then((res)=>{
            if(res.status===200){
                console.log(`[Login Store] : Login 200`);
                const token = res.headers['authorization'];
                localStorage.setItem('token',token);
                set({isLoggedin: true});
                return true;
            }
            return false;
        });
    }

    logout :()=>{
        localStorage.removeItem('token');
        set({ isLoggedin: false });
    }
})