package top.qiudb.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.user.UserCoin;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/6 8:23
 * @description 用户积分表
 */
@Repository
@Mapper
public interface UserCoinMapper {
    //添加用户积分
    public Boolean insertCoin(UserCoin userCoin);
    //更新用户积分
    public Boolean updateCoin(UserCoin userCoin);
    //查询用户积分
    public UserCoin queryCoinById(Integer userId);
}
