<template>
  <div class="my-special">
    <h2 class="header">我的订单</h2>
    <div class="gold-container">
      <el-card class="gold-card" shadow="none">
        <div slot="header">
          <svg class="icon" aria-hidden="true" style="width: 31px;height: 31px;vertical-align: middle;">
            <use xlink:href="#iconrecord"></use>
          </svg>
          <span style="font-weight: 600;">订单记录</span>
        </div>
        <el-table :data="specialData" border stripe style="width: 100%;margin-bottom: 20px;min-height:283px" >
          <el-table-column prop="courseName" label="课程名" width="250"></el-table-column>
          <el-table-column prop="startTime" label="开课时间" width="150"></el-table-column>
          <el-table-column prop="userName" label="姓名" width="150"></el-table-column>
          <el-table-column prop="userPhone" label="留存手机号" width="150"></el-table-column>
          <el-table-column prop="userEmail" label="留存邮箱" width="180"></el-table-column>
          <el-table-column label="课程状态">
            <template slot-scope="scope">
              <span>{{scope.row.applyState | specialTrainState}}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" :disabled="scope.row.applyState!==0" type="primary" @click="retreat(scope.row)">退课</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页-->
        <el-pagination
          class="page"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryData.pageNum"
          :page-sizes="[5, 10, 20, 50]"
          :page-size="queryData.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="queryData.total">
        </el-pagination>
      </el-card>
    </div>
  </div>
</template>

<script>
  export default {
    name: "SpecialTrainingCenter",
    data() {
      return{
        specialData:[],    //花卷币记录数据
        queryData:{
          pageNum:1,
          pageSize:10,
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
      reqInfo() {
        this.$userApi.queryMySpecial(this.queryData).then(res=>{
          this.specialData = res.data.list;
          this.queryData.total = res.data.total;
        });
      },
      retreat(data){
        this.$confirm('将申请退出《'+data.courseName+'》课程?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$userApi.applyRetreatCourse(data.coachId).then(res=>{
            this.reqInfo();
            this.$message.success(res.message);
          });
        }).catch(() => {});
      }
    },
    created(){
      this.reqInfo();
    }
  }
</script>

<style scoped>
.my-special{
  overflow: hidden;
  padding-top: 20px;
  border-radius: 8px;
  background-color: #ffffff;
  margin-bottom: 10px;
  border: 1px solid #e6e6e6;
}

.my-special .header{
  margin-top: 0;
  padding-left: 30px;
  padding-bottom: 16px;
  margin-bottom: 16px;
  border-bottom: 1px solid #e6e6e6;
}

.my-special .gold-container{
  padding: 0 20px 0;
  min-height: 340px;
}

.my-special .gold-card{
  position: relative;
  margin-bottom: 5px;
  border: none;
}

.gold-card .gold-content{
  width: 100%;
  font-size: 20px;
  margin-left: 20px;
  line-height: 26px;
  color: rgba(0, 0, 0, 0.9);
  font-weight: 600;
  text-align: justify;
  text-overflow: ellipsis;
}

.gold-card .set-width{
  width: 88% !important;
}

.gold-card .link{
  position: absolute;
  top: 20px;
  right: 22px;
  display: flex;
  align-items: center;
}

.my-special .page{
  padding: 5px 12px;
  background: rgb(255, 255, 255);
  margin: -10px auto 7px;
  text-align: center;
}
</style>

<style>
.my-special .el-card__header{
  padding: 10px 20px;
  border: 1px solid #EBEEF5;
  position: relative;
}

.my-special .detailed .el-card__body{
  padding: 0 20px;
  border: 1px solid #ebeef5!important;
}

.my-special .el-card__body{
  padding: 0;
  min-height: 50px;
  border: none;
  position: relative;
}

.el-popover .gold-help{
  margin-top: 15px;
}

.el-popover .gold-help .title{
  margin: 5px 10px;
  font-size: 15px;
  font-weight: 600;
}

.el-popover .gold-help ul{
  list-style-type: disc;
  margin-top: 8px;
  margin-bottom: 20px;
}

.el-popover .gold-help li{
  list-style-type: disc;
}
</style>

<style>
.my-special .gold-card .el-table__empty-text {
  line-height: 245px;
  user-select: none;
}
</style>
