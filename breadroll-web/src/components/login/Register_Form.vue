<template>
  <div class="register-form">
    <el-header class="header" height="45px">
      <h2 class="title">注册</h2>
      <span class="pull-right">
      已有账号？
      <router-link class="link" to="/loginForm">点此登录</router-link>
    </span>
    </el-header>
    <div class="form">
      <el-form :model="registerForm" status-icon :rules="rules" ref="registerForm">
        <el-form-item prop="userName">
          <el-input v-model="registerForm.userName" autocomplete="on" placeholder="请输入昵称" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item prop="userAccount">
          <el-input v-model="registerForm.userAccount" autocomplete="off" placeholder="请输入邮箱" prefix-icon="el-icon-message"></el-input>
        </el-form-item>
        <el-form-item prop="checkNumber">
          <el-input style="width: 62%" v-model="registerForm.checkNumber" placeholder="验证码" prefix-icon="el-icon-position"></el-input>
          <el-image @click="getCode" class="check-code" :src="checkCodeUrl" style="height: 40px;" title="看不清，换一张"></el-image>
        </el-form-item>
        <el-form-item prop="checkCode">
          <el-input v-model="registerForm.checkCode" :disabled="codeRight" autocomplete="off" placeholder="邮箱验证码" prefix-icon="el-icon-link"></el-input>
          <el-button plain style="position: absolute;right: 0; width: 40%;top: 0;" v-preventReClick @click="sendEmailCode('registerForm',$event)">发送验证码</el-button>
        </el-form-item>
        <el-form-item prop="passWord">
          <el-input type="password" auto-complete="new-password" show-password v-model="registerForm.passWord" autocomplete="off" placeholder="请输入密码" prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="submit" v-preventReClick @click="submitForm('registerForm')">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register_Form",
  data() {
    let CheckEmail = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入邮箱'));
      }else {
        if (this.$tools.checkEmail(value)) {
          callback();
        }
        return callback(new Error("请输入正确的邮箱"));
      }
    };
    let CheckCode = async (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'));
      }
      if (this.checkCodeErrorMessage!==null){
        callback(new Error(this.checkCodeErrorMessage));
      }else{
        callback()
      }
    };
    let CheckPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.$tools.checkPass(value)) {
          callback();
        }
        return callback(new Error("以字母开头，长度在6~18之间，只能包含字母、数字和下划线"));
      }
    };
    return {
      // 验证码错误信息
      checkCodeErrorMessage:null,
      //临时存放发送验证码按钮，方便后面清除按钮上倒计时的定时器
      codeButtonTemp:null,
      //验证码图片URL
      checkCodeUrl:null,
      //记住我
      rememberMe:false,
      //验证码是否正确
      codeRight:true,
      //登录表单
      registerForm:{
        userAccount:'',
        userName:'',
        passWord:'',
        checkCode:'',
        checkNumber:'',
      },
      //表单的验证规则
      rules: {
        userAccount: [
          { validator: CheckEmail, trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        checkCode: [
          { required: true, message: '请输入邮箱验证码', trigger: 'blur' }
        ],
        passWord: [
          { validator: CheckPass, trigger: 'blur' }
        ],
        checkNumber: [
          { validator: CheckCode, trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    //请求验证码
    getCode(){
      this.checkCodeUrl = this.$globalApi.getImageCode();
    },
    //提交注册信息
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$loginApi.registered(this.registerForm).then(res=>{
            //清除发送验证码按钮倒计时的定时器
            this.$countDown.removeItem(this.codeButtonTemp);
            this.$message.success(res.message);
            this.$router.push("/loginForm");
          })
        } else {
          return false;
        }
      });
    },
    //发送手机验证码
    sendEmailCode(formName,event){
      this.codeButtonTemp = event.currentTarget;
      let va = true;
      this.$refs[formName].validateField('userAccount',(valid)=>{
        va = valid==="";
      })
      this.$refs[formName].validateField('checkNumber',(valid)=>{
        va = va && valid==="";
      })
      this.$refs[formName].validateField('userName',(valid)=>{
        va = va && valid==="";
      })
      if (va) {
        this.$globalApi.checkImageCode(this.$store.state.checkCodeKey,this.registerForm.checkNumber).then(()=>{
          this.$globalApi.getEmailCode(this.registerForm.userAccount,this.registerForm.userName).then(()=>{
            this.codeRight = false;
            this.$countDown.setItem(this.codeButtonTemp);
            this.$message.success("验证码发送成功");
          })
        }).catch(err=>{
          this.checkCodeErrorMessage=err.message;
          this.$refs[formName].validateField('checkNumber')
          this.checkCodeErrorMessage=null;
          this.getCode();
        })
      }
    }
  },
  created() {
    //刷新下验证码
    this.getCode();
  }
}
</script>


<style>
.register-form .header{
  position: relative;
  text-align: left;
  border: none;
  padding: 0;
}

.register-form .header .title{
  margin: 0;
}

.register-form .header .pull-right{
  position: absolute;
  top: 15px;
  right: 20px;
  font-size: 14px;
}
.register-form .header .pull-right .link{
  text-decoration: none;
  cursor: pointer;
  color: #005980;
}
.register-form .form{
  text-align: left;
}

.register-form .form .check-code{
  position: absolute;
  right: 20px;
  cursor: pointer;
}


.register-form .form .submit{
  width: 100%;
}

.register-form .el-form-item{
  margin-bottom: 19px;
}
</style>
