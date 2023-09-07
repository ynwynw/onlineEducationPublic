package top.qiudb.service.auditing;

import org.apache.ibatis.annotations.Param;
import top.qiudb.pojo.SpecialAudit;

import java.util.List;

public interface SpecialAuditService {
    //分页查询特训班审核信息
    List<SpecialAudit> queryPageAudit(int pageNum, int pageSize);
    //查询所有特训班审核信息  统计数量
    List<SpecialAudit> queryTotalAudit();
    //添加特训班课程审核信息
    int addSpecialAuditInfo(SpecialAudit specialAudit);
    //设置课程审核状态
    int updateCourseAudit(int courseId,int auditState);
    //根据课程名称搜索
    List<SpecialAudit> searchCourseAudit(SpecialAudit specialAudit,int pageNum, int pageSize);
    //根据课程名称搜索  统计
    List<SpecialAudit> totalSearchCourseAudit(SpecialAudit specialAudit);
    //添加特训班审核信息
    int addSpecialAuditRemark(SpecialAudit specialAudit);
    //查询拒绝原因
    String queryRefuseCause(int courseId);
    //通过审核Id查询
    SpecialAudit querySpecialAuditByID(int auditId);
}
