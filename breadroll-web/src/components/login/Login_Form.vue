<template>
  <div class="login-form">
    <el-header class="header" height="45px">
      <h2 class="title">登录</h2>
      <span class="pull-right">
        没有账号？
        <router-link class="link" to="/registerForm">点此注册</router-link>
      </span>
    </el-header>
    <div class="form">
      <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm">
        <el-form-item prop="userAccount">
          <el-input  v-model="loginForm.userAccount" autocomplete="on" placeholder="请输入帐号" @keyup.enter.native="submitForm('loginForm')" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item prop="passWord">
          <el-input type="password" show-password v-model="loginForm.passWord" autocomplete="off" placeholder="请输入密码" @keyup.enter.native="submitForm('loginForm')" prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item prop="checkCode" style="margin-bottom: 12px;">
          <el-input style="width: 62%" v-model="loginForm.checkCode" placeholder="验证码" @keyup.enter.native="submitForm('loginForm')" prefix-icon="el-icon-position"></el-input>
          <el-image @click="getCode" class="check-code" :src="checkCodeUrl" style="height: 40px;" title="看不清，换一张"></el-image>
        </el-form-item>
        <el-form-item style="margin-bottom: 10px;">
          <el-checkbox v-model="rememberMe">记住我</el-checkbox>
          <router-link to="/forgotPassword"  class="retrieve-password">找回密码</router-link>
        </el-form-item>
        <el-form-item style="margin-bottom: 10px;">
          <el-button type="primary" class="submit" v-preventReClick @click="submitForm('loginForm')">登录</el-button>
        </el-form-item>
      </el-form>
      <div style="width: 90%;margin: 0 auto;text-align: center">
        <el-divider>其他方式登录</el-divider>
        <div class="my-icon">
          <i class="iconfont iconqq qq" @click="qqLogin"></i>
          <i class="iconfont iconweixin weixin" @click="weixinLogin"></i>
          <i class="iconfont icondingding01 dingding" @click="dingLogin"></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login-Form",
  data() {
    return {
      //checkCodeKey
      checkCodeKey:null,
      //验证码图片URL
      checkCodeUrl:null,
      //记住我
      rememberMe:true,
      //登录表单
      loginForm:{
        userAccount:'',
        passWord:'',
        checkCode:'',
        checkCodeKey:''
      },
      //表单的验证规则
      rules: {
        userAccount: [
          { required: true, message: '请输入帐号', trigger: 'blur' }
        ],
        passWord: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        checkCode: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    qqLogin(){
      this.$message.success("QQ登录");
    },
    weixinLogin(){
      this.$message.warning("微信登录");
    },
    dingLogin(){
      this.$message.info("钉钉登录");
    },
    //请求验证码
    getCode(){
      this.checkCodeUrl = this.$globalApi.getImageCode();
    },
    //提交登录信息
    submitForm(formName) {
       this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loginForm.checkCodeKey = this.$store.state.checkCodeKey;
          this.$loginApi.login(this.loginForm).then(res=>{  //登录成功
            this.$userApi.queryMyVip().then(res=>{
              this.$store.commit("saveVipInfo",res.data);
            });
            //保存token
            this.$cookie.setToken(res.data.token);
            //保存用户信息
            this.$store.commit('saveUserInfo',res.data.user);
            //提示信息
            this.$notify({title: "成功", message:res.message, type: 'success',offset:50});
            //跳转首页
            this.$router.push("/homePage");
          }).catch(()=>{  //登录失败
            //清空验证码
            this.loginForm.checkCode='';
            //清空密码
            this.loginForm.password='';
            //刷新验证码
            this.getCode();
          })
        } else {
          return false;
        }
      });
    },
  },
  created() {
    this.getCode();
  }
}
</script>

<style scoped>
.login-form .header{
  position: relative;
  text-align: left;
  border: none;
  padding: 0;
}

.login-form .header .title{
  margin: 0;
}

.login-form .header .pull-right{
  position: absolute;
  top: 15px;
  right: 0;
  font-size: 14px;
}
.login-form .header .pull-right .link{
  text-decoration: none;
  cursor: pointer;
  color: #005980;
}

.login-form .form{
  text-align: left;
}

.login-form .form .check-code{
  position: absolute;
  right: 20px;
  cursor: pointer;
}

.login-form .form .phone-code{
  float: right;
  text-decoration:none;
  color: #005980;
}
.login-form .form .retrieve-password{
  text-decoration:none;
  float: right;
  color: #005980;
}

.login-form .form .submit{
  width: 100%;
}

/*第三方登录图标*/
.login-form .my-icon i{
  user-select: none;
  cursor: pointer;
  font-size: 32px;
  margin-left: 48px;
}

.login-form .el-divider--horizontal{
  margin: 40px 0 20px;
}

.login-form .qq{
  color: #00b0fb;
  margin-left: 0 !important;
}
.login-form .weixin{
  color: #46d800;
}
.login-form .dingding{
  color: #3795f9;
}
</style>
