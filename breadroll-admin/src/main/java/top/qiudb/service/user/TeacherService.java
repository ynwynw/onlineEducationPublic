package top.qiudb.service.user;

import org.apache.ibatis.annotations.Param;
import top.qiudb.pojo.Teacher;

import java.util.List;

public interface TeacherService {
    //分页查询全部讲师
    List<Teacher> queryAllTeacher(int pageNum, int pageSize);
    //查询讲师统计数量
    List<Teacher> totalTeacher();
    //添加讲师
    int addTeacher(Teacher teacher);
    //修改讲师
    int updateTeacher(Teacher teacher);
    //查询手机号是否存在
    Teacher queryTeacherByPhone(String teacherPhone);
    //查询身份证号是否存在
    Teacher queryTeacherByIdCard(String idCard);
    //查询除本人外的手机号是否存在
    Teacher queryExceptByPhone(@Param("teacherId")int teacherId, @Param("teacherPhone")String teacherPhone);
    //查询除本人外的身份证是否存在
    Teacher queryExceptByIdCard(@Param("teacherId")int teacherId,@Param("idCard")String idCard);
    //删除讲师
    int deleteTeacher(int teacherId);
    //搜索讲师
    List<Teacher> queryTeacher(Teacher teacher,int pageNum, int pageSize);
    //搜索讲师 统计
    List<Teacher> totalQueryTeacher(Teacher teacher);
    //查询所有任职讲师
    List<Teacher> queryAllOnlineTeacher();
    //修改讲师的任职状态
    int updateTeacherState(int teacherId);
    //根据讲师Id查询
    Teacher queryTeacherById(int teacherId);
}
