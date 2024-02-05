import axios from 'axios';

export const $axios = () => {
  return axios.create({
    baseURL: 'http://i10a707.p.ssafy.io:8000',
    headers: {
      'Content-Type': 'application/json',
    },
    withCredentials: true,
  });
};

export const $auth = () => {
  const token = localStorage.getItem('token');
  return axios.create({
    baseURL: 'http://i10a707.p.ssafy.io:8000',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    withCredentials: true,
  });
};
