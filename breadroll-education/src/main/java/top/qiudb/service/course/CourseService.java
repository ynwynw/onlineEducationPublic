package top.qiudb.service.course;

import top.qiudb.pojo.course.Course;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/9 9:58
 * @description 课程
 */
public interface CourseService {
    //查询所有课程
    public List<Course> queryAllCourse(int pageNum, int pageSize);

    //查询课程总数
    public Integer queryCount();

    //根据播放量查询
    public List<Course> queryCourseByPlay(int pageNum, int pageSize);

    //根据发布时间查询
    public List<Course> queryCourseByTime(int pageNum, int pageSize);

    //查询免费课程
    public List<Course> queryCourseByFree(int pageNum, int pageSize);

    //查询免费课程总数
    public Integer queryCountByFree();

    //根据类别查询
    public List<Course> queryCourseByType(Integer typeId, int pageNum, int pageSize);

    //根据类别查询课程总数
    public Integer queryCountByType(Integer typeId);

    //根据课程id查询课程
    public Course queryCourseById(Integer courseId);

    //根据课程名模糊查询
    public List<Course> queryCourseByName(String courseName, int pageNum, int pageSize);

    //根据课程名查询课程总数
    public Integer queryCountByName(String courseName);

    //查询所有特训班
    public List<Course> queryAllSpecialCourse(int pageNum, int pageSize);

    //查询特训班总数
    public Integer queryCountSpecialCourse();

    //根据课程id查询特训班
    public Course querySpecialCourseById(Integer courseId);

    //修改课程信息（播放量自增）
    public Boolean updateCourse(Course course);

    //根据课程id查询课程
    public List<Course> queryCourseByIds(List<Integer> courseIds);
}
