package top.qiudb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.qiudb.pojo.ResourceAudit;

import java.util.List;

@Mapper
@Component
public interface ResourceAuditMapper {
    //分页查询所有资料审核信息
    List<ResourceAudit> pageResourceAudit();
    //查询所有资料审核信息  统计数量
    List<ResourceAudit> totalResourceAudit();
    //添加资料审核信息
    int addResourceAudit(ResourceAudit resourceAudit);
    //设置资料审核状态
    int updateResourceAudit(@Param("resourceId")int resourceId, @Param("auditState")int auditState);
    //搜索资料审核信息
    List<ResourceAudit> searchAudit(ResourceAudit resourceAudit);
    //搜索资料审核信息  统计
    List<ResourceAudit> totalSearchAudit(ResourceAudit resourceAudit);
    //添加资料审核备注信息
    int addResourceAuditRemark(ResourceAudit resourceAudit);
    //查询拒绝原因
    String queryRefuseCause(int resourceId);
}
