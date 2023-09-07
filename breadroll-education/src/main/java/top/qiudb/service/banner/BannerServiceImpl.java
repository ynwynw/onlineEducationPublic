package top.qiudb.service.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.BannerMapper;
import top.qiudb.pojo.banner.Banner;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/15 8:29
 * @description 描述
 */
@Service
public class BannerServiceImpl implements BannerService{
    @Autowired
    BannerMapper bannerMapper;

    @Override
    public List<Banner> queryAllBanner() {
        return bannerMapper.selectAllBanner();
    }
}
