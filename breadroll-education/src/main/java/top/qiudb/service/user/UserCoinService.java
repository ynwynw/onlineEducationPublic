package top.qiudb.service.user;

import top.qiudb.pojo.user.UserCoin;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/6 8:35
 * @description 用户积分表
 */
public interface UserCoinService {
    //添加用户积分
    public Boolean insertCoin(UserCoin userCoin);
    //更新用户积分
    public Boolean updateCoin(UserCoin userCoin);
    //查询用户积分
    public UserCoin queryCoinById(Integer userId);
}
