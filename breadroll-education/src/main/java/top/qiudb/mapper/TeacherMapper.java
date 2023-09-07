package top.qiudb.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.course.Teacher;
import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/10 8:31
 * @description 课程讲师
 */
@Repository
@Mapper
public interface TeacherMapper {
    //根据Id查询讲师信息
    public Teacher selectTeacherById(Integer teacherId);
}
