// 公共请求
import http from '@/lib/http';
import config from '@/config';
import cookie from "../lib/cookie";
const baseUrl = config.getBaseUrl();
export const courseApi = {
  //获取所有课程类别
  queryCourseType(){
    return http.get('/course/queryType');
  },

  //获取首页课程信息
  queryCourseHome(){
    return http.get('/course/queryCourse/home');
  },

  //获取所有课程信息
  queryAllCourse(obj){
    return http.get('/course/queryAllCourse',{params:obj});
  },

  //根据播放量查询课程
  queryCourseByPlay(obj){
    return http.get('/course/queryCourse/play',{params:obj});
  },

  //根据发布时间查询课程
  queryCourseByTime(obj){
    return http.get('/course/queryCourse/time',{params:obj});
  },

  //查询免费课程
  queryCourseByFree(obj){
    return http.get('/course/queryCourse/free',{params:obj});
  },

  //根据课程类型查询课程
  queryCourseByType(obj){
    return http.get('/course/queryCourse/type',{params:obj});
  },

  //根据课程Id查询课程
  queryCourseById(courseId){
    return http.get('/course/queryCourse/id',{params:{courseId:courseId}});
  },

  //根据课程Id查询特训班
  querySpecialById(courseId){
    return http.get('/course/querySpecial/id',{params:{courseId:courseId}});
  },

  //根据课程名称模糊查询课程
  queryCourseByName(obj){
    return http.get('/course/queryCourse/name',{params:obj});
  },

  //查询全部特训班
  queryAllSpecialCourse(obj){
    return http.get('/course/queryAllSpecialCourse',{params:obj});
  },

  //根据课程id查询课程详细信息（课程信息、目录信息、讲师信息）
  queryCourseInfoById(id){
    return http.get('/course/queryCourseInfo/id',{params:{courseId:id}});
  },

  //获取随机课程
  queryCourseByRandom(count){
    return http.get('/course/queryCourse/random',{params:{count:count}});
  },

  //获取全部学习资料
  queryAllResource(obj){
    return http.get('/course/resource/all',{params:obj});
  },

  //根据资料名获取学习资料
  queryResourceByName(obj){
    return http.get('/course/resource/name',{params:obj});
  },

  //检测学习资源是否存在
  checkResourceExist(resourceId){
    return http.get('/course/resource/exist',{params:{resourceId:resourceId}});
  },

  //下载学习资料
  downloadLearnMaterials(resourceId){
    return baseUrl + '/upload/download?resourceId='+resourceId;
  },

  //获取课程预告
  queryCoursePreview(){
    return http.get('/course/queryCourse/preview');
  },

  //获取所有文章信息
  queryAllArticle(obj){
    return http.get('/course/article/all',{params:obj});
  },

  //通过ID获取文章
  queryArticleById(id){
    return http.get('/course/article/id',{params:{articleId:id}});
  },


  //通过文章类型获取随机文章
  queryArticleByType(typeName){
    return http.get('/course/article/type',{params:{typeName:typeName,count:5}});
  },





















}
