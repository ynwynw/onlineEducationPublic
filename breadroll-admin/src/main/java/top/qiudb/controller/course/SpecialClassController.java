package top.qiudb.controller.course;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.controller.loginInfo.LoginController;
import top.qiudb.param.AddSpecialParam;
import top.qiudb.pojo.*;
import top.qiudb.service.course.CourseService;
import top.qiudb.service.course.CourseTypeService;
import top.qiudb.service.auditing.SpecialAuditService;
import top.qiudb.service.user.TeacherService;
import top.qiudb.util.tools.ResultVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Controller
@RequestMapping("/special")
@Api(tags="特训班操作接口")
public class SpecialClassController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseTypeService courseTypeService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SpecialAuditService specialAuditService;

    @GetMapping("/goToEditSpecialCourse")
    @ApiOperation("跳转到添加/编辑特训班课程")
    public String goToEditCourse(@ApiParam("课程Id") int courseId, Model model){
        //查询所有课程类别
        List<CourseType> courseTypes=courseTypeService.queryOnlineType();
        //查询所有任职讲师
        List<Teacher> teachers=teacherService.queryAllOnlineTeacher();
        model.addAttribute("courseTypes",courseTypes);
        model.addAttribute("teachers",teachers);
        if (courseId != 0) {
            //查询相应的课程
            Course course = courseService.queryByCourseId(courseId);
            model.addAttribute("course", course);
            System.out.println("课程");
            System.out.println(course);
        }
        return "course/add-edit-special-class";
    }


    @GetMapping("/classList")
    @ResponseBody
    @ApiOperation("分页查询所有特训班")
    public ResultVO queryAllSpecialClass(@RequestParam int pageNum, @RequestParam int pageSize) {
        List<CourseTeacherConn> courses=courseService.queryPageSpecialClass(pageNum,pageSize);
        List<Course> totalCourse=courseService.queryAllSpecialClass();
        LocalDate today = LocalDate.now(); //获取当前日期 年月日
        for (CourseTeacherConn course : courses) {

            course.setEndFlag((today.toEpochDay()-course.getStartTime().toEpochDay()) >= -3 && course.getPublishState() && course.getAuditState()==1);
        }
        Map<String,Object> data=new HashMap<>();
        data.put("list",courses);
        data.put("total",totalCourse.size());
        return ResultVO.success("查询成功",data);
    }

    @PostMapping("/addClass")
    @ResponseBody
    @ApiOperation("添加特训班")
    public ResultVO addSpecialClass(AddSpecialParam addSpecialParam, HttpServletRequest request) throws ParseException {
        Course course=new Course();
        HttpSession session = request.getSession();
        Manager manager= (Manager) session.getAttribute("manager");
        course.setPublisher(manager.getManagerName());
        course.setPublisherId(manager.getManagerId());
        course.setPutTime(new Date());
        course.setUpdateTime(new Date());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=simpleDateFormat.parse(addSpecialParam.getStartTime());
        course.setCourseName(addSpecialParam.getCourseName());
        course.setTypeId(addSpecialParam.getTypeId());
        course.setTypeName(addSpecialParam.getTypeName());
        course.setDescription(addSpecialParam.getDescription());
        course.setTeacherId(addSpecialParam.getTeacherId());
        course.setStartTime(date);
        course.setCourseTime(addSpecialParam.getCourseTime());
        course.setPrice(addSpecialParam.getPrice());
        course.setCoverUrl(addSpecialParam.getCoverUrl());
        int n=courseService.addSpecialClass(course);
        if(n==1){
            return ResultVO.success("添加成功",200);
        }else{
            return ResultVO.error("添加失败",500);
        }
    }

    @PostMapping("/editClass")
    @ResponseBody
    @ApiOperation("修改特训班")
    public ResultVO editSpecialClass(AddSpecialParam addSpecialParam,HttpServletRequest request) throws ParseException {
        Course course=new Course();
        HttpSession session = request.getSession();
        Manager manager= (Manager) session.getAttribute("manager");
        course.setPublisher(manager.getManagerName());
        course.setPublisherId(manager.getManagerId());
        course.setUpdateTime(new Date());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=simpleDateFormat.parse(addSpecialParam.getStartTime());
        course.setCourseId(addSpecialParam.getCourseId());
        course.setCourseName(addSpecialParam.getCourseName());
        course.setTypeId(addSpecialParam.getTypeId());
        course.setTypeName(addSpecialParam.getTypeName());
        course.setDescription(addSpecialParam.getDescription());
        course.setTeacherId(addSpecialParam.getTeacherId());
        course.setStartTime(date);
        course.setCourseTime(addSpecialParam.getCourseTime());
        course.setPrice(addSpecialParam.getPrice());
        course.setCoverUrl(addSpecialParam.getCoverUrl());
        int n=courseService.updateSpecialClass(course);
        if(n==1){
            return ResultVO.success("修改成功",200);
        }else{
            return ResultVO.error("修改失败",500);
        }
    }

    @GetMapping("/delClass")
    @ResponseBody
    @ApiOperation("删除特训班")  //加删除标记
    public ResultVO editSpecialClass(@ApiParam("特训班Id")int courseId){
        int n=courseService.delCourse(courseId);
        if(n==1){
            return ResultVO.success("删除成功",200);
        }else{
            return ResultVO.error("删除失败",500);
        }
    }

    @PostMapping("/selSpecialCourse")
    @ResponseBody
    @ApiOperation("搜索特训班课程")
    public ResultVO selectOnlineCourse(@ApiParam("课程Id")String courseId,
                                       @ApiParam("课程名称")String courseName,
                                       @ApiParam("课程类别")String typeName,
                                       @ApiParam("讲师姓名")String teacherName,
                                       @RequestParam int pageNum, @RequestParam int pageSize){
        CourseTeacherConn courseTeacherConn=new CourseTeacherConn();
        if(courseId!=null && courseId.trim().length()!=0){
            courseTeacherConn.setCourseId(Integer.parseInt(courseId));
        }
        courseTeacherConn.setTeacherName(teacherName);
        courseTeacherConn.setCourseName(courseName);
        courseTeacherConn.setTypeName(typeName);
        List<CourseTeacherConn> courses=courseService.selSpecialCourse(courseTeacherConn,pageNum,pageSize);
        List<CourseTeacherConn> totalCourses=courseService.totalSelSpecialCourse(courseTeacherConn);
         LocalDate today = LocalDate.now(); //获取当前日期 年月日
        for (CourseTeacherConn cours : courses) {
            cours.setEndFlag(Period.between(today, cours.getStartTime()).getDays() < 5);
        }
        Map<String,Object> data=new HashMap<>();
        assert courseId != null;
        if(courseId.trim().equals("") && teacherName.trim().equals("") && courseName.trim().equals("") && typeName.trim().equals("")){
            List<Course> totalCourse=courseService.queryAllSpecialClass();
            data.put("total",totalCourse.size());
        }else{
            data.put("total",totalCourses.size());
        }
        data.put("list",courses);
        if(courses.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("无查询结果",data);
    }

    @GetMapping("/updatePublishState")
    @ResponseBody
    @ApiOperation("修改特训班课程的发布状态")
    public ResultVO updatePublishState(@ApiParam("课程Id")int courseId,HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager manager= (Manager) session.getAttribute("manager");
        int n=courseService.updatePublishState(courseId,manager.getManagerName(), manager.getManagerId());
        Course course=courseService.queryByCourseId(courseId);
        Boolean publishState=course.getPublishState();
        //添加一条相应的课程审核信息
        SpecialAudit specialAudit=new SpecialAudit();
        specialAudit.setCourseId(courseId);
        specialAudit.setCourseName(course.getCourseName());
        specialAudit.setPublisherId(course.getPublisherId());
        specialAudit.setPublisher(course.getPublisher());
        if(n==1){
            //如果课程发布状态改变true，则向课程审核表中添加一条记录
            //如果为false,则关闭了课程  课程一旦关闭它的审核状态改为0
            if(publishState){
                int offCourse=courseService.updateOFFAuditState(courseId,0);
                if (offCourse==1) {
                    int flag = specialAuditService.addSpecialAuditInfo(specialAudit);
                    if (flag == 1) {
                        return ResultVO.success("修改成功");
                    }
                }
                return ResultVO.error("修改失败");
            }else{
                int i=courseService.updateOFFAuditState(courseId,-1);
                if(i==1){
                    //改变特训班审核表的审核状态为3
                    int j=specialAuditService.updateCourseAudit(courseId,3);
                    if(j==1){
                        return ResultVO.success("已关闭审核状态");
                    }
                }
                return ResultVO.error("关闭审核状态失败");
            }
        }
        return ResultVO.error("失败");
    }

    @PostMapping("/refuseCause")
    @ApiOperation("查询拒绝原因")
    @ResponseBody
    public ResultVO queryRefuseCause(@ApiParam("课程Id")int courseId){
        String remark=specialAuditService.queryRefuseCause(courseId);
        return ResultVO.success(remark);
    }
}
