<template>
  <div class="article-list">
    <h2 class="header">学习锦囊</h2>
    <ul class="article-box">
      <li v-for="(article,index) in articleList" :key="index" @click="articleView(article.articleId)">
        <el-image :src="article.coverUrl"></el-image>
        <p class="title">{{article.articleTitle}}</p>
        <p class="info">
          <span class="publish-time">{{article.publishTime}}</span>
          <span class="read-count">{{article.readingCount}}阅读</span>
        </p>
      </li>
    </ul>
    <el-pagination
      style="margin: 15px auto 35px;"
      background
      :hide-on-single-page="true"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryData.pageNum"
      :page-size="queryData.pageSize"
      layout=" prev, pager, next"
      :total="queryData.total">
    </el-pagination>
  </div>
</template>

<script>
  export default {
    name: "ArticleList",
    data() {
        return{
          articleList:[],
          queryData:{
            pageNum:1,
            pageSize:15,
            total:0,
          },
        }
    },
    methods:{
      reqInfo(){
        this.$courseApi.queryAllArticle(this.queryData).then(res=>{
          this.articleList = res.data.list;
          this.queryData.total =res.data.total;
        });
      },
      //到文章详情页
      articleView(articleId){
        this.$router.push({ path: '/articleDetail', query: {id:articleId}});
      },
      //每页大小
      handleSizeChange(val) {
        this.queryData.pageSize=val;
        this.reqInfo();
      },
      //修改当前页
      handleCurrentChange(val) {
        this.queryData.pageNum=val;
        this.reqInfo();
      },
    },
    created(){
      this.reqInfo();
    }
  }
</script>

<style scoped>
  .article-list{
    width: 70%;
    margin: 0 auto;
    min-height: 600px;
    min-width: 1250px;
  }
  .header{
    margin-top: 40px;
    margin-bottom: 14px;
    color: #333333;
    text-align: left;
    font-size: 22px;
    font-weight: normal;
  }

  .article-box{
    display: grid;
    grid-template-columns: repeat(5, 1fr);
    grid-row-gap: 35px;
    grid-column-gap: 15px;
    padding: 0;
    margin: 0 0 38px;
    list-style: none;
  }

  .article-box li{
    overflow: hidden;
    border-radius: 10px;
    box-shadow: 0 5px 12px 0 rgba(0,16,34,0.03);
    border: solid 1px #f3f3f3;
    background-color: #ffffff;
    transition: all 0.2s ease;
    cursor: pointer;
  }

  .article-box li:hover{
    box-shadow: 0 6px 10px 0 rgba(75,91,138,0.14);
    transform: translate3d(0, -2px, 0);
  }

  .article-box .el-image{
    width: 100%;
    height: 181px;
  }

  .article-box .title{
    color: #333;
    margin: 0;
    padding: 6px 12px;
    line-height: 20px;
    font-size: 14px;
    font-weight: 500;
    height: 35px;
    overflow: hidden;
    text-align: justify;
    text-overflow: ellipsis;
    word-wrap: break-word;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
  }

  .article-box .info{
    height: 20px;
    margin: 9px 10px 8px;
    font-size: 0.75rem;
    line-height: 1rem;
    color: #999999;
    text-align: left;
    position: relative;
  }

  .article-box .publish-time{
    position: absolute;
    left: 5px;
  }

  .article-box .read-count{
    position: absolute;
    right: 5px;
  }
</style>
