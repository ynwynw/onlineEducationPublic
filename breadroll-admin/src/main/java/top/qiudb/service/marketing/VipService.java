package top.qiudb.service.marketing;

import top.qiudb.pojo.Vip;
import top.qiudb.pojo.DataModel;

import java.util.List;

public interface VipService {
    //分页查询vip
    List<Vip> queryPageVip(int pageNum, int pageSize);
    //查询所有未删除的vip  统计数量
    List<Vip> queryAllVip();
    //根据vipId查询
    Vip queryByVipId(int vipId);
    //添加VIP
    int addVip(Vip vip);
    //修改VIP
    int updateVip(Vip vip);
    //改变VIP状态
    int updateVipState(int vipId);
    //删除VIP
    int deleteVip(int vipId);
    //统计VIP用户数量
    DataModel countVIPUser();
}
