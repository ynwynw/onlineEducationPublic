<template>
  <div class="course-preview">
    <el-image :src="bannerImage" class="banner-image">
      <div slot="error" class="image-slot">
        <el-image src="https://imgs.wanmen.org/1b3d4fe6d934b9cb66b951d06445ca3c.png" class="banner-image"></el-image>
      </div>
    </el-image>
    <div class="preview-container">
      <h2 class="header">{{new Date().getFullYear()}} <span>课程预告</span></h2>
      <ul class="preview-box" v-if="preview!=null && preview.length!==0">
        <li v-for="(course,index) in preview" :key="index">
          <p class="title">{{ course.courseName }}</p>
          <p class="description">课程简介
            <el-divider direction="vertical"></el-divider>
            {{course.description}}</p>
          <p class="description">授课讲师
            <el-divider direction="vertical"></el-divider>
            {{course.teacherName}}，{{course.teacherDes}}</p>
          <p class="info">
            <span class="start-date">开播时间<el-divider direction="vertical"></el-divider>{{course.startTime}}</span>
            <span class="length-time">预计总时长<el-divider direction="vertical"></el-divider>{{course.timeLength}}小时</span>
          </p>
        </li>
      </ul>
      <div v-else class="no-course">
        <el-image :src="noCourseImg"></el-image>
        <h2>暂无课程预告</h2>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "CoursePreview",
    data() {
      return{
        noCourseImg:require("../../assets/global/no-data.png"),
        bannerImage:require("../../assets/global/curriculum.png"),
        preview:[],
      }
    },
    methods:{
      reqInfo(){
        this.$courseApi.queryCoursePreview().then(res=>{
          //this.bannerImage = res.data.img;
          this.preview = res.data.list;
        });
      },
    },
    created(){
      this.reqInfo();
    }
  }
</script>

<style scoped>
  .course-preview{
    width: 100%;
    background: #F9F9F9;
  }

  .banner-image{
    height: 430px;
    width: 100%;
  }

  .preview-container{
    width: 75%;
    min-height: 600px;
    margin: 0 auto;
  }

  .header{
    text-align: left;
    color: #4a4a4a;
    font-weight: 500;
    font-size: 23px;
    padding: 12px 20px;
    background: #F9BF80;
  }

  .preview-box{
    list-style: none;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    grid-row-gap: 50px;
    grid-column-gap: 30px;
    padding: 0;
    margin: 20px 0;
  }

  .preview-box li{
    max-height: 195px;
    min-height: 170px;
    position: relative;
    text-align: left;
    padding: 12px 16px 22px;
    border-radius: 2px;
    background: #FFFFFF url(../../assets/global/back-pro.png) no-repeat;
    background-size: 100% 100%;
    box-shadow: 0 4px 10px 0 rgba(0,0,0,0.08);
  }

  .preview-box .title{
    font-size: 20px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin: 10px 0 0;
  }

  .preview-box .description{
    margin: 8px 0 16px;
    font-size: 14px;
    color: #666;
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 2; /* 限制在一个块元素显示的文本的行数 */
    -webkit-box-orient: vertical; /* 垂直排列 */
    word-break: break-all;  /* 内容自动换行 */
    text-overflow: ellipsis;
    line-height: 21px;
    max-height: 42px;

  }

  .preview-box .info{
    position: absolute;
    bottom: 12px;
    padding-top: 8px;
    width: 90%;
    color: #666;
    font-size: 14px;
    border-top: 1px solid rgba(0,0,0,0.07);
  }

  .preview-box .info .start-date{
    position: absolute;
    left: 5px;
  }

  .preview-box .info .length-time{
    position: absolute;
    right: 5px;
  }

  .el-divider--vertical{
    margin: 0 4px;
  }

  .course-preview .no-course{
    margin-top: 40px;
    text-align: center;
    height: 500px;
    width: 100%;
    position: relative;
  }

  .course-preview .no-course .el-image{
    height: 370px;
    width: 600px;
  }

  .course-preview .no-course h2{
    position: absolute;
    top: 66%;
    left: 50%;
    margin: 0;
    font-weight: 500;
    transform: translate(-50%,-50%);
  }

</style>
