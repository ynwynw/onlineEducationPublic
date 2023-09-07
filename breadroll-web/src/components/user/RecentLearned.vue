<template>
  <div class="recent-learned">
    <h2 class="header">最近学习
      <el-button plain size="small" class="clear-all" @click="clearAll">全部清空</el-button>
    </h2>
    <ul v-if="courseData!=null && courseData.length!==0">
      <li class="course-info" v-for="(course,index) in courseData" :key="index">
        <el-image :src="course.coverUrl"></el-image>
        <h2 class="title">{{course.courseName}}</h2>
        <p class="desc">{{course.description}}</p>
        <div class="course-state">
          <el-tag class="time-length" size="small"><i class="el-icon-time" style="margin-right: 4px;"/>{{course.courseTime,course.courseSecond | changeHourMin}}</el-tag>
          <el-tag class="state" v-if="course.vipState" type="warning" size="small">VIP课程</el-tag>
          <el-tag class="state" v-else type="success" size="small">免费课程</el-tag>
        </div>
        <div>
          <div class="operate-course">
            <el-button type="primary" size="small" @click="watchCourse(course.courseId)">观看课程</el-button>
            <el-button type="danger" size="small" @click="clearCourse(course.courseId)">清除记录</el-button>
          </div>
        </div>
      </li>
    </ul>
    <div v-else class="no-course">
      <el-image :src="noCourseImg"></el-image>
      <h2>暂无学习记录，快去学习吧</h2>
    </div>
    <el-pagination
      class="page"
      v-if="courseData!=null && courseData.length!==0"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryData.pageNum"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="queryData.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="queryData.total">
    </el-pagination>
  </div>
</template>

<script>
  export default {
    name: "RecentLearned",
    data() {
      return{
        courseData:[],
        noCourseImg:require("../../assets/global/no-course.png"),
        queryData:{
          pageNum:1,
          pageSize:5,
          total:0,
        },
      }
    },
    methods:{
      handleSizeChange(val) {
        this.queryData.pageSize=val;
        this.reqInfo();
      },
      //修改当前页
      handleCurrentChange(val) {
        this.queryData.pageNum=val;
        this.reqInfo();
      },
      //清除全部课程数据
      clearAll(){
        this.$confirm('你确定要清空全部观看记录么?', '提示', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          this.$userApi.clearAllStudyRecord().then(res=>{
            this.$message({type: 'success', message: res.message});
            this.reqInfo();
          })
        }).catch(() => {});
      },
      //观看课程
      watchCourse(courseId){
        this.$router.push({ path: '/courseDetail', query: {id:courseId}});
      },
      //清除课程
      clearCourse(courseId){
        this.$userApi.clearStudyRecord(courseId).then(res=>{
          this.$message({type: 'success', message: res.message});
          this.reqInfo();
        });
      },
      //查询个人的学习课程
      reqInfo(){
        this.$userApi.queryStudyRecord(this.queryData).then(res=>{
          this.courseData = res.data.list;
          this.queryData.total = res.data.total;
        });
      },
    },
    created(){
      this.reqInfo();
    }
  }
</script>

<style scoped>
.recent-learned{
  overflow: hidden;
  padding-top: 20px;
  border-radius: 8px;
  background-color: #ffffff;
  margin-bottom: 50px;
  border: 1px solid #e6e6e6;
}

.recent-learned .header{
  margin-top: 0;
  position: relative;
  padding-left: 30px;
  padding-bottom: 16px;
  margin-bottom: 16px;
  border-bottom: 1px solid #e6e6e6;
}

.recent-learned .header .clear-all{
  position: absolute;
  right: 20px;
}


.recent-learned>ul{
  padding: 10px 30px 0;
  list-style: none;
  width: auto;
  margin: 0;
}

.recent-learned .course-info{
  position: relative;
  height: 156px;
  width: auto;
  color: #333333;;
  transition: all 0.5s;
  text-align: left;
  padding-bottom: 20px;
  margin-bottom: 20px;
  border-bottom: 1px solid #ededed;
}

.recent-learned .course-info:hover{
  color: #40a9ff;
}

.recent-learned .course-info .el-image{
  width: 315px;
  height: 100%;
  margin-right: 20px;
  border-radius: 10px;
  overflow: hidden;
  display: flex;
  position: relative;
}

.recent-learned .course-info>.title{
  position: absolute;
  top: 0;
  margin: 16px 0 8px;
  padding: 0;
  left: 337px;
  font-size: 18px;
  font-family: 'PingFangSC', sans-serif;
}

.recent-learned .course-info .desc{
  position: absolute;
  top: 46px;
  left: 337px;
  margin: 0;
  font-size: 14px;
  width: 60%;
  height: 40px;
  line-height: 20px;
  overflow: hidden;
  text-align: left;
  color: #999999;
  text-overflow: ellipsis;
  font-family: 'PingFangSC', sans-serif;
  display: -webkit-box;
  -webkit-box-orient: vertical;
}

.recent-learned .course-info .course-state{
  position: absolute;
  left: 337px;
  bottom: 30px;
}

.recent-learned .course-info .el-tag{
  font-size: 14px;
  margin-right: 10px;
}

.recent-learned .course-info .operate-course{
  position: absolute;
  right: 20px;
  bottom: 30px;
}

.recent-learned .page{
  padding: 5px 12px;
  background: rgb(255, 255, 255);
  margin: -10px auto 14px;
  text-align: center;
}

.recent-learned .no-course{
  text-align: center;
  height: 500px;
  width: 100%;
}

.recent-learned .no-course .el-image{
  height: 74%;
  width: 440px;
}
</style>
