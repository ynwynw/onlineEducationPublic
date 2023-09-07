package top.qiudb.service.auditing;

import org.apache.ibatis.annotations.Param;
import top.qiudb.pojo.Article;
import top.qiudb.pojo.ArticleAudit;

import java.util.List;

public interface ArticleAuditService {
    //分页查询文章审核信息
    List<ArticleAudit> queryPageAudit(int pageNum, int pageSize);
    //查询全部文章审核信息 统计数量
    List<ArticleAudit> totalAudit();
    //添加文章审核信息
    int addArticleAudit(ArticleAudit articleAudit);
    //设置文章审核状态
    int updateArticleAudit(int articleId,int auditState);
    //根据审核Id查询
    ArticleAudit queryAuditById(int auditId);
    //添加审核同意/拒绝 信息
    int addArticleAuditRemark(ArticleAudit articleAudit);
    //查询拒绝原因
    String queryRefuseCause(int articleId);
    //搜索文章审核信息
    List<ArticleAudit> searchArticleAudit(ArticleAudit articleAudit,int pageNum, int pageSize);
    //搜索文章审核信息 统计
    List<ArticleAudit> totalSearchArticleAudit(ArticleAudit articleAudit);
}
