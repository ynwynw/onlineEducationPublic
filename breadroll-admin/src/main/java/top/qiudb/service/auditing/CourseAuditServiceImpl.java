package top.qiudb.service.auditing;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.CourseAuditMapper;
import top.qiudb.pojo.CourseAudit;

import java.util.List;

@Service
public class CourseAuditServiceImpl implements CourseAuditService{
    @Autowired
    private CourseAuditMapper courseAuditMapper;

    @Override
    public List<CourseAudit> pageCourseAudit(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseAuditMapper.pageCourseAudit();
    }

    @Override
    public List<CourseAudit> totalCourseAudit() {
        return courseAuditMapper.totalCourseAudit();
    }

    @Override
    public int addCourseAuditInfo(CourseAudit courseAudit) {
        return courseAuditMapper.addCourseAuditInfo(courseAudit);
    }

    @Override
    public int updateCourseAudit(int courseId, int auditState) {
        return courseAuditMapper.updateCourseAudit(courseId,auditState);
    }

    @Override
    public List<CourseAudit> searchCourseAudit(CourseAudit courseAudit,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseAuditMapper.searchCourseAudit(courseAudit);
    }

    @Override
    public List<CourseAudit> totalSearchCourseAudit(CourseAudit courseAudit) {
        return courseAuditMapper.totalSearchCourseAudit(courseAudit);
    }

    @Override
    public int addCourseAuditRemark(CourseAudit courseAudit) {
        return courseAuditMapper.addCourseAuditRemark(courseAudit);
    }

    @Override
    public String queryRefuseCause(int courseId) {
        return courseAuditMapper.queryRefuseCause(courseId);
    }

    @Override
    public int queryAuditStateById(int auditId) {
        return courseAuditMapper.queryAuditStateById(auditId);
    }


}
