package top.qiudb.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.course.Course;
import top.qiudb.pojo.course.CourseType;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/9 9:48
 * @description 课程
 */
@Repository
@Mapper
public interface CourseMapper {
    //查询所有课程
    public List<Course> selectAllCourse();

    //查询课程总数
    public Integer queryCount();

    //根据播放量查询
    public List<Course> selectCourseByPlay();

    //根据发布时间查询
    public List<Course> selectCourseByTime();

    //查询免费课程
    public List<Course> selectCourseByFree();

    //查询免费课程总数
    public Integer queryCountByFree();

    //根据类别查询
    public List<Course> selectCourseByType(Integer typeId);

    //根据类别查询课程总数
    public Integer queryCountByType(Integer typeId);

    //根据课程名模糊查询
    public List<Course> selectCourseByName(String courseName);

    //根据课程名查询课程总数
    public Integer queryCountByName(String courseName);

    //根据课程id查询课程
    public Course selectCourseById(Integer courseId);

    //根据课程id查询课程
    public List<Course> selectCourseByIds(List<Integer> courseIds);

    //查询所有特训班
    public List<Course> selectAllSpecialCourse();

    //查询特训班总数
    public Integer selectCountSpecialCourse();

    //修改课程信息（播放量自增）
    public Boolean updateCourse(Course course);

    //根据课程id查询特训班
    public Course selectSpecialCourseById(Integer courseId);
}
