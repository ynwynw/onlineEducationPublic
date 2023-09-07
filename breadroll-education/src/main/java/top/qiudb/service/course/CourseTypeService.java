package top.qiudb.service.course;

import top.qiudb.pojo.course.CourseType;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/9 9:19
 * @description 课程类别
 */
public interface CourseTypeService {
    //查询所有课程类别
    public List<CourseType> queryAllType();
}
