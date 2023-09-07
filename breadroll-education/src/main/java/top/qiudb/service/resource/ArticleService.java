package top.qiudb.service.resource;

import top.qiudb.pojo.resource.Article;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/18 19:58
 * @description 文章表
 */
public interface ArticleService {
    //查询所有文章
    public List<Article> queryAllArticle(int pageNum, int pageSize);

    //查询文章总数
    public Integer queryCount();

    //通过id查询文章
    public Article queryArticleById(Integer articleId);

    //文章播放量自增
    public Boolean readingCountGrowth(Integer articleId);

    //根据文章类别获取文章
    public List<Article> queryArticleByType(String typeName,int pageNum, int pageSize);

    //查询文章总数通过类型
    public Integer queryCountByType(String typeName);
}
