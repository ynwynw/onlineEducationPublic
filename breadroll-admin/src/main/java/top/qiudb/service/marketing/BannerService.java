package top.qiudb.service.marketing;

import top.qiudb.pojo.Banner;

import java.util.List;

public interface BannerService {
    //分页查询轮播图
    List<Banner> queryPageBanner(int pageNum, int pageSize);
    //查询所有可用轮播图  统计数量
    List<Banner> queryAllBanner();
    //根据Id查询
    Banner queryByBannerId(int bannerId);
    //修改轮播图状态
    int updateBannerState(int bannerId);
    //删除轮播图
    int deleteBanner(int bannerId);
    //添加轮播图
    int addBanner(Banner banner);
    //编辑轮播图
    int editBanner(Banner banner);
    //根据课程Id关闭轮播图状态
    int updateBannerStateByCourseId(int courseId);
}
