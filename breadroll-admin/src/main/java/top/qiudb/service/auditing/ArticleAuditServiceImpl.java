package top.qiudb.service.auditing;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.ArticleAuditMapper;
import top.qiudb.pojo.ArticleAudit;
import top.qiudb.service.auditing.ArticleAuditService;

import java.util.List;

@Service
public class ArticleAuditServiceImpl implements ArticleAuditService {
    @Autowired
    private ArticleAuditMapper articleAuditMapper;
    @Override
    public List<ArticleAudit> queryPageAudit(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return articleAuditMapper.queryPageAudit();
    }

    @Override
    public List<ArticleAudit> totalAudit() {
        return articleAuditMapper.totalAudit();
    }
    @Override
    public int addArticleAudit(ArticleAudit articleAudit) {
        return articleAuditMapper.addArticleAudit(articleAudit);
    }

    @Override
    public int updateArticleAudit(int articleId, int auditState) {
        return articleAuditMapper.updateArticleAudit(articleId,auditState);
    }

    @Override
    public ArticleAudit queryAuditById(int auditId) {
        return articleAuditMapper.queryAuditById(auditId);
    }

    @Override
    public int addArticleAuditRemark(ArticleAudit articleAudit) {
        return articleAuditMapper.addArticleAuditRemark(articleAudit);
    }

    @Override
    public String queryRefuseCause(int articleId) {
        return articleAuditMapper.queryRefuseCause(articleId);
    }

    @Override
    public List<ArticleAudit> searchArticleAudit(ArticleAudit articleAudit,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return articleAuditMapper.searchArticleAudit(articleAudit);
    }

    @Override
    public List<ArticleAudit> totalSearchArticleAudit(ArticleAudit articleAudit) {
        return articleAuditMapper.totalSearchArticleAudit(articleAudit);
    }

}
