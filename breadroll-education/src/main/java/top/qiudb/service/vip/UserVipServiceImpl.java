package top.qiudb.service.vip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.UserVipMapper;
import top.qiudb.pojo.vip.UserVip;
import top.qiudb.pojo.vip.Vip;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/14 20:41
 * @description 描述
 */
@Service
public class UserVipServiceImpl implements UserVipService{
    @Autowired
    UserVipMapper userVipMapper;

    @Override
    public UserVip queryUserVipByUserId(Integer userId) {
        return userVipMapper.selectUserVipByUserId(userId);
    }

    @Override
    public Vip queryVipByUserId(Integer userId) {
        return userVipMapper.selectVipByUserId(userId);
    }

    @Override
    public Boolean insertUserVip(UserVip userVip) {
        return userVipMapper.insertUserVip(userVip);
    }

    @Override
    public Boolean updateUserVip(UserVip userVip) {
        return userVipMapper.updateUserVip(userVip);
    }
}
