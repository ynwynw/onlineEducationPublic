<template>
  <div id="app">
    <!--回到顶部-->
    <el-backtop style="height: 50px;width: 50px;"><i class="el-icon-top"/></el-backtop>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  created() {
    if(this.$cookie.getToken()===null){
      this.$store.commit("saveUserInfo",null);
      this.$store.commit("saveVipInfo",null);
    }else{
      this.$userApi.checkState().then(res=>{
        let vipInfo;
        if(this.$store.state.vipInfo!==null){
          vipInfo = this.$store.state.vipInfo;
          vipInfo.isVip = res.data.isVip;
        }else{
          vipInfo.isVip = res.data.isVip;
        }
        this.$store.commit("saveVipInfo",vipInfo);
      });
    }
    if(this.$store.state.userInfo===null && this.$cookie.getToken()!==null){
      this.$userApi.getUserInfo().then(res=>{
        this.$store.commit('saveUserInfo',res.data);  //保存用户信息
      });
    }
    if(this.$store.state.vipInfo===null && this.$cookie.getToken()!==null){
      this.$userApi.queryMyVip().then(res=>{
        this.$store.commit("saveVipInfo",res.data);
      });
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  width: 100%;
}
/*elementUI表头与表格居中*/
.el-table th,.el-table td{
  text-align: center!important;
}

/*解决elementUI表头与表格对齐问题*/
.el-table th.gutter{
  display: table-cell!important;
}

/*dialog 对话框遮罩层最小1024*/
.el-dialog__wrapper{
  min-width: 1540px;
}

/*dialog 对话框标题加下划线*/
.el-dialog .el-dialog__header{
  border-bottom: 1px solid #e3e9ed;
}
.el-dialog .el-dialog__body{
  padding: 20px 20px 0;
}

a{
  text-decoration: none;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

@keyframes ran {
  from {
    backgroud-position: 0 0;
  }
  to {
    background-position: 2000px 0;
  }
}
</style>
