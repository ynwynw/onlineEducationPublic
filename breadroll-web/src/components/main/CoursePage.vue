<template>
  <div class="course-page">
    <div class="course-container">
      <el-image class="banner" src="https://static.wanmen.org/406cc4a6b08400394590d4f393d14970.jpg"></el-image>
      <div class="course-content">
        <el-tabs v-model="activeName" @tab-click="handleClick" style="padding: 20px 30px 0;">
          <el-tab-pane label="全部课程" name="first"></el-tab-pane>
          <el-tab-pane label="新品" name="second"></el-tab-pane>
          <el-tab-pane label="最热" name="third"></el-tab-pane>
          <el-tab-pane :label="tabName" name="fourth"></el-tab-pane>
        </el-tabs>
        <ul v-if="courseData!=null && courseData.length!==0">
          <li class="course-info animated bounceIn" v-for="(course,index) in courseData" :key="index" @click="toCourseDetail(course)">
            <el-image :src="course.coverUrl"></el-image>
            <h2 class="title">{{course.courseName}}</h2>
            <p class="desc">{{course.description}}</p>
            <div class="course-state">
              <el-tag class="time-length" size="small"><i class="el-icon-time" style="margin-right: 4px;"/>{{course.courseTime,course.courseSecond | changeHourMin}}</el-tag>
              <el-tag class="state" v-if="course.vipState" type="warning" size="small">VIP课程</el-tag>
              <el-tag class="state" v-else type="success" size="small">免费课程</el-tag>
              <el-tag class="play-count" type="warning" size="small"><i class="el-icon-view"/>&nbsp;播放量&nbsp;{{course.playCount}}</el-tag>
            </div>
          </li>
        </ul>
        <div v-else class="no-course">
          <el-image :src="noCourseImg"></el-image>
          <h2>抱歉，没有找到 “<span style="color:#0088f0;">{{tabName}}</span>” 的相关课程</h2>
        </div>
        <el-pagination
          v-if="courseData!=null && courseData.length!==0"
          class="page"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryData.pageNum"
          :page-sizes="[5, 10, 20, 50, 100, 200]"
          :page-size="queryData.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="queryData.total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "CoursePage",
    data() {
      return{
        noCourseImg:require("../../assets/global/no-course.png"),
        courseData:[],
        activeName: 'first',
        tabName:"",
        index:0,
        queryData:{
          pageNum:1,
          pageSize:5,
          total:0,
        },
      }
    },
    methods:{
      toCourseDetail(course){
        this.$router.push({ path: '/courseDetail', query: {id:course.courseId}});
      },
      //切换tab
      handleClick(tab, event) {
        let index = parseInt(tab.index);
        this.index = index;
        this.reqInfo(index);
      },
      //每页大小
      handleSizeChange(val) {
        this.queryData.pageSize=val;
        this.reqInfo(this.index);
      },
      //修改当前页
      handleCurrentChange(val) {
        this.queryData.pageNum=val;
        this.reqInfo(this.index);
      },
      reqInfo(index){
        switch (index) {
          case 0:{
            this.$courseApi.queryAllCourse(this.queryData).then(res=>{
              this.courseData = res.data.list;
              this.queryData.total = res.data.total;
            });
            break;
          }
          case 1:{
            this.$courseApi.queryCourseByTime(this.queryData).then(res=>{
              this.courseData = res.data.list;
              this.queryData.total = res.data.total;
            });
            break;
          }
          case 2:{
            this.$courseApi.queryCourseByPlay(this.queryData).then(res=>{
              this.courseData = res.data.list;
              this.queryData.total = res.data.total;
            });
            break;
          }
          case 3:{
            this.reqData();
            break;
          }
        }
      },
      reqData(){
        if(this.$route.query.way!=null){
          this.tabName="";
          switch (this.$route.query.way){
            case "0":{
              this.activeName="third";
              this.index=2;
              this.reqInfo(this.index);
              break;
            }
            case "1":{
              this.activeName="second";
              this.index=1;
              this.reqInfo(this.index);
              break;
            }
            case "2":{
              this.tabName=this.$route.query.title;
              this.activeName="fourth";
              this.index=3;
              this.$courseApi.queryCourseByFree(this.queryData).then(res=>{
                this.courseData = res.data.list;
                this.queryData.total = res.data.total;
              });
              break;
            }
            case "type":{
              this.tabName=this.$route.query.title;
              this.activeName="fourth";
              this.index=3;
              this.queryData["typeId"]=this.$route.query.id;
              this.$courseApi.queryCourseByType(this.queryData).then(res=>{
                this.courseData = res.data.list;
                this.queryData.total = res.data.total;
              });
              break;
            }
            case "name":{
              this.tabName= this.$route.query.name;
              this.activeName="fourth";
              this.index=3;
              this.queryData["courseName"]=this.$route.query.name;
              this.$courseApi.queryCourseByName(this.queryData).then(res=>{
                this.courseData = res.data.list;
                this.queryData.total = res.data.total;
              });
              break;
            }
          }
        }else {
          this.$courseApi.queryAllCourse(this.queryData).then(res=>{
            this.courseData = res.data.list;
            this.queryData.total = res.data.total;
          });
        }
      }
    },
    watch: {
      // 监听路由的变化 参数变化时更新发布订阅数据
      '$route' (to, from) {
        this.reqData();
      }
    },
    created(){
      this.reqData();
    }
  }
