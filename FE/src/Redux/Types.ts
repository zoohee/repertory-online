export interface LoginData {
  memberLoginId: string;
  memberLoginPassword: string;
}

export interface LoginState {
  token: string | null;
}
