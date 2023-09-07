<template>
  <div class="no-course">
    <el-image class="person" :src="imageSrc1"></el-image>
    <el-image class="background" :src="imageSrc2"></el-image>
    <div class="operator">
      <h2>课程处于下架或待审核状态，先观看下其它课程吧</h2>
      <el-button type="primary" round @click="ret">浏览更多课程</el-button>
    </div>
  </div>
</template>

<script>
  export default {
    name: "noCourse",
    data() {
      return{
        imageSrc1:require("../../assets/error/sucainiu-error-1.png"),
        imageSrc2:require("../../assets/error/sucainiu-error-2.jpg"),
      }
    },
    methods:{
      ret(){
        this.$router.push('/');
      },
    },
  }
</script>

<style scoped>
.no-course{
  position: relative;
  width: 100%;
  height: 70vh;
}

.background{
  position: absolute;
  top: 40%;
  left: 0%;
  width: 100%;
}

.background img{
  min-height: 158px;
}

.person{
  position: absolute;
  top: 20%;
  left: 45%;
  z-index: 999;
}

.operator{
  position: absolute;
  top: 42%;
  left: 15%;
  z-index: 999;
}

.operator h2{
  font-size: 21px;
  font-weight: 400;
  color: #333333;
  text-align: center;
  letter-spacing: 2px;
  margin-bottom: 20px;
}

.operator .el-button{
  width: 180px;
  font-size: 15px;
}

</style>
