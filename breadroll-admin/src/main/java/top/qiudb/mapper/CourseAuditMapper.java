package top.qiudb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.qiudb.pojo.CourseAudit;

import java.util.List;

@Mapper
@Component
public interface CourseAuditMapper {
    //分页查询课程审核信息
    List<CourseAudit> pageCourseAudit();
    //查询所有课程审核信息  统计数量
    List<CourseAudit> totalCourseAudit();
    //添加线上课程审核信息
    int addCourseAuditInfo(CourseAudit courseAudit);
    //设置课程审核状态
    int updateCourseAudit(@Param("courseId")int courseId,@Param("auditState")int auditState);
    //搜索课程审核信息
    List<CourseAudit> searchCourseAudit(CourseAudit courseAudit);
    //搜索课程审核信息 统计
    List<CourseAudit> totalSearchCourseAudit(CourseAudit courseAudit);
    //添加课程审核备注
    int addCourseAuditRemark(CourseAudit courseAudit);
    //查询拒绝原因
    String queryRefuseCause(int courseId);
    //根据审核Id获取审核状态
    int queryAuditStateById(int auditId);
}
