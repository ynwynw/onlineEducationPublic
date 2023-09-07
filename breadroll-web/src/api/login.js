// 公共请求
import http from '@/lib/http';
export const loginApi = {
  //注册
  registered(data){
    return http.post('/global/register',JSON.stringify(data));
  },

  //帐号密码登录
  login(data){
    return http.post('/global/doLogin', JSON.stringify(data));
  },

  //验证密码是否正确
  checkPassword(userAccount,passWord){
    let data = {userAccount:userAccount,passWord:passWord}
    return http.post('/util/check/password', JSON.stringify(data));
  },

  //忘记密码
  forgotPassword(data){
    return http.post('/global/forgotPassword',JSON.stringify(data));
  },

  //退出系统
  logout(){
    return http.get('/user/logout');
  }
}