</script>

<style scoped>
  .course-page{
    background-color: #F9F9F9!important;
    width: 100%;
  }

  .course-container{
    width: 72%;
    margin: 0 auto;
  }

  .banner{
    width: 100%;
    height: 65px;
    margin: 20px 0;
    cursor: pointer;
    border-radius: 10px;
    overflow: hidden;
    transition: all 0.5s;
  }

  .banner:hover{
    box-shadow: 0 15px 30px rgba(0,0,0,0.1);
    -webkit-transform: translate3d(0px, -1px, 0px);
    transform: translate3d(0px, -1px, 0px);
  }

  .course-content{
    overflow: hidden;
    border-radius: 8px;
    box-shadow: 0 4px 6px 0 rgba(0,0,0,0.06);
    background-color: #ffffff;
    margin-bottom: 50px;
  }

  .course-content .no-course{
    height: 500px;
    width: 100%;
  }

  .course-content .no-course .el-image{
    height: 74%;
    width: 440px;
  }

  .course-content>ul{
    padding: 10px 45px 0;
    width: auto;
    margin: 0;
    list-style: none;
  }

  .course-content .course-info{
    position: relative;
    height: 156px;
    width: auto;
    cursor: pointer;
    color: #333333;;
    transition: all 0.5s;
    text-align: left;
    padding-bottom: 20px;
    margin-bottom: 20px;
    border-bottom: 1px solid #ededed;
  }

  .course-content .course-info:hover{
    color: #0088f0;
  }

  .course-content .course-info .el-image{
    width: 290px;
    height: 100%;
    margin-right: 20px;
    border-radius: 10px;
    overflow: hidden;
    display: flex;
    position: relative;
  }

  .course-content .course-info .title{
    position: absolute;
    top: 0px;
    left: 310px;
    font-size: 18px;
    font-family: 'PingFangSC', sans-serif;
  }

  .course-content .course-info .desc{
    position: absolute;
    top: 50px;
    left: 310px;
    margin: 0;
    font-size: 15px;
    height: 45px;
    width: 65%;
    color: #999999;
    overflow: hidden;
    text-overflow: ellipsis;
    font-family: 'PingFangSC', sans-serif;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    line-height: 1.5;
    text-align: justify;
  }

  .course-content .course-info .course-state{
    position: absolute;
    left: 310px;
    bottom: 30px;
  }

  .course-content .course-info .el-tag{
    font-size: 14px;
    margin-right: 10px;
  }

  .course-content .page{
    padding: 5px 12px;
    background: rgb(255, 255, 255);
    margin: -10px auto 14px;
  }
</style>

<style>
  .course-page .el-tabs__nav-scroll .el-tabs__item{
    font-size: 18px!important;
  }
</style>
