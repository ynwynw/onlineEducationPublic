import axios from 'axios'
import { Loading,MessageBox } from 'element-ui'
import NewMessage from '../lib/message'
import config from '../config';
import store from "../store";

import cookie from "./cookie";
import VueRouter from "../router";
//Loading加载的实例对象
let loadingInstance = null
//默认路径
const baseUrl = config.getBaseUrl();

//使用create方法创建axios实例
const http = axios.create({
  timeout: 30000, // 请求超时时间
  baseURL: baseUrl,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  }
})

// 添加请求拦截器
http.interceptors.request.use(config => {
  //ElementUI 的 Loading加载
  loadingInstance = Loading.service({lock: true, text: 'loading...'})
  // 在发送请求之前做些什么
  if (cookie.getToken()) {
    config.headers["satoken"] = cookie.getToken();
  }
  //使用vuex 存放请求
  config.cancelToken = new axios.CancelToken((cancel) => {
    store.dispatch('pushCancel', {cancelToken:cancel})
  });
  return config
})

// 添加响应拦截器
http.interceptors.response.use((response) => {
  //关闭 Loading加载
  loadingInstance.close()
  if(response.data.code === 200){
    return response.data;
  }else{
    if(response.data.code===490){
      store.dispatch('clearCancel');
      MessageBox.alert('当前身份已过期，请重新登录', '提示', {
        confirmButtonText: '确定',
        type: 'warning',
        callback: () => {
          cookie.clearToken();
          VueRouter.push('/loginForm');
        }
      });
      throw response.data.message;
    }if(response.data.code===495){
      store.dispatch('clearCancel');
      MessageBox.alert('此账号已在别处登录', '提示', {
        confirmButtonText: '确定',
        type: 'warning',
        callback: () => {
          cookie.clearToken();
          VueRouter.push('/loginForm');
        }
      });
      throw response.data.message;
    } else{
      NewMessage.warning({message: response.data.message, type: 'warning', duration: 3 * 1000})
      throw response.data;
    }
  }
}, (error) => {
  //关闭 Loading加载
  loadingInstance.close()
  if (axios.isCancel(error)) {
    return new Promise(() => {});
  }else {
    //控制台打印网络错误
    console.log('http错误：', error)
    // 判断 error.Message 是否为 undefined
    const msg = error.Message !== undefined ? error.Message : ''
    // Message 提示错误信息
    NewMessage.error({message: '网络错误' + msg, type: 'error', duration: 3 * 1000})
    return Promise.reject(error);
  }
})

export default http

