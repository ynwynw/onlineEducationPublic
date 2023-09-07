package top.qiudb.service.auditing;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.SpecialAuditMapper;
import top.qiudb.pojo.SpecialAudit;

import java.util.List;

@Service
public class SpecialAuditServiceImpl implements SpecialAuditService{
    @Autowired
    private SpecialAuditMapper specialAuditMapper;

    @Override
    public List<SpecialAudit> queryPageAudit(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return specialAuditMapper.queryPageAudit();
    }

    @Override
    public List<SpecialAudit> queryTotalAudit() {
        return specialAuditMapper.queryTotalAudit();
    }

    @Override
    public int addSpecialAuditInfo(SpecialAudit specialAudit) {
        return specialAuditMapper.addSpecialAuditInfo(specialAudit);
    }

    @Override
    public int updateCourseAudit(int courseId, int auditState) {
        return specialAuditMapper.updateCourseAudit(courseId,auditState);
    }

    @Override
    public List<SpecialAudit> searchCourseAudit(SpecialAudit specialAudit,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return specialAuditMapper.searchCourseAudit(specialAudit);
    }

    @Override
    public List<SpecialAudit> totalSearchCourseAudit(SpecialAudit specialAudit) {
        return specialAuditMapper.totalSearchCourseAudit(specialAudit);
    }

    @Override
    public int addSpecialAuditRemark(SpecialAudit specialAudit) {
        return specialAuditMapper.addSpecialAuditRemark(specialAudit);
    }

    @Override
    public String queryRefuseCause(int courseId) {
        return specialAuditMapper.queryRefuseCause(courseId);
    }

    @Override
    public SpecialAudit querySpecialAuditByID(int auditId) {
        return specialAuditMapper.querySpecialAuditByID(auditId);
    }

}
