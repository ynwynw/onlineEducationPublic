<template>
  <div class="not-found">
    <div class="box">
      <el-image class="image"
        :src="imageSrc">
      </el-image>
      <div class="text">
        <h2>糟糕，您访问的页面出错了</h2>
        <p>您可能输错了网址，或该网页已删除或不存在</p>
        <el-button type="primary" @click="ret">{{count}}s 返回主页</el-button>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "404",
    data() {
      return{
        imageSrc:require("../../assets/error/404.png"),
        count:5,
        timer:'',
      }
    },
    methods:{
      ret(){
        this.$router.push('/');
      },
      countRet(){
        const self = this;
        this.timer =  setInterval(() =>{
          self.count--
          if(self.count === 0){
            clearInterval(self.timer)
            self.count = 5
            self.ret();
          }
        },1000)
      },
    },
    created(){
      this.countRet();
    },
    beforeDestroy() {
     clearInterval(this.timer)
    }
  }
</script>

<style scoped>
.not-found{
  height: 70vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.text h2{
  margin: 0;
  font-size: 32px;
  color: #333333;
  text-align: center;
  letter-spacing: 5px;
  padding-top: 33px;
  padding-bottom: 25px;
}

.text p{
  margin: 0;
  font-size: 14px;
  color: #666666;
  letter-spacing: 1px;
  text-align: center;
  padding-bottom: 35px;
}

.text .el-button{
  width: 220px;
  height: 56px;
  font-size: 18px;
}

</style>
