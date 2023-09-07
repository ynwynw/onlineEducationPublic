package top.qiudb.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.UserCoinMapper;
import top.qiudb.pojo.user.UserCoin;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/6 8:35
 * @description 用户积分表
 */
@Service
public class UserCoinServiceImpl implements UserCoinService{
    @Autowired
    UserCoinMapper userCoinMapper;

    @Override
    public Boolean insertCoin(UserCoin userCoin) {
        return userCoinMapper.insertCoin(userCoin);
    }

    @Override
    public Boolean updateCoin(UserCoin userCoin) {
        return userCoinMapper.updateCoin(userCoin);
    }

    @Override
    public UserCoin queryCoinById(Integer userId) {
        return userCoinMapper.queryCoinById(userId);
    }
}
