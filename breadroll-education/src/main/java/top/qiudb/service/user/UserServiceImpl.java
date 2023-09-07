package top.qiudb.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.UserMapper;
import top.qiudb.pojo.user.User;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/25 16:06
 * @description 描述
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Override
    public User queryUserByAccount(String userAccount) {
        return userMapper.queryUserByAccount(userAccount);
    }

    @Override
    public User queryUserById(Integer userId) {
        return userMapper.queryUserById(userId);
    }

    @Override
    public Boolean insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public Boolean updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User queryUserByPhone(String userPhone) {
        return userMapper.queryUserByPhone(userPhone);
    }
}
