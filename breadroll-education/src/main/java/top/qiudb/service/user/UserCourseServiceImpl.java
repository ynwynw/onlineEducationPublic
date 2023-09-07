package top.qiudb.service.user;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.UserCourseMapper;
import top.qiudb.pojo.user.UserCourse;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/11 18:15
 * @description 描述
 */
@Service
public class UserCourseServiceImpl implements UserCourseService{
    @Autowired
    UserCourseMapper userCourseMapper;

    @Override
    public List<UserCourse> queryCourseByUserId(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return userCourseMapper.selectCourseByUserId(userId);
    }

    @Override
    public Boolean addUserCourse(UserCourse userCourse) {
        return userCourseMapper.insertUserCourse(userCourse);
    }

    @Override
    public Boolean updateUserCourse(UserCourse userCourse) {
        return userCourseMapper.updateUserCourse(userCourse);
    }

    @Override
    public Integer queryCount(Integer userId) {
        return userCourseMapper.queryCount(userId);
    }

    @Override
    public Boolean queryCourseExist(Integer userId, Integer courseId) {
        return userCourseMapper.selectCourseExist(userId,courseId);
    }
}
