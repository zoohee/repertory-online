import { $axios, $auth } from "./config";

// Test -OK
const communityTest  = async() =>{
    const response = await $axios().get(`/community/test`);
    console.log(response)
    return response;
}

// 소스 이름 검색 -OK
const searchSourceName=  async(keyword: string) =>{
    const params = {
        'keyword': keyword
    }
    const response = await $axios().get(`/dance/source`,{params} );
    console.log(response)
    return response;
}

// 소스 정보 조회 -OK
const fetchSourceInfo = async(sourceId: number)=>{
    const params = {
        'sourceId': sourceId
    }
    const response = await $axios().get(`/dance/source/${sourceId}`,{params});
    console.log(response)
    return response;
}

// 소스 저장 -OK
const postSource = async(data : FormData)=>{
    const response = await $axios().post(`/dance/source`, data,{
        headers:{
            'Content-Type' : 'multipart/form-data'
        }
    });
    console.log(response)
    return response;
}

// 소스 편집 - CORS
const patchSource = async(sourceId: number)=>{
    const params = {
        'sourceName': 'patchTest',
        'sourceLength': 1234,
        'sourceCount': 0,
        'tagList': ['this','source','is','patched'],
        'start': 'end',
        'end': 'start', 
    }
    const response = await $axios().patch(`/dance/source/${sourceId}`, {params});
    console.log(response)
    return response;
}

// 소스 삭제 500 
const deleteSource = async(sourceId: number)=>{
    const params = {
        'sourceId': sourceId
    }
    const response = await $axios().delete(`/dance/source/${sourceId}`, {params});
    console.log(response)
    return response;
}

// 내 소스 조회 -OK
const getMySource = async()=>{
    const response = await $axios().get(`/dance/source/mine`);
    console.log(response)
    return response;
}

// 내 클론 소스 -OK
const getMySourceClone = async()=>{
    const response = await $axios().get(`/dance/clone`);
    console.log(response)
    return response;
}


export {
    communityTest,
    searchSourceName,
    fetchSourceInfo,
    postSource,
    patchSource,
    deleteSource,
    getMySource,
    getMySourceClone,
}