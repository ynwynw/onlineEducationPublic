package top.qiudb.controller.banner;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.qiudb.pojo.banner.Banner;
import top.qiudb.service.banner.BannerService;
import top.qiudb.util.tools.PropertiesUtil;
import top.qiudb.util.tools.ResultVO;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/15 8:31
 * @description 轮播图
 */
@RestController
@CrossOrigin
@RequestMapping("/banner")
@Api(tags = "轮播图详情接口")
public class BannerController {
    @Autowired
    BannerService bannerService;
    //后台服务器地址
    String adminAddress = PropertiesUtil.getAdminAddress();


    // 获取轮播图信息
    @GetMapping("/info")
    @ApiOperation("查询轮播图详情")
    public ResultVO queryVips() {
        List<Banner> banners = bannerService.queryAllBanner();
        banners.forEach(banner -> banner.setBannerUrl(adminAddress+banner.getBannerUrl()));
        return ResultVO.success("轮播图查询成功",banners);
    }
}
