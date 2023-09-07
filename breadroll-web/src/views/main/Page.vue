<template>
  <el-container class="page">
    <!--导航栏-->
    <el-header class="el-header">
      <el-menu router :default-active="$route.path" mode="horizontal" class="navigation" text-color="#000" active-text-color="#409EFF">
        <el-menu-item index="/homePage" style="padding: 0 25px;border-bottom: none!important;">
          <el-image style="width: 200px; height: 60px" :src="logoImg" fit="fill"></el-image>
        </el-menu-item>
        <el-menu-item index="/homePage">首页</el-menu-item>
        <el-menu-item index="/coursePage">课程</el-menu-item>
        <el-menu-item index="/special-training">特训班</el-menu-item>
        <el-menu-item style="border-bottom: 1px solid transparent!important;">
          <el-input placeholder="请输入内容" v-model="queryKey" @keyup.enter.native="searchCourse(queryKey)" style="width: 450px">
            <el-button slot="append" icon="el-icon-search" @click="searchCourse(queryKey)"></el-button>
          </el-input>
        </el-menu-item>
        <el-submenu index="" style="border-bottom: none!important;">
          <template slot="title">课程类别</template>
          <el-menu-item class="course-style" v-for="(item,index) in courseType" @click="queryCourse(item)" :key="index">{{item.typeName}}</el-menu-item>
        </el-submenu>
        <el-menu-item style="position: absolute;right: 20px;;border-bottom: none!important;">
          <div v-if="token">
            <el-badge :is-dot="newState" class="el-badge">
              <router-link to="/personalMessage">
                <i style="color: #909399;!important;" class="iconfont iconxiaoxi11 animated rubberBand" title="消息中心"/>
              </router-link>
            </el-badge>
            <!--用户个人资料卡-->
            <el-popover
              placement="bottom"
              width="290"
              trigger="hover">
              <div class="title">
                <!--用户头像-->
                <el-avatar slot="reference" class="avatar" :size="70" :src="userAvatar">
                  <img :src="defaultAvatar" alt="默认头像"/>
                </el-avatar>
                <h2><span class="name">{{userName}}</span>
                  <svg v-if="isVip" class="icon vip animated rubberBand" aria-hidden="true">
                    <use :xlink:href="vipIcon"></use>
                  </svg>
                </h2>
                <span class="gold" title="花卷币" @click="toGold">
                  <svg class="icon" aria-hidden="true">
                    <use xlink:href="#iconmantou"></use>
                  </svg>
                  <span>{{userCoin}}</span>
                </span>
                <p v-if="user.userAccount">帐号:{{userAccount}}</p>
              </div>
              <div class="operate">
                <el-button type="primary" :disabled="isSign" size="mini" round icon="el-icon-position" @click="userSign">
                  <span v-if="isSign">今日已签到</span>
                  <span v-else>今日签到</span>
                </el-button>
                <el-button type="warning" size="mini" round icon="el-icon-thumb" @click="openMember">开通VIP</el-button>
              </div>
              <div class="menu">
                <el-button style="display: none">我是无用的按钮</el-button>
                <el-button plain @click="toUser"><i class="iconfont iconzhanghu"></i>账户中心</el-button>
                <el-button plain @click="toLearn"><i class="iconfont iconxinbaniconshangchuan-"></i>学习中心</el-button>
                <el-button plain @click="toOrder"><i class="iconfont icondingdan1"></i>我的订单</el-button>
                <el-button plain @click="logout"><i class="iconfont iconexit"></i>安全退出</el-button>
              </div>
              <!--用户头像-->
              <el-avatar slot="reference" :size="40" :src="userAvatar">
                <img :src="defaultAvatar" alt="默认头像"/>
              </el-avatar>
            </el-popover>
          </div>
          <div v-else>
            <el-button @click="toLogin">登录</el-button>
            <el-button type="primary" @click="toRegister">注册</el-button>
          </div>
        </el-menu-item>
      </el-menu>
    </el-header>
    <!--内容区域-->
    <el-main class="el-main">
      <router-view/>
    </el-main>
    <!--底部导航-->
    <el-footer class="el-footer">
      <div class="friend-chain">
        <ul>
          <li>友情链接 :</li>
          <li><a href="https://api.vvhan.com/" target="_blank" title="韩小韩API接口站">韩小韩API接口站</a></li>
          <li><a href="http://www.uugai.com/" target="_blank" title="LOGO在线设计">LOGO在线设计</a></li>
          <li><a href="https://translate.google.cn/" target="_blank" title="谷歌翻译">谷歌翻译</a></li>
        </ul>
      </div>
      <div class="description">
        <div class="contact-us">
          <el-image class="qr-code" :src="logoWriteImg"></el-image>
        </div>
        <div class="nav">
          <ul class="services">
            <li><span class="title">支持与服务</span></li>
            <li><a>花卷会员</a></li>
            <li><a>使用手册</a></li>
            <li><a>课程动态</a></li>
          </ul>
          <ul class="services">
            <li><span class="title">快速入口</span></li>
            <li><a>课程学习</a></li>
            <li><a>个人中心</a></li>
            <li><a>学习锦囊</a></li>
          </ul>
          <ul class="services">
            <li><span class="title">资源和社区</span></li>
            <li><a>常见问题</a></li>
            <li><a>支持计划</a></li>
            <li><a>帮助中心</a></li>
          </ul>
          <ul class="services">
            <li><span class="title">关于</span></li>
            <li><a>服务协议</a></li>
            <li><a>意见反馈</a></li>
            <li><a>隐私和法律声明</a></li>
          </ul>
        </div>
        <div class="about">
          <h2>联系我们</h2>
          <div>
            <h3>0765-000-0000</h3>
            <p>周一至周五 9:00 - 18:00</p>
            <p>邮箱：email@email.mt</p>
            <p>地址：河南省郑州市高新区</p>
          </div>
        </div>
      </div>
      <div class="copyright">© 2020 - {{new Date().getFullYear()}} 在线教育版权所有&nbsp;&nbsp;冀ICP备2021000352号&nbsp;&nbsp;</div>
    </el-footer>
  </el-container>
