package top.qiudb.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.vip.UserVip;
import top.qiudb.pojo.vip.Vip;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/14 20:31
 * @description 用户VIP表
 */
@Repository
@Mapper
public interface UserVipMapper {
    //通过用户Id获取用户VIP信息
    public UserVip selectUserVipByUserId(Integer userId);

    //根据用户Id获取VIP信息
    public Vip selectVipByUserId(Integer userId);

    //插入会员信息
    public Boolean insertUserVip(UserVip userVip);

    //修改信息
    public Boolean updateUserVip(UserVip userVip);
}
