// 读取本地的JSON文件，并把数据返回
import banner from '../../static/json/banner.json'
import homeCourse from '../../static/json/homeCourse.json'
import specialTraining from '../../static/json/specialTraining.json'
import course from '../../static/json/course.json'
import systemMessage from '../../static/json/systemMessage.json'
import personalMessage from '../../static/json/personalMessage.json'

const readJson={
  //返回轮播图数据
  getBanner(){
    return banner.data;
  },
  //返回轮播图数据
  getHomeCourse(){
    return homeCourse.data;
  },
  //返回特训班数据
  getSpecialTraining(){
    return specialTraining.data;
  },
  //返回课程数据
  getCourse(){
    return course.data;
  },
  //返回系统消息数据
  getSystemMessage(){
    return systemMessage.data;
  },
  //返回个人消息数据
  getPersonalMessage(){
    return personalMessage.data;
  },
}

export default readJson;
