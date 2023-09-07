package top.qiudb.controller.course;

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
import top.qiudb.service.course.CourseTypeService;
import top.qiudb.service.marketing.BannerService;
import top.qiudb.service.user.TeacherService;
import top.qiudb.util.tools.ResultVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/course")
@Api(tags = "课程操作接口")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseTypeService courseTypeService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseListService courseListService;
    @Autowired
    private CourseAuditService courseAuditService;
    @Autowired
    private BannerService bannerService;

    @GetMapping("/goToEditCourse")
    @ApiOperation("跳转到添加/编辑课程")
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
        }
        //是否显示   审核通过 和 审核拒绝按钮
        model.addAttribute("isShow",false);
        return "course/add-edit-online-class";
    }


    @ResponseBody
    @GetMapping("/list")
    @ApiOperation("分页查询所有线上课程")
    public ResultVO courseList(@RequestParam int pageNum, @RequestParam int pageSize){
        List<CourseTeacherConn> courses=courseService.queryPageOnlineCourse(pageNum,pageSize);
        List<Course> totalCourse=courseService.queryAllOnlineCourse();
        int total=totalCourse.size();
        Map<String,Object> data=new HashMap<>();
        data.put("list",courses);
        data.put("total",total);
        return ResultVO.success("查询成功",data);
    }

    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation("修改线上课程")
    public ResultVO editClass(@ApiParam("课程信息") Course course, HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager manager= (Manager) session.getAttribute("manager");
        course.setPublisherId(manager.getManagerId());
        course.setPublisher(manager.getManagerName());
        course.setUpdateTime(new Date());   //设置修改时间
        int n=courseService.updateOnlineCourse(course);
        if(n==1){
            //修改目录表中相关的课程名字
            courseListService.updateCourseNameByCourseId(course.getCourseId(),course.getCourseName());
            return ResultVO.success("修改成功",200);
        }else {
            return ResultVO.error("修改失败",500);
        }
    }

    @PostMapping("/addCourse")
    @ResponseBody
    @ApiOperation("添加线上课程")
    public ResultVO addOnlineCourse(@ApiParam("课程信息")Course course,HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager manager= (Manager) session.getAttribute("manager");
        course.setPublisher(manager.getManagerName());  //发布人名字
        course.setPublisherId(manager.getManagerId()); //发布人ID
        course.setPutTime(new Date());
        course.setUpdateTime(new Date());
        int n=courseService.addOnlineCourse(course);
        if(n==1){
            return ResultVO.success("添加成功",200);
        }else{
            return ResultVO.error("添加失败",500);
        }
    }

    @GetMapping("/delCourse")
    @ResponseBody
    @ApiOperation("删除课程")
    public ResultVO delCourse(@ApiParam("课程ID")int courseId){
        int n=courseService.delCourse(courseId);
        if(n==1){
            int i=courseListService.deleteCourseDirectory(courseId);
            if(i!=0){
                return ResultVO.success("删除成功",200);
            }
        }
        return ResultVO.error("删除失败",500);
    }

    @PostMapping("/selOnlineCourse")
    @ResponseBody
    @ApiOperation("搜索线上课程")
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
        List<CourseTeacherConn> courses=courseService.selOnlineCourse(courseTeacherConn,pageNum,pageSize);
        List<CourseTeacherConn> totalCourses=courseService.totalselOnlineCourse(courseTeacherConn);
        Arrays.toString(courses.toArray());
        Map<String,Object> data=new HashMap<>();
        assert courseId != null;
        if(courseId.trim().equals("") && courseName.trim().equals("") && typeName.trim().equals("") && teacherName.trim().equals("")){
            List<Course> totalCourse=courseService.queryAllOnlineCourse();
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

    @GetMapping("/details")
    @ApiOperation("跳转到课程详情")
    public String goToDetails(@ApiParam("课程Id") String courseId,Model model){
        int CId=Integer.parseInt(courseId);
        //查询相应的课程
        Course course=courseService.queryByCourseId(CId);
        //根据课程Id搜索相应的目录
        List<CourseList> courseLists=courseListService.queryDirectoryByCourseId(CId);
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
        model.addAttribute("teacher",teacher);
        model.addAttribute("totalTime",timeInfo);
        model.addAttribute("course",course);
        model.addAttribute("courseLists",courseLists);
        return "course/class-details";
    }

    @GetMapping("/updatePublishState")
    @ResponseBody
    @ApiOperation("修改线上课程的发布状态")
    public ResultVO updatePublishState(@ApiParam("课程Id")int courseId,HttpServletRequest request){
        Course course=courseService.queryByCourseId(courseId);
        Boolean publishState=course.getPublishState();
        //判断是否要发布课程
        if(!publishState){
            List<CourseList> courseLists=courseListService.queryByCourseId(courseId);
            if(courseLists.size()==0){
                return ResultVO.error("此课程未添加目录不能发布");
            }
        }
        Manager manager=(Manager) request.getSession().getAttribute("manager");
        int n=courseService.updatePublishState(courseId,manager.getManagerName(), manager.getManagerId());
        if(n==1){
            //如果课程发布状态改变true，则向课程审核表中添加一条记录
            //如果为false,则关闭了课程
            publishState=courseService.queryByCourseId(courseId).getPublishState();
            if(publishState){
                int offCourse=courseService.updateOFFAuditState(courseId,0);
                if (offCourse==1){
                    //添加一条相应的课程审核信息
                    CourseAudit courseAudit=new CourseAudit();
                    courseAudit.setCourseId(courseId);
                    courseAudit.setCourseName(course.getCourseName());
                    courseAudit.setPublisherId(course.getPublisherId());
                    courseAudit.setPublisher(course.getPublisher());
                    int flag=courseAuditService.addCourseAuditInfo(courseAudit);
                    if(flag==1){
                        return ResultVO.success("修改成功");
                    }
                }
                return ResultVO.error("修改失败");
            }else{
                int i=courseService.updateOFFAuditState(courseId,-1);
                if(i==1){
                    //课程审核表的审核状态应为  3
                    int j=courseAuditService.updateCourseAudit(courseId,3);
                    if (j==1){
                        //关闭轮播图中相应课程宣传图的开启状态
                        int off=bannerService.updateBannerStateByCourseId(courseId);
                        if (off!=0){
                            return ResultVO.success("轮播图关闭成功");
                        }
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
    public ResultVO queryRefuseCause(int courseId){
        String remark=courseAuditService.queryRefuseCause(courseId);
        return ResultVO.success(remark);
    }
    @PostMapping("/courseVIPState")
    @ApiOperation("查询课程VIP状态")
    @ResponseBody
    public ResultVO queryCourseVIPState(@ApiParam("课程Id") int courseId){
        Course course=courseService.queryByCourseId(courseId);
        Boolean vipState=course.getVipState();
        Map<String,Object> vipInfo=new HashMap<>();
        vipInfo.put("vipState",vipState);
        return ResultVO.success("成功",vipInfo);
    }
}
