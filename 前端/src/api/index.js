import axios from 'axios';

axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'; 
import qs from 'qs';

let base = 'http://xxxx.xxxx.com.cn/hubei/doc';
let base1 = 'http://xxxx.com';

let token = '';


let http = axios.create({
  baseURL: 'http://localhost:8080/',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
  },
  transformRequest: [function (data) {
    let newData = '';
    for (let k in data) {
      if (data.hasOwnProperty(k) === true) {
        newData += encodeURIComponent(k) + '=' + encodeURIComponent(data[k]) + '&';
      }
    }
    return newData;
  }]
});

function apiAxios(method, url, params, response) {
  http({
    method: method,
    url: url,
    data: method === 'POST' || method === 'PUT' ? params : null,
    params: method === 'GET' || method === 'DELETE' ? params : null,
  }).then(function (res) {
    response(res);
  }).catch(function (err) {
    response(err);
  })
}

export default {
  get: function (url, params, response) {
    return apiAxios('GET', url, params, response)
  },
  post: function (url, params, response) {
    return apiAxios('POST', url, params, response)
  },
  put: function (url, params, response) {
    return apiAxios('PUT', url, params, response)
  },
  delete: function (url, params, response) {
    return apiAxios('DELETE', url, params, response)
  }
}

export const bmz = params => { return axios.get(`${base1}/getGroupCodeList`, { params: params }); };

export const getOptList = params => { return axios.get(`${base1}/getOptList`, { params: params }); };

export const branchList = params => { return axios.get(`${base1}/branchList`, { params: params }); };

export const addOperator = params => { return axios.post(`${base1}/addOperator`, qs.stringify(params)).then(res => res.data); };

export const editBranch = params => { return axios.post(`${base1}/editBranch`, qs.stringify(params)).then(res => res.data);  };

export const addDzb = params => { return axios.post(`${base1}/addBranch`, qs.stringify(params)).then(res => res.data); };

export const partlist = params => { return axios.get(`${base1}/branchList`, { params: params }); };

export const memberList = params => { return axios.get(`${base1}/memberList`, { params: params }); };

export const selectMember = params => { return axios.get(`${base1}/selectMember`, { params: params }); };

export const addMember = params => { return axios.get(`${base1}/addMember`, { params: params }); };

export const branchFeeList = params => { return axios.get(`${base1}/branchFeeList`, { params: params }); };

export const memberFeeList = params => { return axios.post(`${base1}/getMemberFeeList`, qs.stringify(params)).then(res => res.data); };

export const getOptNameList = params => { return axios.get(`${base1}/getOptNameList`, { params: params }); };

export const createTask = params => { return axios.post(`${base1}/createTask`, qs.stringify(params)).then(res => res.data);  };

export const memberFee = params => { return axios.post(`${base1}/memberFee`, qs.stringify(params)).then(res => res.data);  };

export const uploadToken = params => { return axios.get(`${base1}/uploadToken`, { params: params });  };

export const savePic = params => { return axios.post(`${base1}/savePic`, qs.stringify(params)).then(res => res.data);  };