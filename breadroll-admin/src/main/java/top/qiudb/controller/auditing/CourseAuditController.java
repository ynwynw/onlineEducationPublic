package top.qiudb.controller.auditing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.*;
import top.qiudb.service.auditing.CourseAuditService;
import top.qiudb.service.course.CourseListService;
import top.qiudb.service.course.CourseService;
import top.qiudb.service.user.TeacherService;
import top.qiudb.util.tools.ResultVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/courseAudit")
@Api(tags="课程审核信息操作接口")
public class CourseAuditController {
    @Autowired
    private CourseAuditService courseAuditService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseListService courseListService;
    @GetMapping("/pageList")
    @ResponseBody
    @ApiOperation("分页查询课程审核信息")
    public ResultVO pageCourseAudit(@RequestParam int pageNum, @RequestParam int pageSize){
        List<CourseAudit> pageCourseAudit= courseAuditService.pageCourseAudit(pageNum,pageSize);
        List<CourseAudit> totalCourseAudit=courseAuditService.totalCourseAudit();
        Map<String,Object> data=new HashMap<>();
        data.put("list",pageCourseAudit);
        data.put("total",totalCourseAudit.size());
        if(totalCourseAudit.size()==1){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("查询失败",data);
    }
    @GetMapping("/lookCourse")
    @ApiOperation("跳转到查看课程")
    public String goToLookCourse(@ApiParam("课程Id") String courseId,@ApiParam("课程审核Id")String auditId, Model model){
        //获取该课程的审核状态
        int auditState=courseAuditService.queryAuditStateById(Integer.parseInt(auditId));
        //查询相应的课程
        Course course=courseService.queryByCourseId(Integer.parseInt(courseId));
        course.setAuditState(auditState);
        //根据课程Id搜索相应的目录
        List<CourseList> courseLists=courseListService.queryDirectoryByCourseId(Integer.parseInt(courseId));
        int totalTime=0;
        int totalSecond=0;
        for (CourseList courseList : courseLists) {
            totalTime+=courseList.getTimeMinute();
            totalSecond+=courseList.getTimeSecond();
        }
        StringBuffer timeInfo=new StringBuffer();
        if(totalSecond/60!=0){
            totalTime+=totalSecond/60;
            totalSecond=totalSecond%60;
        }
        if(totalTime!=0){
            timeInfo.append(totalTime).append("分");
        }
        if(totalSecond!=0){
            timeInfo.append(totalSecond).append("秒");
        }else{
            timeInfo.append("钟");
        }
        //获取第一条目录的视频
        if(courseLists.size()!=0){
            model.addAttribute("firstVideo",courseLists.get(0).getVideoUrl());
        }
        Teacher teacher = teacherService.queryTeacherById(course.getTeacherId());
        model.addAttribute("operation","audit");
        model.addAttribute("teacher",teacher);
        model.addAttribute("totalTime",timeInfo);
        model.addAttribute("course",course);
        model.addAttribute("courseLists",courseLists);
        return "course/class-details";
    }

    @PostMapping("/searchCourseAudit")
    @ApiOperation("搜索课程审核信息")
    @ResponseBody
    public ResultVO searchCourseAudit(@ApiParam("课程名字")String courseName,
                                      @RequestParam int pageNum, @RequestParam int pageSize){
        CourseAudit courseAudit=new CourseAudit();
        courseAudit.setCourseName(courseName);
        List<CourseAudit> searchCourseAudit= courseAuditService.searchCourseAudit(courseAudit,pageNum,pageSize);
        List<CourseAudit> totalSearchCourseAudit=courseAuditService.totalSearchCourseAudit(courseAudit);
        Map<String,Object> data=new HashMap<>();
        if(courseName.trim().length()==0){
            List<CourseAudit> totalCourseAudit=courseAuditService.totalCourseAudit();
            data.put("total",totalCourseAudit.size());
        }else{
            data.put("total",totalSearchCourseAudit.size());
        }
        data.put("list",searchCourseAudit);
        if(searchCourseAudit.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("无查询结果",data);
    }

    @PostMapping("/refuseCause")
    @ApiOperation("查询拒绝原因")
    @ResponseBody
    public ResultVO queryRefuseCause(int courseId){
        String remark=courseAuditService.queryRefuseCause(courseId);
        return ResultVO.success(remark);
    }
}
