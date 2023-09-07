package top.qiudb.service.user;

import top.qiudb.pojo.user.UserPlay;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/11 9:00
 * @description 播放记录表
 */
public interface UserPlayService {
    //添加用户播放记录
    public Boolean addPlayRecord(UserPlay userPlay);

    //更新用户播放记录
    public Boolean updatePlayRecord(UserPlay userPlay);

    //查询用户播放记录
    public UserPlay queryUserPlay(Integer userId,Integer courseId);
}
