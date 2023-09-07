package top.qiudb.service.auditing;

import org.apache.ibatis.annotations.Param;
import top.qiudb.mapper.ResourceAuditMapper;
import top.qiudb.pojo.ResourceAudit;

import java.util.List;

public interface ResourceAuditService {
    //分页查询所有资料审核信息
    List<ResourceAudit> pageResourceAudit(int pageNum, int pageSize);
    //查询所有资料审核信息  统计数量
    List<ResourceAudit> totalResourceAudit();
    //添加资料审核信息
    int addResourceAudit(ResourceAudit resourceAudit);
    //设置资料审核状态
    int updateResourceAudit(int resourceId,int auditState);
    //搜索资料审核信息
    List<ResourceAudit> searchAudit(ResourceAudit resourceAudit,int pageNum, int pageSize);
    //搜索资料审核信息  统计
    List<ResourceAudit> totalSearchAudit(ResourceAudit resourceAudit);
    //添加资料审核备注信息
    int addResourceAuditRemark(ResourceAudit resourceAudit);
    //查询拒绝原因
    String queryRefuseCause(int resourceId);
}
