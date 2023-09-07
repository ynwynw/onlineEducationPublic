package top.qiudb.service.course;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.CourseMapper;
import top.qiudb.pojo.course.Course;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/9 9:59
 * @description 描述
 */
@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    CourseMapper courseMapper;

    @Override
    public List<Course> queryAllCourse(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseMapper.selectAllCourse();
    }

    @Override
    public List<Course> queryCourseByPlay(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseMapper.selectCourseByPlay();
    }

    @Override
    public List<Course> queryCourseByTime(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseMapper.selectCourseByTime();
    }

    @Override
    public List<Course> queryCourseByFree(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseMapper.selectCourseByFree();
    }

    @Override
    public Integer queryCount() {
        return courseMapper.queryCount();
    }

    @Override
    public Integer queryCountByFree() {
        return courseMapper.queryCountByFree();
    }

    @Override
    public Integer queryCountByType(Integer typeId) {
        return courseMapper.queryCountByType(typeId);
    }

    @Override
    public List<Course> queryCourseByType(Integer typeId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseMapper.selectCourseByType(typeId);
    }

    @Override
    public Course queryCourseById(Integer courseId) {
        return courseMapper.selectCourseById(courseId);
    }

    @Override
    public List<Course> queryCourseByName(String courseName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseMapper.selectCourseByName(courseName);
    }

    @Override
    public Integer queryCountByName(String courseName) {
        return courseMapper.queryCountByName(courseName);
    }

    @Override
    public List<Course> queryAllSpecialCourse( int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseMapper.selectAllSpecialCourse();
    }

    @Override
    public Integer queryCountSpecialCourse() {
        return courseMapper.selectCountSpecialCourse();
    }

    @Override
    public Course querySpecialCourseById(Integer courseId) {
        return courseMapper.selectSpecialCourseById(courseId);
    }

    @Override
    public Boolean updateCourse(Course course) {
        return courseMapper.updateCourse(course);
    }

    @Override
    public List<Course> queryCourseByIds(List<Integer> courseIds) {
        return courseMapper.selectCourseByIds(courseIds);
    }
}
