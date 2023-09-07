<template>
  <div class="user-page">
    <div class="user-container">
      <div class="user-box" :style="background">
        <el-upload
          style="display: inline-block"
          :on-success="userBackgroundSuccess"
          :show-file-list="false"
          :before-upload="this.$tools.beforePictureUpload"
          :headers="this.$tools.getMyHeaders()"
          :action="this.$userApi.uploadBackground()">
          <el-button plain class="upload-background" size="small" icon="el-icon-camera">背景图片 </el-button>
        </el-upload>
        <div class="user-info">
          <!--用户头像-->
          <el-avatar slot="reference" class="avatar" :size="80" :src="userAvatar">
            <img :src="defaultAvatar" alt="默认头像"/>
          </el-avatar>
          <el-upload
            style="display: inline-block"
            :on-success="userAvatarSuccess"
            :show-file-list="false"
            :before-upload="this.$tools.beforePictureUpload"
            :headers="this.$tools.getMyHeaders()"
            :action="this.$userApi.uploadAvatar()">
            <div class="over-avatar">更换头像</div>
          </el-upload>
          <div class="user-name">
            <h2 v-text="userName" style="margin-right: 10px;vertical-align: middle;"></h2>
            <div v-if="userGender" class="gender" :class="[userGender==='男'?'man':'woman']">
              <i v-if="userGender==='男'" class="el-icon-male"/>
              <i v-else class="el-icon-female"/>
            </div>
          </div>
          <p class="description">{{description}}</p>
        </div>
      </div>
      <div class="user-center">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="个人中心" name="user">
            <div class="set-box">
              <ul>
                <li v-for="(item,index) in userMenu" :key="index" @click="userClick(index,item.url)"
                  :class="[active.user === index ? 'li-active':'']" >{{item.title}}</li>
              </ul>
              <div class="data">
                <router-view/>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="学习中心" name="course">
            <div class="set-box">
              <ul>
                <li v-for="(item,index) in courseMenu" :key="index" @click="courseClick(index,item.url)"
                    :class="[active.course === index ? 'li-active':'']" >{{item.title}}</li>
              </ul>
              <div class="data">
                <router-view/>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="消息中心" name="message">
            <div class="set-box">
              <ul>
                <li v-for="(item,index) in messageMenu" :key="index" @click="messageClick(index,item.url)"
                    :class="[active.message === index ? 'li-active':'']" >{{item.title}}</li>
              </ul>
              <div class="data">
                <router-view/>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="订单中心" name="order">
            <div class="set-box">
              <ul>
                <li v-for="(item,index) in orderMenu" :key="index" @click="orderClick(index,item.url)"
                    :class="[active.order === index ? 'li-active':'']" >{{item.title}}</li>
              </ul>
              <div class="data">
                <router-view/>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "UserPage",
    data() {
        return{
          //默认头像
          defaultAvatar:this.$config.getDefaultAvatar(),
          //默认背景图片
          defaultBackground:this.$config.getDefaultBackground(),
          //用户信息
          user:this.$store.state.userInfo,
          //当前Tab
          activeName:"user",
          //个人中心
          userMenu:[{title:'账户中心',url:'/accountCenter'},{title:'个人信息',url:'/userCenter'}],
          //学习中心
          courseMenu:[{title:'我的课程',url:'/courseCenter'},{title:'最近学习',url:'/recentLearned'}],
          //订单中心
          orderMenu:[{title:'我的订单',url:'/orderCenter'},{title:'我的花卷币',url:'/breadRollGold'},{title:'我的特训班',url:'/mySpecial'}],
          //学习中心
          messageMenu:[{title:'系统消息',url:'/systemMessage'},{title:'我的消息',url:'/personalMessage'}],
          //当前活动菜单
          active:{user:0, course:0, order:0, message:0},
        }
    },
    // 监听,当路由发生变化的时候执行
    watch: {
      $route: {
        handler: function(val){
          this.selected(val.path);
        },
        // 深度观察监听
        deep: true
      }
    },
    methods:{
      selected(path){
        switch (path) {
          case "/accountCenter":{
            this.activeName = "user";
            this.active.user = 0;
            break;
          }
          case "/userCenter":{
            this.activeName = "user";
            this.active.user = 1;
            break;
          }
          case "/orderCenter":{
            this.activeName = "order";
            this.active.order = 0;
            break;
          }
          case "/breadRollGold":{
            this.activeName = "order";
            this.active.order = 1;
            break;
          }
          case "/mySpecial":{
            this.activeName = "order";
            this.active.order = 2;
            break;
          }
          case "/courseCenter":{
            this.activeName = "course";
            this.active.course = 0;
            break;
          }
          case "/recentLearned":{
            this.activeName = "course";
            this.active.course = 1;
            break;
          }
          case "/systemMessage":{
            this.activeName = "message";
            this.active.message = 0;
            break;
          }
          case "/personalMessage":{
            this.activeName = "message";
            this.active.message = 1;
            break;
          }
        }
      },
      handleClick(tab) {
        let index = parseInt(tab.index);
        switch (index) {
          case 0:{
            this.active.user=0;
            this.$router.push(this.userMenu[0].url);
            break;
          }
          case 1:{
            this.active.course=0;
            this.$router.push(this.courseMenu[0].url);
            break;
          }
          case 2:{
            this.active.message=0;
            this.$router.push(this.messageMenu[0].url);
            break;
          }
          case 3:{
            this.active.order=0;
            this.$router.push(this.orderMenu[0].url);
            break;
          }
        }
      },
      userClick(index,url){
        this.active.user=index;
        this.$router.push(url);
      },
      courseClick(index,url){
        this.active.course=index;
        this.$router.push(url);
      },
      messageClick(index,url){
        this.active.message=index;
        this.$router.push(url);
      },
      orderClick(index,url){
        this.active.order=index;
        this.$router.push(url);
      },
      //上传头像
      userAvatarSuccess(res){
        this.user.avatarUrl = res.data.url;
        this.$store.commit("saveUserInfo",this.user);
        this.$message.success('上传成功');
      },
      //上传背景图片
      userBackgroundSuccess(res){
        this.user.backgroundUrl = res.data.url;
        this.$store.commit("saveUserInfo",this.user);
        this.$message.success('上传成功');
      },
    },
    mounted() {
      this.selected(this.$route.path);
    },
    computed:{
      userName:function(){
        return this.$store.state.userInfo.userName;
      },
      userAccount:function(){
        return this.$store.state.userInfo.userAccount;
      },
      userAvatar:function(){
        return this.$config.getBaseUrl() + this.$store.state.userInfo.avatarUrl;
      },
      background:function(){
        if(this.$store.state.userInfo.backgroundUrl!==null){
          return "background-image:url(" + this.$config.getBaseUrl() + this.$store.state.userInfo.backgroundUrl + "),url("+this.defaultBackground+")";
        }
      },
      userGender:function(){
        return this.$store.state.userInfo.userGender;
      },
      description:function(){
        return this.$store.state.userInfo.description;
      },
    },
  }
