package top.qiudb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.qiudb.pojo.CourseList;

import java.util.List;

@Mapper
@Component
public interface CourseListMapper {
    //分页查询全部目录
    List<CourseList> queryAllPageDirectory();
    //添加目录
    int addDirectory(CourseList courseList);
    //修改目录
    int updateDirectory(CourseList courseList);
    //删除目录
    int deleteDirectory(int listId);
    //根据目录Id查询
    CourseList queryListById(int listId);
    //查询所有未删除目录 统计数量
    List<CourseList> queryAllDirectory();
    //根据条件进行查询
    List<CourseList> selectDirectory(CourseList courseList);
    //根据条件进行查询 统计数量
    List<CourseList> totalSelectDirectory(CourseList courseList);
    //根据课程Id查询相应的目录
    List<CourseList> queryDirectoryByCourseId(int courseId);
    //根据课程Id删除所有目录
    int deleteCourseDirectory(int courseId);
    //根据课程Id修改课程名称
    int updateCourseNameByCourseId(@Param("courseId")int courseId,@Param("courseName")String courseName);
    //根据课程Id查询
    List<CourseList> queryByCourseId(int courseId);
}
