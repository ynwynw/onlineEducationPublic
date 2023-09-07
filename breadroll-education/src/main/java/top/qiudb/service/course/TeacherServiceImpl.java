package top.qiudb.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.TeacherMapper;
import top.qiudb.pojo.course.Teacher;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/10 8:43
 * @description 描述
 */
@Service
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public Teacher queryTeacherById(Integer teacherId) {
        return teacherMapper.selectTeacherById(teacherId);
    }
}
