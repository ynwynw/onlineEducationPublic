package top.qiudb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.qiudb.pojo.Article;

import java.util.List;

@Mapper
@Component
public interface ArticleMapper {
    //分页查询所有未删除的文章
    List<Article> queryPageArticle();
    //查询所有未删除的文章  统计数量
    List<Article> queryAllArticle();
    //搜索文章
    List<Article> searchArticle(Article article);
    //搜索文章 统计
    List<Article> totalSearchArticle(Article article);
    //根据文章Id删除
    int deleteArticle(int articleId);
    //根据文章Id查询
    Article queryArticleById(int articleId);
    //添加文章
    int addArticle(Article article);
    //编辑文章
    int updateArticle(Article article);
    //修改发布状态
    int updatePublishState(@Param("articleId") int articleId,@Param("publisher")String publisher,@Param("publisherId")int publisherId);
    //改变文章的审核状态
    int updateAuditState(@Param("articleId")int articleId, @Param("auditState")int auditState);
    //查询所有发布且审核通过的文章
    List<Article> queryAllPublishAuditArticle();
}