</template>

<script>
  export default {
    name: "HomePage",
    data() {
        return{
          //默认头像
          defaultAvatar:this.$config.getDefaultAvatar(),
          //logo
          logoImg:require("../../assets/global/logo.png"),
          logoWriteImg:require("../../assets/global/logo-write.png"),
          //登录凭证 token
          token:this.$cookie.getToken(),
          //用户信息
          user:this.$store.state.userInfo,
          //搜索关键字
          queryKey:'',
          //今日是否签到
          isSign:false,
          //课程类别
          courseType:[],
        }
    },
    methods:{
      //通过类别搜索课程
      queryCourse(item){
        if(item!==null){
          this.$router.push({ path: '/coursePage', query: { title: item.typeName,way:"type",id:item.typeId }});
        }
      },
      //通过关键词搜索课程
      searchCourse(queryKey){
        if(queryKey!==null && queryKey.length!==0){
          this.$router.push({ path: '/coursePage', query: { way:"name",name:queryKey }});
          this.queryKey="";
        }
      },
      //到注册页面
      toRegister(){
        this.$router.push("/registerForm");
      },
      //到登录页面
      toLogin(){
        this.$router.push("/loginForm");
      },
      //到用户中心
      toUser(){
        this.$router.push("/accountCenter");
      },
      //到学习中心
      toLearn(){
        this.$router.push("/courseCenter");
      },
      //到订单中心
      toOrder(){
        this.$router.push("/orderCenter");
      },
      //到花卷币
      toGold(){
        this.$router.push("/breadRollGold");
      },
      //退出系统
      logout() {
        this.$confirm('您确定要退出吗?', '温馨提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$loginApi.logout().then(res=>{
            this.$notify({title: '成功', message: res.message, type: 'success'});
            this.$router.push("/loginForm");
            this.$cookie.clearToken();
            this.$store.commit("saveUserInfo","");
          });
        }).catch(() => {});
      },
      //今日签到
      userSign(){
        this.$userApi.userSign().then(res=>{
          this.isSign=true;
          this.$message.success(res.message);
          //查询花卷币数量
          this.$userApi.queryCoin().then(res=>{
            let userCoin = res.data;
            this.$store.commit("saveUserCoin",userCoin);
          });
        });
      },
      //开通VIP
      openMember(){
        this.$router.push("/memberDetails");
      }
    },
    computed:{
      newState:function(){
        return this.$store.state.messageState;
      },
      userCoin:function(){
        return this.$store.state.userCoin;
      },
      userName:function(){
        return this.$store.state.userInfo.userName;
      },
      userAccount:function(){
        return this.$store.state.userInfo.userAccount;
      },
      userAvatar:function(){
        if(this.$store.state.userInfo!=null && this.$store.state.userInfo.avatarUrl!=null){
          return this.$config.getBaseUrl() + this.$store.state.userInfo.avatarUrl;
        }
      },
      isVip:function(){
        if(this.$store.state.vipInfo!=null && this.$store.state.vipInfo.isVip!=null){
          return this.$store.state.vipInfo.isVip;
        }
        return false;
      },
      vipIcon:function(){
        if(this.$store.state.vipInfo!=null && this.$store.state.vipInfo.vipIcon!=null){
          return this.$store.state.vipInfo.vipIcon;
        }
      },
    },
    mounted(){
      if(this.$cookie.getToken()){
        //检测今日是否签到
        this.$userApi.checkSign().then(res=>{
          this.isSign = res.data.isSign;
          this.$store.commit("saveMessageState",res.data.newMessage);
        });
        //查询花卷币数量
        this.$userApi.queryCoin().then(res=>{
          let userCoin = res.data;
          this.$store.commit("saveUserCoin",userCoin);
        });
      }
      //获取课程类别
      this.$courseApi.queryCourseType().then(res=>{
        this.courseType = res.data;
      });
    }
  }
