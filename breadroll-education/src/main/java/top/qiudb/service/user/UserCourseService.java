package top.qiudb.service.user;

import org.apache.ibatis.annotations.Param;
import top.qiudb.pojo.user.UserCourse;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/11 18:14
 * @description 用户课程表
 */
public interface UserCourseService {
    // 通过用户id查询用户课程
    public List<UserCourse> queryCourseByUserId(Integer userId,int pageNum, int pageSize);

    // 添加用户课程
    public Boolean addUserCourse(UserCourse userCourse);

    // 删除用户课程
    public Boolean updateUserCourse(UserCourse userCourse);

    //查询总数
    public Integer queryCount(Integer userId);

    //判断课程是否存在
    public Boolean queryCourseExist(Integer userId,Integer courseId);
}
