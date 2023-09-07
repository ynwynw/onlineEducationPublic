package top.qiudb.service.marketing;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.BannerMapper;
import top.qiudb.pojo.Banner;

import java.util.List;
@Service
public class BannerServiceImpl implements BannerService{
    @Autowired
    private BannerMapper bannerMapper;
    @Override
    public List<Banner> queryPageBanner(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return bannerMapper.queryPageBanner();
    }

    @Override
    public List<Banner> queryAllBanner() {
        return bannerMapper.queryAllBanner();
    }

    @Override
    public Banner queryByBannerId(int bannerId) {
        return bannerMapper.queryByBannerId(bannerId);
    }

    @Override
    public int updateBannerState(int bannerId) {
        return bannerMapper.updateBannerState(bannerId);
    }

    @Override
    public int deleteBanner(int bannerId) {
        return bannerMapper.deleteBanner(bannerId);
    }

    @Override
    public int addBanner(Banner banner) {
        return bannerMapper.addBanner(banner);
    }

    @Override
    public int editBanner(Banner banner) {
        return bannerMapper.editBanner(banner);
    }

    @Override
    public int updateBannerStateByCourseId(int courseId) {
        return bannerMapper.updateBannerStateByCourseId(courseId);
    }
}
