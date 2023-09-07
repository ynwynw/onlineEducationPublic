<template>
  <div class="system-message">
    <h2 class="header">系统消息</h2>
    <div class="message-container">
      <el-card class="message-card" shadow="hover" v-for="(item,index) in systemMessageData" :key="index">
        <div slot="header">
          <span style="font-weight: 600;"><i class="iconfont iconxinbaniconshangchuan-1"/>&nbsp;{{item.title}}</span>
          <span class="publish-time">{{item.publishTime}}</span>
        </div>
        <p class="message-content markdown-body" :class="[item.url ? 'set-width':'']" v-html="item.content"/>
        <div class="link" v-if="item.url">
          <el-link type="success" target="_blank" :href="item.url" style="height: 20px">查看详情<i class="el-icon-thumb"/> </el-link>
        </div>
      </el-card>
    </div>
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
  </div>
</template>

<script>
  import marked from 'marked'
  export default {
    name: "SystemMessage",
    data() {
      return{
        systemMessageData:this.$readJson.getSystemMessage(),
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
      reqInfo(){
        //查询系统所有消息
        this.$userApi.querySystemMessage(this.queryData).then(res=>{
          this.systemMessageData = res.data.list;
          for (let i=0;i<this.systemMessageData.length;i++){
            this.systemMessageData[i].content = marked(this.systemMessageData[i].content);
          }
          this.queryData.total = res.data.total;
        });
      },
    },
    mounted() {
      this.reqInfo();
    }
  }
</script>

<style scoped>
.system-message{
  overflow: hidden;
  padding-top: 20px;
  border-radius: 8px;
  background-color: #ffffff;
  margin-bottom: 10px;
  border: 1px solid #e6e6e6;
}

.system-message .header{
  margin-top: 0;
  padding-left: 30px;
  padding-bottom: 16px;
  margin-bottom: 16px;
  border-bottom: 1px solid #e6e6e6;
}

.system-message .message-container{
  padding: 0 20px 30px;
  min-height: 340px;
}

.system-message .message-card{
  position: relative;
  margin-bottom: 25px;
}

.message-card .publish-time{
  position: absolute;
  right: 20px;
  top: 12px;
  color: #999;
  font-size: 13px;
}

.message-card .message-content{
  width: 100%;
  font-size: 15px;
  line-height: 26px;
  color: rgba(0, 0, 0, 0.65);
  text-align: justify;
  text-overflow: ellipsis;
}

.message-card .set-width{
  width: 88% !important;
}

.message-card .link{
  position: absolute;
  top: 0;
  right: 22px;
  height: 100%;
  display: flex;
  align-items: center;
}

.system-message .page{
  padding: 5px 12px;
  background: rgb(255, 255, 255);
  margin: -10px auto 14px;
  text-align: center;
}

</style>

<style>
  .system-message .el-card__header{
    padding: 10px 20px;
    background-color: #F9F9F9;
    position: relative;
  }

  .system-message .el-card__body{
    padding: 0 20px;
    min-height: 50px;
    position: relative;
    display: flex;
    align-items: center;
  }

  .message-card .message-content>p{
    margin: 5px 0!important;
  }

  .message-card .message-content ol{
    padding-left: 20px;
    margin: 8px 0;
  }
</style>

