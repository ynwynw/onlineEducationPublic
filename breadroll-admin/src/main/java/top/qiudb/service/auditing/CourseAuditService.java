package top.qiudb.service.auditing;

import org.apache.ibatis.annotations.Param;
import top.qiudb.pojo.CourseAudit;

import java.util.List;

public interface CourseAuditService {
    //分页查询课程审核信息
    List<CourseAudit> pageCourseAudit(int pageNum, int pageSize);
    //查询所有课程审核信息  统计数量
    List<CourseAudit> totalCourseAudit();
    //添加课程审核信息
    int addCourseAuditInfo(CourseAudit courseAudit);
    //设置课程审核状态
    int updateCourseAudit(int courseId,int auditState);
    //搜索课程审核信息
    List<CourseAudit> searchCourseAudit(CourseAudit courseAudit,int pageNum, int pageSize);
    //搜索课程审核信息 统计
    List<CourseAudit> totalSearchCourseAudit(CourseAudit courseAudit);
    //添加课程审核备注
    int addCourseAuditRemark(CourseAudit courseAudit);
    //查询拒绝原因
    String queryRefuseCause(int courseId);
    //根据审核Id获取审核状态
    int queryAuditStateById(int auditId);
}
