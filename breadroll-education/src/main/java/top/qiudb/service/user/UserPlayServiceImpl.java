package top.qiudb.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.UserPlayMapper;
import top.qiudb.pojo.user.UserPlay;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/11 9:01
 * @description 描述
 */
@Service
public class UserPlayServiceImpl implements UserPlayService{
    @Autowired
    UserPlayMapper userPlayMapper;

    @Override
    public Boolean addPlayRecord(UserPlay userPlay) {
        return userPlayMapper.insertPlayRecord(userPlay);
    }


    @Override
    public Boolean updatePlayRecord(UserPlay userPlay) {
        return userPlayMapper.updatePlayRecord(userPlay);
    }

    @Override
    public UserPlay queryUserPlay(Integer userId, Integer courseId) {
        return userPlayMapper.selectUserPlay(userId,courseId);
    }
}
