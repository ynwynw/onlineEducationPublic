package top.qiudb.controller.course;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.param.CoursePreviewParam;
import top.qiudb.pojo.CoursePreview;
import top.qiudb.pojo.Teacher;
import top.qiudb.service.course.CoursePreviewService;
import top.qiudb.service.user.TeacherService;
import top.qiudb.util.tools.ResultVO;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/preview")
@Api(tags = "课程预告信息操作接口")
public class CoursePreviewController {
    @Autowired
    private CoursePreviewService coursePreviewService;
    @Autowired
    private TeacherService teacherService;
    @GetMapping("/pageCoursePreview")
    @ResponseBody
    @ApiOperation("分页查询课程预告")
    public ResultVO pageCoursePreview(@RequestParam int pageNum, @RequestParam int pageSize){
        List<CoursePreview> pageCoursePreview=coursePreviewService.pageCoursePreview(pageNum,pageSize);
        List<CoursePreview> totalCoursePreview=coursePreviewService.totalCoursePreview();
        LocalDate date=LocalDate.now();
        for (CoursePreview coursePreview : pageCoursePreview) {
            if((date.toEpochDay()-coursePreview.getStartTime().toEpochDay())>0){
                coursePreviewService.OFFCoursePreview(coursePreview.getPreviewId());
            }
        }
        Map<String,Object> data=new HashMap<>();
        data.put("list",pageCoursePreview);
        data.put("total",totalCoursePreview.size());
        if(totalCoursePreview.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("查询失败",data);
    }

    @GetMapping("/goToAddEditPreview")
    @ApiOperation("跳转到课程添加/编辑预告")
    public String goToAddEditPreview(int previewId, Model model){
        List<Teacher> teachers=teacherService.queryAllOnlineTeacher();
        if(previewId!=0){
            CoursePreview coursePreview= coursePreviewService.queryCoursePreviewById(previewId);
            model.addAttribute("coursePreview",coursePreview);
        }
        model.addAttribute("teachers",teachers);
        return "course/add-edit-course-preview";
    }

    @PostMapping("/searchCoursePreview")
    @ResponseBody
    @ApiOperation("搜索课程预告")
    public ResultVO searchCoursePreview(@ApiParam("课程名称")String courseName,
                                        @ApiParam("开课时间")String startTime,
                                        @RequestParam int pageNum, @RequestParam int pageSize) throws ParseException {
        CoursePreview coursePreview=new CoursePreview();
        coursePreview.setCourseName(courseName);
        if (startTime!=null && startTime.trim().length()!=0){
            LocalDate date=LocalDate.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            coursePreview.setStartTime(date);
        }
        List<CoursePreview> searchPreview= coursePreviewService.searchCoursePreview(coursePreview,pageNum,pageSize);
        List<CoursePreview> totalSearchPreview=coursePreviewService.totalSearchCoursePreview(coursePreview);
        Map<String,Object> data=new HashMap<>();
        if(courseName.trim().equals("") && Objects.requireNonNull(startTime).trim().equals("")){
            List<CoursePreview> totalCoursePreview=coursePreviewService.totalCoursePreview();
            data.put("total",totalCoursePreview.size());
        }else{
            data.put("total",totalSearchPreview.size());
        }
        data.put("list",searchPreview);
        if(searchPreview.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("无查询结果",data);
    }

    @PostMapping("/addCoursePreview")
    @ResponseBody
    @ApiOperation("添加课程预告")
    public ResultVO addCoursePreview(@ApiParam("课程预告信息") CoursePreviewParam coursePreviewParam) {
        CoursePreview coursePreview=new CoursePreview();
        Teacher teacher= teacherService.queryTeacherById(coursePreviewParam.getTeacherId());
        coursePreview.setCourseName(coursePreviewParam.getCourseName());
        coursePreview.setDescription(coursePreviewParam.getDescription());
        coursePreview.setTimeLength(coursePreviewParam.getTimeLength());
        coursePreview.setTeacherId(coursePreviewParam.getTeacherId());
        coursePreview.setTeacherName(teacher.getTeacherName());
        coursePreview.setTeacherDes(teacher.getDescription());
        coursePreview.setStartTime(LocalDate.parse(coursePreviewParam.getStartTime()));
        int n=coursePreviewService.addCoursePreview(coursePreview);
        if(n==1){
            return ResultVO.success("发布成功");
        }
        return ResultVO.error("发布失败");
    }

    @PostMapping("/editCoursePreview")
    @ResponseBody
    @ApiOperation("编辑课程预告")
    public ResultVO editCoursePreview(@ApiParam("课程预告信息")CoursePreviewParam coursePreviewParam) {
        CoursePreview coursePreview=new CoursePreview();
        Teacher teacher= teacherService.queryTeacherById(coursePreviewParam.getTeacherId());
        coursePreview.setPreviewId(coursePreviewParam.getPreviewId());
        coursePreview.setCourseName(coursePreviewParam.getCourseName());
        coursePreview.setDescription(coursePreviewParam.getDescription());
        coursePreview.setStartTime(LocalDate.parse(coursePreviewParam.getStartTime()));
        coursePreview.setTimeLength(coursePreviewParam.getTimeLength());
        coursePreview.setTeacherId(coursePreviewParam.getTeacherId());
        coursePreview.setTeacherName(teacher.getTeacherName());
        coursePreview.setTeacherDes(teacher.getDescription());
        int n=coursePreviewService.updateCoursePreview(coursePreview);
        if(n==1){
            return ResultVO.success("保存成功");
        }
        return ResultVO.error("保存失败");
    }

    @GetMapping("/updatePreviewState")
    @ResponseBody
    @ApiOperation("修改预告状态")
    public ResultVO updatePreviewState(@ApiParam("预告Id") int previewId){
        LocalDate date=LocalDate.now();
        CoursePreview coursePreview= coursePreviewService.queryCoursePreviewById(previewId);
        LocalDate startTime=coursePreview.getStartTime();
        if(Period.between(date,startTime).getDays()>0){
            int n=coursePreviewService.updatePreviewState(previewId);
            if (n==1){
                return ResultVO.success("修改成功");
            }
        }
        return ResultVO.error("公告已过期,不可打开");
    }
    @GetMapping("/deleteCoursePreview")
    @ResponseBody
    @ApiOperation("删除课程预告")
    public ResultVO deleteCoursePreview(@ApiParam("预告Id")int previewId){
        int n=coursePreviewService.deleteCoursePreview(previewId);
        if(n==1){
            return ResultVO.success("删除成功");
        }
        return ResultVO.error("删除失败");
    }
}
