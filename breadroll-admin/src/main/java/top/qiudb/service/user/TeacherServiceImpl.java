package top.qiudb.service.user;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.TeacherMapper;
import top.qiudb.pojo.Teacher;

import java.util.List;
@Service
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public List<Teacher> queryAllTeacher(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return teacherMapper.queryAllTeacher();
    }

    @Override
    public List<Teacher> totalTeacher() {
        return teacherMapper.totalTeacher();
    }

    @Override
    public int addTeacher(Teacher teacher) {
        return teacherMapper.addTeacher(teacher);
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        return teacherMapper.updateTeacher(teacher);
    }

    @Override
    public Teacher queryTeacherByPhone(String teacherPhone) {
        return teacherMapper.queryTeacherByPhone(teacherPhone);
    }

    @Override
    public Teacher queryTeacherByIdCard(String idCard) {
        return teacherMapper.queryTeacherByIdCard(idCard);
    }

    @Override
    public Teacher queryExceptByPhone(int teacherId, String teacherPhone) {
        return teacherMapper.queryExceptByPhone(teacherId,teacherPhone);
    }

    @Override
    public Teacher queryExceptByIdCard(int teacherId, String idCard) {
        return teacherMapper.queryExceptByIdCard(teacherId,idCard);
    }

    @Override
    public int deleteTeacher(int teacherId) {
        return teacherMapper.deleteTeacher(teacherId);
    }

    @Override
    public List<Teacher> queryTeacher(Teacher teacher, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return teacherMapper.queryTeacher(teacher);
    }

    @Override
    public List<Teacher> totalQueryTeacher(Teacher teacher) {
        return teacherMapper.totalQueryTeacher(teacher);
    }

    @Override
    public List<Teacher> queryAllOnlineTeacher() {
        return teacherMapper.queryAllOnlineTeacher();
    }

    @Override
    public int updateTeacherState(int teacherId) {
        return teacherMapper.updateTeacherState(teacherId);
    }

    @Override
    public Teacher queryTeacherById(int teacherId) {
        return teacherMapper.queryTeacherById(teacherId);
    }

}
