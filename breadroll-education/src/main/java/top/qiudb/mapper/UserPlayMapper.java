package top.qiudb.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.user.User;
import top.qiudb.pojo.user.UserPlay;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/11 8:50
 * @description 用户播放表
 */
@Repository
@Mapper
public interface UserPlayMapper {
    //添加用户播放记录
    public Boolean insertPlayRecord(UserPlay userPlay);

    //更新用户播放记录
    public Boolean updatePlayRecord(UserPlay userPlay);

    //查询用户播放记录
    public UserPlay selectUserPlay(@Param("userId")Integer userId,@Param("courseId") Integer courseId);
}