</script>

<style scoped>
  .page{
    width: 100%;
    background-color: #fafbfc;
  }

  .el-header{
    border-bottom: solid 1px #e6e6e6;
    height: 100% !important;
    width: 100%;
  }

  .el-header>.navigation{
    width: 90%;
    margin: 0 auto;
    min-width: 1420px;
    position: relative;
    border-bottom: none!important;
  }

  .el-header .el-menu--horizontal>.el-menu-item{
    font-size: 16px;
    font-weight: 600;
    padding: 0 30px;
  }

  .el-header .el-button{
    padding: 10px 20px;
    width: 90px;
  }


  .el-main{
    min-height: 85vh;
    padding: 0;
  }

  .el-footer{
    padding: 0;
    height: auto!important;
    background-color: #252525;
  }

  .el-footer .friend-chain{
    width: 100%;
    height: 52px;
    margin: 0 auto;
    background-color: #252525;
    font-size: 15px;
    color: #999999;
  }
  .el-footer .friend-chain ul{
    width: 80%;
    margin: 0 auto;
    line-height: 52px;
    list-style: none;
  }
  .el-footer .friend-chain ul li{
    float: none;
    display: inline-block;
  }

  .el-footer a{
    color: #999999;
    transition: color 0.2s ease-out;
  }

  .el-footer a:hover{
    color: #ff6a00!important;
  }

  .el-footer .friend-chain ul li:nth-child(1) {
    color: #ffffff;
    padding-right: 5px;
  }

  .el-footer .friend-chain ul li:nth-child(n+3)::before {
    display: inline-block;
    content: '/';
    color: #ccd5db;
    padding: 0 5px;
  }

  .el-footer .description{
    display: grid;
    height: 195px;
    width: 100%;
    margin: 0 auto;
    min-width: 1450px;
    text-align: left;
    grid-template-columns: .7fr 1.2fr .8fr;
    border-top: 2px solid #ff6a00!important;
  }

  .el-footer .description>div{
    width: 100%;
    height: 100%;
    color: #FFFFFF;
  }

  .el-footer .description>div h2{
    margin:25px 0 25px 40px;
    font-size: 20px;
  }

  .el-footer .description .nav{
    margin-left: 5%;
    width: 95%;
    min-width: 700px;
  }

  .el-footer .description .nav .services{
    float: left;
    width: 128px;
    list-style-type: none;
  }

  .el-footer .description .nav .services .title {
    display: block;
    text-align: left;
    line-height: 35px;
    margin: 12px 0;
    font-size: 17px;
    color: #d7d8d9;
  }

  .el-footer .description .nav .services a {
    display: block;
    cursor: pointer;
    text-align: left;
    line-height: 35px;
    font-size: 14px;
    color: #9b9ea0;
    text-decoration: none;
    -webkit-transition: color .3s;
    transition: color .3s;
  }

  .el-footer .description .about div{
    margin-left: 39px;
  }

  .el-footer .description .about h3{
    color: #ff6a00!important;
    font-size: 24px;
    margin: 0 0 10px 0;
  }

  .el-footer .description .about p{
    color: #999999;
    font-size: 14px;
    line-height: 20px;
    margin: 0 0 5px 0;
  }

  .el-footer .description .contact-us{
    position: relative;
    height: 76%;
    margin-top: 22px;
    border-right: 1px solid #4b5054;

  }

  .el-footer .description .contact-us .qr-code{
    position: absolute;
    width: 320px;
    height: 100px;
    right: 40px;
  }

  .el-footer .description .contact-us p{
    position: absolute;
    color: #999999;
    font-size: 16px;
    width: 128px;
    left: 162px;
  }

  .el-footer .copyright{
    height: 30px;
    line-height: 30px;
    font-size: 15px;
    color: #9b9ea0;
  }

  .el-header .el-badge{
    height: 26px;
    right: 30px;
    top: 0;
  }

  .el-header .el-badge i{
    font-size: 21px;
    position: absolute;
    left: -22px;
    line-height: 28px;
  }

  .el-header .el-badge i:hover{
    color: #1890ff;
  }

  .el-popover .title{
    position: relative;
    height: 90px;
  }

  .el-popover .avatar{
    position: absolute;
    left: 14px;
  }

  .el-popover h2{
    position: absolute;
    left: 100px;
    color: #333333;
    font-size: 18px;
    margin: 6px 0 0 0;
  }

  .el-popover .name{
    display: inline-block;
    vertical-align: middle;
    text-align: left;
    max-width: 77px;
    height: 28px;
    overflow: hidden;
  }

  .el-popover .vip{
    font-size: 27px;
  }

  .el-popover .gold{
    display: inline-block;
    position: absolute;
    left: 215px;
    width: 32px;
    font-size: 15px;
    margin: 10px 0 0 0;
    cursor: pointer;
  }

  .el-popover .gold svg{
    display: inline-block;
    vertical-align: middle;
    font-size: 20px;
    font-weight: 500;
  }

  .el-popover .gold>span{
    font-size: 14px;
    font-weight: 900;
    color: #999999;
    display: inline-block;
    position: absolute;
    width: 65px;
    top: 4px;
    left: 21px;
  }

  .el-popover p{
    position: absolute;
    left: 100px;
    top: 30px;
    font-size: 14px;
    font-weight: 500;
    color: #999999;
  }

  .el-popover .operate{
    text-align: center;
  }

  .el-popover .menu{
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-row-gap: 10px;
    grid-column-gap: 10px;
    margin-top: 15px;
  }

  .el-popover .menu .el-button i{
    margin-right: 5px;
    font-size: 18px;
    display: inline-block;
    vertical-align: middle;
  }

  .el-menu .course-style{
    color: #909399!important;
  }

  .el-menu .course-style:hover{
    color: #303133!important;
  }

</style>

<style>
.el-header>.navigation .el-submenu__title{
  border-bottom: none!important;
  color: #303133!important;
}

</style>

