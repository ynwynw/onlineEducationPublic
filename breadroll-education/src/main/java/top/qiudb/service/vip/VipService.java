package top.qiudb.service.vip;

import top.qiudb.pojo.vip.Vip;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/14 15:22
 * @description VIP详情
 */
public interface VipService {
    //查询所有VIP信息
    public List<Vip> queryAllVip();

    //根据vipId查询VIP
    public Vip queryVipById(Integer vipId);
}
