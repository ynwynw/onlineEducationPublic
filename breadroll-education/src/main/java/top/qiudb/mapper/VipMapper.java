package top.qiudb.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.vip.Vip;
import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/14 14:58
 * @description VIP详情表
 */
@Repository
@Mapper
public interface VipMapper {
    //查询所有VIP信息
    public List<Vip> selectAllVip();

    //根据vipId查询VIP
    public Vip selectVipById(Integer vipId);
}
