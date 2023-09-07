package top.qiudb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.qiudb.pojo.CourseType;

import java.util.List;

@Mapper
@Component
public interface CourseTypeMapper {
    //添加课程类别
    int addCourseType(CourseType courseType);

    //查询是否已存在该类别
    CourseType queryTypeIsExist(String typeName);

    //查询所有类别
    List<CourseType> queryAllPageType();

    //修改课程类型
    int updateType(CourseType courseType);

    //查询除自己的类型是否存在
    CourseType queryTypeExceptSelf(@Param("typeId") int typeId, @Param("typeName") String typeName);

    //删除类型
    int deleteType(int typeId);

    //查询所有可用的类别
    List<CourseType> queryOnlineType();

    //根据类别名称查询
    List<CourseType> queryTypeByName(CourseType courseType);
    List<CourseType> totalQueryTypeByName(CourseType courseType);
    //根据类别Id查询
    CourseType queryTypeById(int typeId);
    //修改课程类别状态
    int updateTypeState(int typeId);
}

