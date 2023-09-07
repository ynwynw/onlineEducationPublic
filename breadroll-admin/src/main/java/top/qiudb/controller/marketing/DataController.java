package top.qiudb.controller.marketing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.qiudb.pojo.*;
import top.qiudb.service.course.CoachApplyService;
import top.qiudb.service.course.CourseService;
import top.qiudb.service.marketing.VipService;

import java.util.List;

@Controller
@RequestMapping("/data")
@Api(tags = "数据分析信息操作接口")
public class DataController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CoachApplyService coachApplyService;
    @Autowired
    private VipService vipService;
    @GetMapping("/analysis")
    @ApiOperation("跳转到数据分析界面")
    public String goToData(Model model){
        //按课程播放量取前五名
        List<Course> courses= courseService.queryTopFiveCourse();
        //查询前五名讲师所讲授课程数量
        List<CountTeacherCourse> countCourse= courseService.queryTeacherCourse();
        //查询前五名特训班报名情况
        List<CountGroupApply> countGroupApplies= coachApplyService.queryCountGroupApply();
        //查询数据
        DataModel dataModel=vipService.countVIPUser();
        model.addAttribute("courses",courses);
        model.addAttribute("countCourse",countCourse);
        model.addAttribute("countGroupApply",countGroupApplies);
        model.addAttribute("dataModel",dataModel);
        return "page/data";
    }
}
