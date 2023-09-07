package top.qiudb.service.resource;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.ArticleMapper;
import top.qiudb.pojo.resource.Article;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/18 19:58
 * @description 描述
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;


    @Override
    public List<Article> queryAllArticle(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return articleMapper.selectAllArticle();
    }

    @Override
    public Integer queryCount() {
        return articleMapper.queryCount();
    }

    @Override
    public Article queryArticleById(Integer articleId) {
        return articleMapper.selectArticleById(articleId);
    }

    @Override
    public Boolean readingCountGrowth(Integer articleId) {
        return articleMapper.readingCountGrowth(articleId);
    }

    @Override
    public List<Article> queryArticleByType(String typeName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return articleMapper.selectArticleByType(typeName);
    }

    @Override
    public Integer queryCountByType(String typeName) {
        return articleMapper.queryCountByType(typeName);
    }
}
