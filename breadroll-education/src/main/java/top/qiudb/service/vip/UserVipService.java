package top.qiudb.service.vip;

import top.qiudb.pojo.vip.UserVip;
import top.qiudb.pojo.vip.Vip;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/14 20:40
 * @description 用户VIP表
 */
public interface UserVipService {
    //通过用户Id获取
    public UserVip queryUserVipByUserId(Integer userId);

    //根据用户Id获取VIP信息
    public Vip queryVipByUserId(Integer userId);

    //插入会员信息
    public Boolean insertUserVip(UserVip userVip);

    //修改信息
    public Boolean updateUserVip(UserVip userVip);
}
