package top.qiudb.controller.marketing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.mapper.BannerMapper;
import top.qiudb.pojo.Banner;
import top.qiudb.pojo.Course;
import top.qiudb.service.course.CourseService;
import top.qiudb.service.marketing.BannerService;
import top.qiudb.util.tools.ResultVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/banner")
@Api(tags="轮播图信息操作接口")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private CourseService courseService;
    @GetMapping("/pageList")
    @ResponseBody
    @ApiOperation("分页查询所有轮播图")
    public ResultVO queryPageList(@RequestParam int pageNum, @RequestParam int pageSize){
        List<Banner> pageBanner=bannerService.queryPageBanner(pageNum,pageSize);
        List<Banner> totalBanner=bannerService.queryAllBanner();
        Map<String,Object> data=new HashMap<>();
        data.put("total",totalBanner.size());
        data.put("list",pageBanner);
        if(totalBanner.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("查询失败",data);
    }

    @GetMapping("/goToEditBanner")
    @ApiOperation("跳转到添加/编辑轮播图")
    public String goToEditBanner(@ApiParam("轮播图Id") int bannerId, Model model){
        //查询所有的在线且可用课程
        List<Course> courses=courseService.queryPublishedAuditedCourse();
        model.addAttribute("courses",courses);
        if(bannerId!=0){
            Banner banner=bannerService.queryByBannerId(bannerId);
            model.addAttribute("banner",banner);
        }
        return "marketing/add-edit-banner";
    }
    @PostMapping("/addBanner")
    @ResponseBody
    @ApiOperation("添加轮播图")
    public ResultVO addBanner(@ApiParam("轮播图信息")Banner banner){
        int n= bannerService.addBanner(banner);
        if (n==1){
            return ResultVO.success("添加成功");
        }
        return ResultVO.error("添加失败");
    }

    @PostMapping("/editBanner")
    @ResponseBody
    @ApiOperation("添加轮播图")
    public ResultVO editBanner(@ApiParam("轮播图信息")Banner banner){
        int n= bannerService.editBanner(banner);
        if (n==1){
            return ResultVO.success("修改成功");
        }
        return ResultVO.error("修改失败");
    }

    @GetMapping("/updateBannerState")
    @ResponseBody
    @ApiOperation("修改轮播图状态")
    public ResultVO updateBannerState(@ApiParam("轮播图Id")int bannerId){
        Banner banner=bannerService.queryByBannerId(bannerId);
        int auditState=courseService.queryAuditStateById(banner.getCourseId());
        if(auditState!=1){
            return  ResultVO.success("此课程未通过审核，不能开启");
        }else{
            int n=bannerService.updateBannerState(bannerId);
            if (n==1){
                if(!banner.getBannerState()){
                    return ResultVO.success("\" "+banner.getCourseName()+"\" "+" 轮播图已启用");
                }else{
                    return ResultVO.success("\" "+banner.getCourseName()+"\" "+" 轮播图已被禁用");
                }
            }
            return ResultVO.error("\" "+banner.getCourseName()+"\" "+" 轮播图状态修改失败");
        }
    }

    @GetMapping("/deleteBanner")
    @ResponseBody
    @ApiOperation("删除轮播图")
    public ResultVO deleteBanner(@ApiParam("轮播图Id")int bannerId){
        int n=bannerService.deleteBanner(bannerId);
        if(n==1){
            return ResultVO.success("删除成功");
        }
        return ResultVO.error("删除失败");
    }
}
