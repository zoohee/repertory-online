import { $axios, $auth } from './config';
interface IPage {
  page: number;
  pageSize: number;
}
// Test -OK
const communityTest = async () => {
  const response = await $axios().get(`/community/test`);
  console.log(response);
  return response;
};

// 소스 이름 검색 -OK
const searchSourceName = async (keyword: string) => {
  const params = {
    keyword: keyword,
  };
  const response = await $auth().get(`/dance/source`, { params });
  console.log(response);
  return response;
};

// 소스 정보 조회 -OK
const fetchSourceInfo = async (sourceId: number) => {
  const params = {
    sourceId: sourceId,
  };
  const response = await $auth().get(`/dance/source/${sourceId}`, { params });
  console.log(response);
  return response;
};

// 소스 저장 -OK
const postSource = async (data: FormData) => {
  const response = await $auth().post(`/dance/source`, data, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  console.log(response);
  return response;
};

// 소스 편집 - CORS
const patchSource = async () => {
  const params = {
    sourceName: 'patchTest',
    sourceLength: 1234,
    sourceCount: 0,
    tagList: ['this', 'source', 'is', 'patched'],
    start: 'end',
    end: 'start',
  };
  const response = await $auth().patch(`/dance/source/${2}`, {
    params,
  });
  console.log(response);
  return response;
};

// 소스 삭제 500
const deleteSource = async (sourceId: number) => {
  const response = await $auth().delete(`/dance/source/${sourceId}`);
  console.log(response);
  return response;
};

// 내 소스 조회 -OK
const getMySource = async () => {
  const response = await $auth().get(`/dance/source/mine`);
  console.log(response);
  return response.data;
};

// 내가 클론 소스 조회 -OK
const getMySourceClone = async () => {
  const response = await $auth().get(`/dance/clone`);
  console.log(response);
  return response.data;
};

// 클론한 소스 삭제-OK
// 현재 클론 여부 상관 없이 200 => response확인 필요
const deleteMySourceClone = async (sourceId: number) => {
  const params = {
    sourceId: sourceId,
  };
  const response = await $auth().delete(`/dance/clone/${sourceId}`, {
    params,
  });
  console.log(response);
  return response;
};

// 태그 조회 -OK
const getTags = async () => {
  const response = await $auth().get(`/dance/tag`);
  console.log(response.data);
  return response;
};

// 태그 생성 -OK
const postTag = async (tag: string) => {
  const data = {
    tagName: tag,
  };
  const response = await $auth().post(`/dance/tag`, data);
  console.log(response.data);
  return response;
};

// 태그 삭제
const deleteTag = async (
  // tagId: number,
  tagName: string
) => {
  const response = await $auth().delete(`/dance/tag/${2}`, {
    params: {
      tagName,
    },
  });
  console.log(response.data);
  return response;
};

// 소스 공개상태 변경 => CORS
const patchSourceIsAvailable = async () => {
  const params = {
    isAvailable: false,
  };
  const response = await $auth().post(`/dance/source/${2}/disable`, {
    params,
  });
  console.log(response.status);
  return response.status;
};

// 레퍼토리 이름 검색 -> OK
const getRepertoryByName = async (keyword: string) => {
  const params = {
    keyword: keyword,
  };
  const response = await $auth().get(`/dance/source`, {
    params,
  });
  console.log(response);
  return response;
};

// 내 레퍼토리 목록 조회 -OK
const getMyRepertory = async ({ page, pageSize }: IPage) => {
  const response = await $auth().get(`/dance/repertory/mine`, {
    params: { page, pageSize },
  });
  console.log(response);
  return response;
};

// 레퍼토리 저장 - OK
const postRepertory = async (data: FormData) => {
  const response = await $auth().post(`/dance/repertory`, data, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  console.log(response.status);
  console.log(response.data);
  return response;
};

// 레퍼토리 삭제 -> 500
const deleteRepertory = async (repertoryId: number) => {
  const response = await $auth().delete(`/dance/repertory/${repertoryId}`);
  console.log(response.data);
  return response;
};

// 레퍼토리 영상 교체
const patchRepertory = async (repertoryId: number, data: FormData) => {
  const response = await $auth().patch(
    `/dance/repertory/${repertoryId}/video`,
    data,
    {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    }
  );
  console.log(response.data);
  return response;
};

// 레퍼토리 공개상태 변경 => CORS
const patchRepertoryIsAvailable = async (
  sourceId: number,
  isAvailable: boolean
) => {
  const response = await $auth().patch(`/dance/repertory/${sourceId}/status`, {
    params: { isAvailable: isAvailable },
  });
  console.log(response.status);
  return response.status;
};

// const detectPose = async();

export {
  communityTest,
  searchSourceName,
  fetchSourceInfo,
  postSource,
  patchSource,
  deleteSource,
  getMySource,
  getMySourceClone,
  deleteMySourceClone,
  getTags,
  deleteTag,
  postTag,
  patchSourceIsAvailable,
  getRepertoryByName,
  getMyRepertory,
  postRepertory,
  deleteRepertory,
  patchRepertory,
  patchRepertoryIsAvailable,
};
