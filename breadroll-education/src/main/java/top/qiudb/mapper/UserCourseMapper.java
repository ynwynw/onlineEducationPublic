package top.qiudb.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.user.StudyRecord;
import top.qiudb.pojo.user.UserCourse;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/11 18:02
 * @description 用户课程表
 */
@Repository
@Mapper
public interface UserCourseMapper {
    // 通过用户id查询用户课程
    public List<UserCourse> selectCourseByUserId(Integer userId);

    // 添加用户课程
    public Boolean insertUserCourse(UserCourse userCourse);

    // 删除用户课程
    public Boolean updateUserCourse(UserCourse userCourse);

    //查询总数
    public Integer queryCount(Integer userId);

    //判断课程是否存在
    public Boolean selectCourseExist(@Param("userId") Integer userId,@Param("courseId") Integer courseId);
}
