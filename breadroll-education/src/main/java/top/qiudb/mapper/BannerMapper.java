package top.qiudb.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.banner.Banner;
import top.qiudb.pojo.vip.Vip;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/15 8:24
 * @description 轮播图表
 */
@Repository
@Mapper
public interface BannerMapper {
    //查询所有轮播图信息
    public List<Banner> selectAllBanner();
}
