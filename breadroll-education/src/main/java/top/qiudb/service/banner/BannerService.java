package top.qiudb.service.banner;

import top.qiudb.pojo.banner.Banner;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/15 8:28
 * @description 轮播图展示
 */
public interface BannerService {
    //查询所有轮播图信息
    public List<Banner> queryAllBanner();
}
