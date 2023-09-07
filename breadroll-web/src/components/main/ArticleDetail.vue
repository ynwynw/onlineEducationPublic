<template>
  <div class="article-detail">
    <div class="article-container" v-if="articleData!==null">
      <div class="article-box">
        <h2 class="title">{{ articleData.articleTitle}}</h2>
        <div class="article-info-box">
          <el-image v-if="articleData.reprintUrl.length!==0" :src="reprintImg"></el-image>
          <el-image v-else :src="originalImg"></el-image>
          <div class="article-info-data">
            <span class="left">分类专栏：<el-tag effect="plain" size="small">{{articleData.typeName}}</el-tag></span>
            <span class="left">阅读量 {{articleData.readingCount}}</span>
          </div>
          <span class="right">{{articleData.publishTime}}</span>
        </div>
        <div class="typo article-content animated fadeIn" v-html="articleData.text"></div>
        <div class="auth-info">
          <p v-if="articleData.reprintUrl.length!==0">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#iconyuanwen"></use>
            </svg>
            <span>原文链接：<a :href="articleData.reprintUrl" target="_blank" class="link">{{articleData.reprintUrl}}</a></span>
          </p>
          <p>
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#iconlianjie"></use>
            </svg>
            <span>本文链接：<a :href="url" target="_blank" class="link">{{url}}</a></span>
          </p>
          <p>
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#iconfenxiang"></use>
            </svg>
            <span>版权声明：</span><span class="text">
                《
                <a class="link" href="//creativecommons.org/licenses/by-nc-sa/4.0/deed.zh" target="_blank" rel="noopener noreferrer nofollow">署名-非商业性使用-相同方式共享 4.0 国际 (CC BY-NC-SA 4.0)</a>
                》许可协议授权
            </span>
          </p>
        </div>
      </div>
      <div class="right-side">
        <div class="directory" v-if="articleData.directory">
          <p class="dir-title">文章目录</p>
          <div class="dire"></div>
        </div>
        <div v-if="articleList!=null && articleList.length!==0" class="recommend">
          <p class="dir-title">文章推荐</p>
          <ul>
            <li v-for="(item,index) in articleList" :key="index" @click="watch(item.articleId)">
              <el-link type="primary">{{item.articleTitle}}</el-link>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import $ from 'jquery'
  export default {
    name: "ArticleDetail",
    data() {
      return{
        originalImg:require("../../assets/global/original.png"),
        reprintImg:require("../../assets/global/reprint.png"),
        articleId:'',
        articleData:null,
        url:'',
        articleList:[],
      }
    },
    methods:{
      reqInfo(id){
        this.$courseApi.queryArticleById(id).then(res=>{
          this.articleData=res.data;
          this.$nextTick(()=>{
            $("pre").addClass("line-numbers");
            tocbot.init({
              tocSelector: '.dire',  //添加到哪里
              contentSelector: '.article-content', //针对那个id标签
              headingSelector: 'h1,h2',  //需要解析的标题
            });
            Prism.highlightAll();
          })
          this.$courseApi.queryArticleByType(this.articleData.typeName).then(res=>{
            this.articleList = res.data;
          });
        })
      },
      watch(id){
        this.$router.push({ path: '/articleDetail', query: {id:id}});
        this.reqInfo(id);
        this.url = window.location.href;
      },
    },
    created() {
      this.url = window.location.href;
      this.articleId = this.$route.query.id;
      this.reqInfo(this.articleId);
    }
  }
</script>

<style scoped>
  .article-detail{
    min-height: 83vh;
    padding: 20px 0;
    background: #F5F5F5;
  }

  .article-container{
    display: grid;
    grid-template-columns: 1fr 212px;
    grid-column-gap: 15px;
    min-height: 60vh;
    width: 65%;
    min-width: 1240px;
    margin: 0 auto;
    height: 100%;
  }

  .article-box{
    background: #FFFFFF;
    border-radius: 8PX;
    padding: 20px 40px 40px;
    box-shadow: 0 0 20px -5px rgba(158, 158, 158, 0.22);
  }

  .directory{
    width: 225px;
    padding: 10px;
    height: fit-content;
    background: #FFFFFF;
    border-radius: 5PX;
    box-shadow: 0 0 20px -5px rgba(158, 158, 158, 0.22);
    margin-bottom: 20px;
  }

  .dir-title{
    color: #303133;
    margin: 0;
    font-size: 16px;
    font-weight: 500;
    height: 36px;
    line-height: 30px;
    border-bottom: 1px solid #ebeef5;
  }

  .recommend{
    width: 225px;
    height: fit-content;
    background: #FFFFFF;
    border-radius: 5PX;
    padding: 10px 10px 0;
    box-shadow: 0 0 20px -5px rgba(158, 158, 158, 0.22);
    margin-bottom: 20px;
  }

  .right-side{
    height: fit-content;
    width: fit-content;
  }

  .recommend > ul{
    list-style: none;
    padding: 10px;
    margin: 10px 0;
    text-align: left;
  }

  .recommend li{
    font-size: 14px;
    white-space: nowrap;
    height: 31px;
    line-height: 20px;
    text-align: left;
    overflow: hidden;
    text-overflow: ellipsis;
    font-size: 14px;
    white-space: nowrap;
    width: 185px;
  }

  .title{
    font-size: 30px;
    color: #303133;
    text-shadow: 0 1px 2px rgba(0, 0, 0, 0.25);
    text-align: left;
    margin-bottom: 15px;
    word-break: break-all;
  }

  .article-info-box{
    position: relative;
    background: #F8F8F8;
    width: 100%;
    height: 62px;
    border-radius: 4px;
    color: #999aaa!important;
    font-weight: normal;
  }

  .auth-info{
    margin-top: 20px;
    background: #F8F8F8;
    text-align: left;
    padding: 10px 20px 1px;
  }

  .auth-info .icon{
    width: 20px;
    height: 20px;
    vertical-align: middle;
    margin-right: 5px;
  }

  .auth-info p{
    font-size: 14px;
    margin-bottom: 10px;
    word-break: break-all;
    line-height: 22px;
  }

  .auth-info p .link{
    color: #000000;
  }

  .auth-info p .link:hover{
    color: #409eff;
  }

  .article-info-box .el-image{
    position: absolute;
    top: 10px;
    left: 10px;
    width: 36px;
    height: 32px;
  }

  .article-info-data {
    position: absolute;
    bottom: 18px;
    left: 66px;
  }

  .article-info-data .left{
    margin-right: 20px;
  }

  .right{
    position: absolute;
    right: 20px;
    bottom: 18px;
  }

  .article-content{
    text-align: left;
    min-height: 40vh;
    margin: 20px 0 25px;
  }
</style>

<style>
  .article-detail p{
    color: #404040;
    font-weight: 400;
    line-height: 1.8;
  }

  .article-detail blockquote{
    margin-bottom: 1.2em;
  }

 .article-detail blockquote>p{
   margin: 0;
   color: auto;
 }

 .article-detail p>img{
   display: block;
   margin: 10px auto;
 }
</style>

