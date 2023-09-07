package top.qiudb.service.course;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.ArticleMapper;
import top.qiudb.pojo.Article;

import java.util.List;
@Service
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> queryPageArticle(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return articleMapper.queryPageArticle();
    }

    @Override
    public List<Article> queryAllArticle() {
        return articleMapper.queryAllArticle();
    }

    @Override
    public List<Article> searchArticle(Article article, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return articleMapper.searchArticle(article);
    }

    @Override
    public List<Article> totalSearchArticle(Article article) {
        return articleMapper.totalSearchArticle(article);
    }

    @Override
    public int deleteArticle(int articleId) {
        return articleMapper.deleteArticle(articleId);
    }

    @Override
    public Article queryArticleById(int articleId) {
        return articleMapper.queryArticleById(articleId);
    }

    @Override
    public int addArticle(Article article) {
        return articleMapper.addArticle(article);
    }

    @Override
    public int updateArticle(Article article) {
        return articleMapper.updateArticle(article);
    }

    @Override
    public int updatePublishState(int articleId,String publisher,int publisherId) {
        return articleMapper.updatePublishState(articleId,publisher,publisherId);
    }

    @Override
    public int updateAuditState(int articleId, int auditState) {
        return articleMapper.updateAuditState(articleId,auditState);
    }

    @Override
    public List<Article> queryAllPublishAuditArticle() {
        return articleMapper.queryAllPublishAuditArticle();
    }
}
