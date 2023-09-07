package top.qiudb.service.user;

import top.qiudb.pojo.User;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/25 16:05
 * @description 描述
 */
public interface UserService {
    User queryUserByName(String userName);
    //分页查询未删除的所有用户
    List<User> queryPageUser(int pageNum, int pageSize);
    //查询所有用户统计数量
    List<User> queryAllUser();
    //修改用户的锁定状态
    int updateLockState(int userId);
    //搜索用户
    List<User> searchUser(User user,int pageNum, int pageSize);
    //搜索用户   统计
    List<User> totalSearchUser(User user);
}
