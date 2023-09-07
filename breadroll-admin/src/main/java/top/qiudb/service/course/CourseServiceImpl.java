package top.qiudb.service.course;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.CourseMapper;
import top.qiudb.pojo.CountTeacherCourse;
import top.qiudb.pojo.CountTypeCourse;
import top.qiudb.pojo.Course;
import top.qiudb.pojo.CourseTeacherConn;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public List<CourseTeacherConn> queryPageOnlineCourse(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseMapper.queryPageOnlineCourse();
    }

    @Override
    public List<Course> queryAllOnlineCourse() {
        return courseMapper.queryAllOnlineCourse();
    }

    @Override
    public int addOnlineCourse(Course course) {
        return courseMapper.addOnlineCourse(course);
    }

    @Override
    public List<Course> queryAllAvalibCourse() {
        return courseMapper.queryAllAvalibCourse();
    }

    @Override
    public int updateOnlineCourse(Course course) {
        return courseMapper.updateOnlineCourse(course);
    }

    @Override
    public int delCourse(int courseId) {
        return courseMapper.delCourse(courseId);
    }

    @Override
    public List<CourseTeacherConn> selOnlineCourse(CourseTeacherConn courseTeacherConn, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseMapper.selOnlineCourse(courseTeacherConn);
    }

    @Override
    public List<CourseTeacherConn> totalselOnlineCourse(CourseTeacherConn courseTeacherConn) {
        return courseMapper.totalselOnlineCourse(courseTeacherConn);
    }

    @Override
    public List<CourseTeacherConn> queryPageSpecialClass(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseMapper.queryPageSpecialClass();
    }

    @Override
    public int addSpecialClass(Course course) {
        return courseMapper.addSpecialClass(course);
    }

    @Override
    public int updateSpecialClass(Course course) {
        return courseMapper.updateSpecialClass(course);
    }

    @Override
    public Course queryByCourseId(int courseId) {
        return courseMapper.queryByCourseId(courseId);
    }

    @Override
    public List<Course> queryAllSpecialClass() {
        return courseMapper.queryAllSpecialClass();
    }

    @Override
    public List<CourseTeacherConn> selSpecialCourse(CourseTeacherConn courseTeacherConn,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseMapper.selSpecialCourse(courseTeacherConn);
    }

    @Override
    public List<CourseTeacherConn> totalSelSpecialCourse(CourseTeacherConn courseTeacherConn) {
        return courseMapper.totalSelSpecialCourse(courseTeacherConn);
    }

    @Override
    public int updatePublishState(int courseId,String publisher,int publisherId) {
        return courseMapper.updatePublishState(courseId,publisher,publisherId);
    }

    @Override
    public int updateOFFAuditState(int courseId,int auditState) {
        return courseMapper.updateOFFAuditState(courseId,auditState);
    }

    @Override
    public List<Course> queryPublishedAuditedCourse() {
        return courseMapper.queryPublishedAuditedCourse();
    }

    @Override
    public int queryAuditStateById(int courseId) {
        return courseMapper.queryAuditStateById(courseId);
    }

    @Override
    public List<Course> queryTopFiveCourse() {
        return courseMapper.queryTopFiveCourse();
    }

    @Override
    public List<CountTeacherCourse> queryTeacherCourse() {
        return courseMapper.queryTeacherCourse();
    }

    @Override
    public List<CountTypeCourse> queryTypeCourse() {
        return courseMapper.queryTypeCourse();
    }

}
