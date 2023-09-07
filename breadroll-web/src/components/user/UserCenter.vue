<template>
  <div class="user-form">
    <el-form ref="userForm" :model="userForm" label-position="top" inline :rules="userRules"  label-width="80px" :hide-required-asterisk="true">
      <el-form-item label="帐号" prop="userAccount">
        <el-input v-model="userForm.userAccount" disabled style="width: 380px" placeholder="帐号信息"></el-input>
      </el-form-item>
      <el-form-item label="用户昵称" prop="userName">
        <el-input v-model="userForm.userName" style="width: 380px" placeholder="请填写昵称"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="userForm.userGender" placeholder="请选择性别" style="width: 380px">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="生日" prop="userBirthday">
        <el-date-picker v-model="userForm.userBirthday" value-format="yyyy-MM-dd" style="width: 380px" :picker-options="pickerOptions" type="date" placeholder="请选择出生日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="身份证" prop="encryptId">
        <div style="width: 380px;position: relative">
          <el-input v-model="userForm.encryptId" disabled placeholder="身份证信息"></el-input>
          <el-tooltip effect="dark" content="点我填写身份证信息" placement="right">
            <el-button style="position: absolute;right: 0;top: 0;" plain icon="el-icon-edit" @click="inputUserID"></el-button>
          </el-tooltip>
        </div>
      </el-form-item>
      <el-form-item label="学习目标" prop="learnTarget">
        <el-select v-model="userForm.learnTarget" placeholder="请选择" style="width: 380px">
          <el-option label="人工智能" value="人工智能"></el-option>
          <el-option label="计算机专业基础" value="计算机专业基础"></el-option>
          <el-option label="办公软件" value="办公软件"></el-option>
          <el-option label="职场能力" value="职场能力"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="个性签名" prop="description" style="width: 82%">
        <el-input type="textarea" :autosize="{ minRows: 4, maxRows: 4}" v-model="userForm.description" style="min-width: 822px" placeholder="不个性，没有签名"></el-input>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button type="primary" @click="submitForm('userForm')">保存信息</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: "UserCenter",
    data() {
      return{
        //日期无法选择今日以前的
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now() - 8.64e6
          }
        },
        userForm:{
          userId:null,
          userAccount:null,
          userName:null,
          userGender:null,
          userBirthday:null,
          encryptId:null,
          idCard:null,
          learnTarget:null,
          description:null,
        },
        userRules: {
          userName: [
            { required: true, message: '请输入用户昵称', trigger: 'blur' },
            { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' }
          ],
          description: [
            { min: 1, max: 300, message: '长度在 1 到 300 个字符', trigger: 'blur' }
          ]
        }
      }
    },
    methods:{
      //提交信息
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$userApi.updateUserInfo(this.userForm).then(res=>{
              this.$store.commit("saveUserInfo",this.$tools.deepCope(this.userForm));
              this.$notify({title: '成功', message: res.message, type: 'success',offset:50});
            });
          } else {return false;}
        });
      },
      inputUserID(){
        this.$prompt('请输入身份证号', '完善信息', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
          inputErrorMessage: '身份证格式不正确'
        }).then(({ value }) => {
          this.userForm.idCard = value;
          this.userForm.encryptId = this.$tools.hideMiddle(value);
        }).catch(() => {});
      }
    },
    created(){
      this.userForm = this.$tools.deepCope(this.$store.state.userInfo);
      this.userForm.encryptId = this.$tools.hideMiddle(this.userForm.idCard);
    }
  }
</script>

<style>
  .user-form{
    margin: 20px;
  }

  .user-form .el-form-item{
    width: 47%;
  }

  .user-form .el-textarea__inner{
    max-height: 130px;
  }

  .user-form .el-form-item__label {
    float: none;
    display: inline-block;
    line-height: 30px;
    font-size: 18px;
    font-weight: 600;
  }
</style>
