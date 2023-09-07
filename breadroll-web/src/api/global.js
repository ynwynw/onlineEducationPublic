// 公共请求
import http from '@/lib/http';
import config from '@/config';
import tools from "@/util/tools";
import store from "@/store";
const baseUrl = config.getBaseUrl();
export const globalApi = {
  //获取图片验证码
  getImageCode(){
    let codeKey = tools.getUUID();
    store.commit('checkCodeKey',codeKey);
    return baseUrl + '/util/checkCode?checkCodeKey='+codeKey;
  },

  //验证图形验证码是否正确
  checkImageCode(checkCodeKey,checkCode){
    let data = {checkCodeKey: checkCodeKey, checkCode: checkCode}
    return http.post('/util/checkImgCode',JSON.stringify(data));
  },

  //发送手机短信验证码
  getPhoneCode(phoneNumber){
    let data = {phone: phoneNumber};
    return http.post('/sms/phoneCheckCode',JSON.stringify(data))
  },

  //校验手机验证码是否正确
  checkPhoneCode(phoneNumber,checkNumber){
    let data = {phone: phoneNumber,checkCode:checkNumber};
    return http.post('/util/check/phone',JSON.stringify(data))
  },

  //发送邮箱短信验证码
  getEmailCode(Email,userName){
    let data = {to: Email,userName:userName};
    return http.post('/sms/sendCodeMail',JSON.stringify(data))
  },

  //校验邮箱验证码是否正确
  checkEmailCode(data){
    return http.post('/util/check/mail',JSON.stringify(data))
  },

  //获取VIP详情
  queryVipInfo(){
    return http.get('/vip/info');
  },

  //获取轮播图详情
  queryBannerInfo(){
    return http.get('/banner/info');
  },

  //创建订单
  createOrder(data){
    return http.post('/alipay/create',JSON.stringify(data))
  },



}