</script>

<style scoped>
  .user-page{
    background-color: #F9F9F9!important;
    width: 100%;
  }

  .user-container{
    width: 80%;
    margin: 30px auto;
    min-width: 1450px;
  }

  .user-box{
    position: relative;
    height: 280px;
    border-radius: 5px;
    box-shadow: 0 10px 20px 0 rgba(75,91,138,0.1);
    background: #FFFFFF url(../../assets/global/background.jpg) center no-repeat;
    background-size: cover;
  }

  .user-box .upload-background{
    position: absolute;
    top: 12px;
    right: 20px;
    width: 110px;
    color: rgba(255,255,255,.8);
    background-color: rgba(0,0,0, 0.3);
  }

  .user-box .upload-background:hover{
    color: #409eff!important;
  }

  .user-info{
    position: absolute;
    bottom: 0;
    height: 120px;
    width: 100%;
    background-color: rgba(0,0,0,.8);
    text-align: left;
  }

  .user-info .avatar{
    position: absolute;
    top: 20px;
    left: 30px;
  }

  .user-page .user-info .over-avatar{
    position: absolute;
    opacity: 0;
    top: 20px;
    left: 30px;
    width: 80px;
    height: 80px;
    line-height: 80px;
    font-size: 14px;
    text-align: center;
    z-index: 10;
    color: #fff;
    background: rgba(0,0,0,0.5);
    cursor: pointer;
    border-radius: 50%;
    user-select: none;
  }

  .user-page .user-info .over-avatar:hover{
    opacity: 1;
  }

  .user-info .user-name{
    position: absolute;
    top: 28px;
    left: 135px;
  }

  .user-info h2{
    font-size: 30px;
    color: #FFFFFF;
    display: inline-block;
    margin: 0 0 7px;
  }

  .user-info .gender{
    width: 28px;
    height: 28px;
    display: inline-block;
    line-height: 32px;
    text-align: center;
    border-radius: 50%;
  }

  .man{
    background-color: #007BFF;
  }

  .woman{
    background-color: #ED4F82;
  }

  .user-info .gender i{
    font-weight: 600;
    font-size: 19px;
  }

  .user-info .el-icon-male{
    color: #CAE3FF;
  }

  .user-info .el-icon-female{
    color: #ffccc7;
  }

  .user-info .description{
    position: absolute;
    color: #FFFFFF;
    font-size: 18px;
    left: 132px;
    top: 62px;
  }


  .user-center{
    padding: 10px 0;
    border-radius: 5px;
    box-shadow: 0 10px 20px 0 rgba(75,91,138,0.1);
    background-color: #FFFFFF;
  }

  .user-center .set-box{
    display: grid;
    grid-template-columns: 210px 1fr;

  }

  .user-center .set-box>ul{
    margin: 0;
    padding: 0;
    min-height: 60vh;
    border-right: 2px solid #e6e6e6;
  }

  .user-center .set-box li{
    font-size: 18px;
    margin: 0 5px 0 0;
    padding: 0 0 0 30px;
    height: 55px;
    width: 148px;
    line-height: 55px;
    cursor: pointer;
  }

  .user-center .set-box li:hover{
    background: #f0f0f0;
  }

  .li-active{
    background: #5B6066!important;
    color: #FFF;
  }

  .user-center .data{
    padding: 0 20px 20px;
  }

</style>

<style>
  .user-center .el-tabs__nav-scroll{
    padding: 0 20px;
  }

  .user-center .el-tabs__item{
    font-size: 20px!important;
    height: 55px!important;
    color: #333333;
  }

  .user-center .is-active{
    font-weight: 800!important;
    color: #333333!important;
  }

  .user-center .el-tabs__content{
    text-align: left;
  }
</style>
