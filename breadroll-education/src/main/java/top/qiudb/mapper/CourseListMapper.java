package top.qiudb.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.course.CourseList;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/10 8:21
 * @description 课程目录
 */
@Repository
@Mapper
public interface CourseListMapper {
    //根据课程Id查询课程目录
    public List<CourseList> selectListByCourseId(Integer courseId);

    //根据目录Id查询课程目录
    public  CourseList selectListById(Integer listId);
}
