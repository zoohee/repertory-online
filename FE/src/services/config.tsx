import axios from 'axios';
export const $axios = () => {
  return axios.create({
    baseURL: import.meta.env.VITE_APP_BASE_URL,
    headers: {
      'Content-Type': 'application/json',
    },
    withCredentials: true,
  });
};
export const $pose = () => {
  return axios.create({
    baseURL: import.meta.env.VITE_POSE_BASE_URL,
    headers: {
      'Content-Type': 'application/json',
    },
    withCredentials: true,
  });
};

export const $auth = () => {
  const token = localStorage.getItem('token');
  return axios.create({
    baseURL: import.meta.env.VITE_APP_BASE_URL,
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    withCredentials: true,
  });
};
