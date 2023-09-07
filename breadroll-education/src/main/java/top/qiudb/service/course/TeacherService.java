package top.qiudb.service.course;

import top.qiudb.pojo.course.Teacher;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/10 8:35
 * @description 课程讲师
 */
public interface TeacherService {
    //根据Id查询讲师信息
    public Teacher queryTeacherById(Integer teacherId);
}
