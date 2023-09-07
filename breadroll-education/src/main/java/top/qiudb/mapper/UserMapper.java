package top.qiudb.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.user.User;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/30 17:46
 * @description 描述
 */
@Repository
@Mapper
public interface UserMapper {
    //注册用户
    public Boolean insertUser(User user);

    //更新信息
    public Boolean updateUser(User user);

    //根据Id查询用户
    public User queryUserById(Integer userId);

    //根据帐号查询用户
    public User queryUserByAccount(String userAccount);

    //根据手机号查询用户
    public User queryUserByPhone(String userPhone);
}
