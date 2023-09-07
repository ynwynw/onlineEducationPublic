package top.qiudb.service.course;

import top.qiudb.pojo.course.CourseList;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/10 8:24
 * @description 课程目录
 */
public interface CourseListService {
    //根据课程Id查询课程目录
    public List<CourseList> queryListByCourseId(Integer courseId);

    //根据目录Id查询课程目录
    public  CourseList queryListById(Integer listId);
}
