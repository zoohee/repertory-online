export interface LoginData {
  memberLoginId: string;
  memberLoginPassword: string;
}

export interface memberState {
  memberName: string | null;
  memberProfile: string | null;
  token: string | null;
}
