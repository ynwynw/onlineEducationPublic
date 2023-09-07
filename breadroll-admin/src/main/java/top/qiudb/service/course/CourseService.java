package top.qiudb.service.course;

import org.apache.ibatis.annotations.Param;
import top.qiudb.controller.course.CourseController;
import top.qiudb.pojo.*;

import java.util.List;

public interface CourseService {
    //分页查询线上课程
    List<CourseTeacherConn> queryPageOnlineCourse(int pageNum, int pageSize);
    //查询所有线上课程
    List<Course> queryAllOnlineCourse();
    //添加线上课程
    int addOnlineCourse(Course course);
    //查询所有线上且可用的课程
    List<Course> queryAllAvalibCourse();
    //修改线上课程信息
    int updateOnlineCourse(Course course);
    //删除课程
    int delCourse(int courseId);
    //根据条件查询线上课程
    List<CourseTeacherConn> selOnlineCourse(CourseTeacherConn courseTeacherConn,int pageNum, int pageSize);
    //根据条件查询线上课程 总数
    List<CourseTeacherConn> totalselOnlineCourse(CourseTeacherConn courseTeacherConn);
    //分页查询特训班课程
    List<CourseTeacherConn> queryPageSpecialClass(int pageNum, int pageSize);
    //添加特训班课程
    int addSpecialClass(Course course);
    //修改特训班课程
    int updateSpecialClass(Course course);
    //根据课程id查询
    Course queryByCourseId(int courseId);
    //查询所有特训班  统计数量
    List<Course> queryAllSpecialClass();
    //根据条件搜索特训班课程
    List<CourseTeacherConn> selSpecialCourse(CourseTeacherConn courseTeacherConn,int pageNum, int pageSize);
    //根据条件搜索特训班课程 总数
    List<CourseTeacherConn> totalSelSpecialCourse(CourseTeacherConn courseTeacherConn);
    //改变课程的发布状态
    int updatePublishState(int courseId,String publisher,int publisherId);
    //改变课程的审核状态
    int updateOFFAuditState(int courseId,int auditState);
    //查询所有已发布且通过审核的课程
    List<Course> queryPublishedAuditedCourse();
    //根据课程Id得到课程的审核状态
    int queryAuditStateById(int courseId);
    //根据播放量获得前五名
    List<Course> queryTopFiveCourse();
    //查询不同讲师所讲授的课程
    List<CountTeacherCourse> queryTeacherCourse();
    //查询各专业类型的课程数量
    List<CountTypeCourse> queryTypeCourse();
}
