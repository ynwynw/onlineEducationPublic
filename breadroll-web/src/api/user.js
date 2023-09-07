// 公共请求
import http from '@/lib/http';
import config from '@/config';

const baseUrl = config.getBaseUrl();
export const userApi = {
  //获取当前用户信息
  getUserInfo(){
    return http.get('/user/getUserInfo');
  },

  //修改手机号
  updatePhone(data){
    return http.post('/user/updatePhone',JSON.stringify(data));
  },

  //修改密码
  updatePassWord(data){
    return http.post('/user/updatePassword',JSON.stringify(data));
  },

  //更新用户信息
  updateUserInfo(data){
    return http.post('/user/updateUserInfo',JSON.stringify(data));
  },

  //注销帐号
  logoutUser(){
    return http.post('/user/dropUser');
  },

  checkState(){
    return http.get('/user/checkState');
  },

  //上传头像
  uploadAvatar(){
    return baseUrl+'/upload/avatar';
  },

  //上传背景图片
  uploadBackground(){
    return baseUrl+'/upload/background';
  },

  //检测今日是否签到
  checkSign(){
    return http.get('/user/checkSign');
  },

  //检测是否有新消息
  checkNewMessage(){
    return http.get('/user/check/newMessage');
  },

  //用户签到
  userSign(){
    return http.post('user/sign');
  },

  //本月签到次数
  getSignCount(){
    return http.get('/user/signCount');
  },

  //本月连续签到次数
  getCoiledSignCount(){
    return http.get('/user/coiledSignCount');
  },

  //查询用户积分
  queryCoin(){
    return http.get('/coin/queryCoin');
  },

  //查询用户积分记录
  queryCoinRecord(obj){
    return http.get('/coin/queryCoinRecord',{params:obj});
  },

  //查询我的所有消息
  queryMyMessage(obj){
    return http.get('/message/my/queryAll',{params:obj});
  },

  //个人消息全部设为已读
  readAllMyMessage(){
    return http.post('/message/my/readAll');
  },

  //读取某个消息
  readMessage(messageId){
    return http.delete('/message/my/read/'+messageId);
  },

  //删除某个消息
  clearMyMessage(messageId){
    return http.delete('/message/my/clear/'+messageId);
  },

  //查询所有系统消息
  querySystemMessage(obj){
    return http.get('/message/system/queryAll',{params:obj});
  },

  //-----------最近学习记录-----------
  //清除某个学习记录
  clearStudyRecord(courseId){
    return http.delete('/user/studyRecord/clear/'+courseId);
  },

  //清除全部学习记录
  clearAllStudyRecord(){
    return http.delete('/user/studyRecord/clearAll');
  },

  //查询用户学习记录
  queryStudyRecord(obj){
    return http.get('/user/studyRecord/query',{params:obj});
  },

  //添加用户播放记录
  addPlayRecord(obj){
    return http.post('/user/playRecord/add',JSON.stringify(obj));
  },

  //检测是否存在用户播放记录
  getPlayRecord(courseId){
    return http.get('/user/playRecord/query',{params:{courseId:courseId}});
  },


  //-----------用户课程-----------

  //查询用户课程
  queryUserCourse(obj){
    return http.get('/user/course/query',{params:obj});
  },

  //用户加入课程
  addUserCourse(obj){
    return http.post('/user/course/add',JSON.stringify(obj));
  },

  //用户退出课程
  exitUserCourse(courseId){
    return http.delete('/user/course/exit/'+courseId);
  },

  //查询我的VIP信息
  queryMyVip(){
    return http.get('/vip/my');
  },

  //查询VIP信息
  queryVipById(vipId){
    return http.get('/vip/id',{params:{vipId:vipId}});
  },

  //查询用户个人订单
  queryMyOrder(obj){
    return http.get('/order/user',{params:obj});
  },

  //查询用户个人特训班报名情况
  queryMySpecial(obj){
    return http.get('/course/coachApply/user',{params:obj});
  },

  //申请退课
  applyRetreatCourse(coachId){
    let obj = {coachId:coachId}
    return http.post('course/coachApply/retreat',JSON.stringify(obj));
  }














}
