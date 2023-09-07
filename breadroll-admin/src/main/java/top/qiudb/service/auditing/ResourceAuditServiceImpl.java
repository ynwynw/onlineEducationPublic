package top.qiudb.service.auditing;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.ResourceAuditMapper;
import top.qiudb.pojo.ResourceAudit;
import top.qiudb.service.auditing.ResourceAuditService;

import java.util.List;

@Service
public class ResourceAuditServiceImpl implements ResourceAuditService {
    @Autowired
    private ResourceAuditMapper resourceAuditMapper;

    @Override
    public List<ResourceAudit> pageResourceAudit(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return resourceAuditMapper.pageResourceAudit();
    }

    @Override
    public List<ResourceAudit> totalResourceAudit() {
        return resourceAuditMapper.totalResourceAudit();
    }

    @Override
    public int addResourceAudit(ResourceAudit resourceAudit) {
        return resourceAuditMapper.addResourceAudit(resourceAudit);
    }

    @Override
    public int updateResourceAudit(int resourceId, int auditState) {
        return resourceAuditMapper.updateResourceAudit(resourceId,auditState);
    }

    @Override
    public List<ResourceAudit> searchAudit(ResourceAudit resourceAudit,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return resourceAuditMapper.searchAudit(resourceAudit);
    }

    @Override
    public List<ResourceAudit> totalSearchAudit(ResourceAudit resourceAudit) {
        return resourceAuditMapper.totalSearchAudit(resourceAudit);
    }

    @Override
    public int addResourceAuditRemark(ResourceAudit resourceAudit) {
        return resourceAuditMapper.addResourceAuditRemark(resourceAudit);
    }

    @Override
    public String queryRefuseCause(int resourceId) {
        return resourceAuditMapper.queryRefuseCause(resourceId);
    }

}
