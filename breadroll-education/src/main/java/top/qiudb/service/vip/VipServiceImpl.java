package top.qiudb.service.vip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.VipMapper;
import top.qiudb.pojo.vip.Vip;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/14 15:23
 * @description 描述
 */
@Service
public class VipServiceImpl implements VipService{
    @Autowired
    VipMapper vipMapper;

    @Override
    public List<Vip> queryAllVip() {
        return vipMapper.selectAllVip();
    }

    @Override
    public Vip queryVipById(Integer vipId) {
        return vipMapper.selectVipById(vipId);
    }
}
