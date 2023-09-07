package top.qiudb.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.banner.Banner;
import top.qiudb.pojo.resource.Article;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/18 19:48
 * @description 文章
 */
@Repository
@Mapper
public interface ArticleMapper {
    //查询所有文章
    public List<Article> selectAllArticle();

    //查询文章总数
    public Integer queryCount();

    //通过id查询文章
    public Article selectArticleById(Integer articleId);

    //文章播放量自增
    public Boolean readingCountGrowth(Integer articleId);

    //根据文章类别获取文章
    public List<Article> selectArticleByType(String typeName);

    //查询文章总数通过类型
    public Integer queryCountByType(String typeName);

}
