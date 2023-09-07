package top.qiudb.controller.course;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.banner.Banner;
import top.qiudb.pojo.course.*;
import top.qiudb.pojo.user.StudyRecord;
import top.qiudb.service.banner.BannerService;
import top.qiudb.service.course.*;
import top.qiudb.service.user.StudyRecordService;
import top.qiudb.util.tools.PropertiesUtil;
import top.qiudb.util.tools.ResultVO;

import java.util.*;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/9 9:30
 * @description 课程相关接口
 */
@RestController
@CrossOrigin
@Api(tags = "课程操作相关接口")
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseTypeService courseTypeService;
    @Autowired
    CourseService courseService;
    @Autowired
    CourseListService courseListService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudyRecordService studyRecordService;
    @Autowired
    CoursePreviewService coursePreviewService;
    @Autowired
    BannerService bannerService;
    @Autowired
    CoachApplyService coachApplyService;

    //后台服务器地址
    String adminAddress = PropertiesUtil.getAdminAddress();

    // 首页课程展示信息
    @GetMapping("/queryCourse/home")
    @ApiOperation("首页课程展示信息")
    public ResultVO queryCourseHome() {
        //播放量最高的课程
        List<Course> playCourses = courseService.queryCourseByPlay(1, 8);
        for(Course course : playCourses){
            course.setCoverUrl(adminAddress+course.getCoverUrl());
        }
        //新品课程
        List<Course> newCourses = courseService.queryCourseByTime(1, 8);
        for(Course course : newCourses){
            course.setCoverUrl(adminAddress+course.getCoverUrl());
        }
        //免费专区
        List<Course> freeCourses = courseService.queryCourseByFree(1, 8);
        for(Course course : freeCourses){
            course.setCoverUrl(adminAddress+course.getCoverUrl());
        }
        HashMap<String, Object> playCourse = new HashMap<>();
        HashMap<String, Object> newCourse = new HashMap<>();
        HashMap<String, Object> freeCourse = new HashMap<>();
        List<Object> data = new ArrayList<>();
        playCourse.put("title","精选好课");
        playCourse.put("course",playCourses);
        data.add(playCourse);
        newCourse.put("title","新品推荐");
        newCourse.put("course",newCourses);
        data.add(newCourse);
        freeCourse.put("title","免费专区");
        freeCourse.put("course",freeCourses);
        data.add(freeCourse);
        if(data.size()!=0){
            return ResultVO.success("课程查询成功",data);
        }
        return ResultVO.error("课程数据错误");
    }


    // 查询所有课程类别
    @GetMapping("/queryType")
    @ApiOperation("获取所有课程类别")
    public ResultVO queryType() {
        List<CourseType> courseTypes = courseTypeService.queryAllType();
        if(courseTypes!=null){
            return ResultVO.success("课程类别查询成功",courseTypes);
        }
        return ResultVO.success("课程类别查询失败");
    }

    // 查询所有课程
    @GetMapping("/queryAllCourse")
    @ApiOperation("获取所有课程")
    public ResultVO queryAllCourse(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        List<Course> courses = courseService.queryAllCourse(pageNum,pageSize);
        if(courses!=null){
            courses.forEach(course -> course.setCoverUrl(adminAddress+course.getCoverUrl()));
            Integer total = courseService.queryCount();
            HashMap<String, Object> data = new HashMap<>();
            data.put("list",courses);
            data.put("total",total);
            return ResultVO.success("课程查询成功",data);
        }
        return ResultVO.success("课程查询失败");
    }

    // 根据播放量查询课程
    @GetMapping("/queryCourse/play")
    @ApiOperation("根据播放量查询课程")
    public ResultVO queryCourseByPlay(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        List<Course> courses = courseService.queryCourseByPlay(pageNum,pageSize);
        if(courses!=null){
            courses.forEach(course -> course.setCoverUrl(adminAddress+course.getCoverUrl()));
            Integer total = courseService.queryCount();
            HashMap<String, Object> data = new HashMap<>();
            data.put("list",courses);
            data.put("total",total);
            return ResultVO.success("课程查询成功",data);
        }
        return ResultVO.success("课程查询失败");
    }

    // 根据发布时间查询课程
    @GetMapping("/queryCourse/time")
    @ApiOperation("根据发布时间查询课程")
    public ResultVO queryCourseByTime(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        List<Course> courses = courseService.queryCourseByTime(pageNum,pageSize);
        if(courses!=null){
            courses.forEach(course -> course.setCoverUrl(adminAddress+course.getCoverUrl()));
            Integer total = courseService.queryCount();
            HashMap<String, Object> data = new HashMap<>();
            data.put("list",courses);
            data.put("total",total);
            return ResultVO.success("课程查询成功",data);
        }
        return ResultVO.success("课程查询失败");
    }

    // 查询免费课程
    @GetMapping("/queryCourse/free")
    @ApiOperation("查询免费课程")
    public ResultVO queryCourseByFree(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        List<Course> courses = courseService.queryCourseByFree(pageNum,pageSize);
        if(courses!=null){
            courses.forEach(course -> course.setCoverUrl(adminAddress+course.getCoverUrl()));
            Integer total = courseService.queryCountByFree();
            HashMap<String, Object> data = new HashMap<>();
            data.put("list",courses);
            data.put("total",total);
            return ResultVO.success("课程查询成功",data);
        }
        return ResultVO.success("课程查询失败");
    }

    // 根据类别查询课程
    @GetMapping("/queryCourse/type")
    @ApiOperation("根据类别查询课程")
    public ResultVO queryCourseByType(@RequestParam(name = "typeId")Integer typeId,
                                      @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        List<Course> courses = courseService.queryCourseByType(typeId,pageNum,pageSize);
        if(courses!=null){
            courses.forEach(course -> course.setCoverUrl(adminAddress+course.getCoverUrl()));
            Integer total = courseService.queryCountByType(typeId);
            HashMap<String, Object> data = new HashMap<>();
            data.put("list",courses);
            data.put("total",total);
            return ResultVO.success("课程查询成功",data);
        }
        return ResultVO.success("课程查询失败");
    }

    // 根据课程名模糊查询课程
    @GetMapping("/queryCourse/name")
    @ApiOperation("根据课程名模糊查询课程")
    public ResultVO queryCourseByName(@RequestParam(name = "courseName")String courseName,
                                      @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        List<Course> courses = courseService.queryCourseByName(courseName,pageNum,pageSize);
        if(courses!=null){
            courses.forEach(course -> course.setCoverUrl(adminAddress+course.getCoverUrl()));
            Integer total = courseService.queryCountByName(courseName);
            HashMap<String, Object> data = new HashMap<>();
            data.put("list",courses);
            data.put("total",total);
            return ResultVO.success("课程查询成功",data);
        }
        return ResultVO.success("课程查询失败");
    }

    // 根据课程id查询课程
    @GetMapping("/queryCourse/id")
    @ApiOperation("根据课程id查询课程")
    public ResultVO queryCourseById(Integer courseId) {
        Course course = courseService.queryCourseById(courseId);
        if(course!=null){
            course.setCoverUrl(adminAddress+course.getCoverUrl());
            return ResultVO.success("课程查询成功",course);
        }
        return ResultVO.error("课程查询失败");
    }

    // 根据课程id查询特训班
    @GetMapping("/querySpecial/id")
    @ApiOperation("根据课程id查询特训班")
    public ResultVO querySpecialById(Integer courseId) {
        Course course = courseService.querySpecialCourseById(courseId);
        if(course!=null){
            Teacher teacher = teacherService.queryTeacherById(course.getTeacherId());
            teacher.setAvatarUrl(adminAddress + teacher.getAvatarUrl());
            HashMap<String, Object> data = new HashMap<>();
            data.put("course",course);
            data.put("teacher",teacher);
            data.put("applyState",false);
            String TempId =(String) StpUtil.getLoginIdDefaultNull();
            if(TempId!=null) {
                Integer userId = Integer.parseInt(TempId);
                CoachApply coachApply = coachApplyService.queryOnlyCoachApply(userId, courseId);
                if (coachApply != null) {
                    data.put("applyState", true);
                }
            }
            course.setCoverUrl(adminAddress+course.getCoverUrl());
            return ResultVO.success("课程查询成功",data);
        }
        return ResultVO.error("课程查询失败");
    }

    // 查询所有特训班
    @GetMapping("/queryAllSpecialCourse")
    @ApiOperation("获取所有特训班")
    public ResultVO queryAllSpecialCourse(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        List<Course> courses = courseService.queryAllSpecialCourse(pageNum,pageSize);
        if(courses!=null){
            courses.forEach(course -> course.setCoverUrl(adminAddress+course.getCoverUrl()));
            Integer total = courseService.queryCountSpecialCourse();
            HashMap<String, Object> data = new HashMap<>();
            data.put("list",courses);
            data.put("total",total);
            return ResultVO.success("课程查询成功",data);
        }
        return ResultVO.success("课程查询失败");
    }

    // 根据课程id查询课程详细信息（课程信息、目录信息、讲师信息）
    @GetMapping("/queryCourseInfo/id")
    @ApiOperation("根据课程id查询课程详细信息（课程信息、目录信息、讲师信息）")
    public ResultVO queryCourseInfoById(Integer courseId) {
        Course course = courseService.queryCourseById(courseId);
        if(course!=null){
            HashMap<String, Object> data = new HashMap<>();
            course.setCoverUrl(adminAddress+course.getCoverUrl());
            data.put("course",course);
            if(course.getTeacherId()!=null){
                Teacher teacher = teacherService.queryTeacherById(course.getTeacherId());
                teacher.setAvatarUrl(adminAddress+teacher.getAvatarUrl());
                data.put("teacher",teacher);
            }
            if(course.getCourseId()!=null){
                List<CourseList> courseLists = courseListService.queryListByCourseId(course.getCourseId());
                courseLists.forEach(list -> list.setVideoUrl(adminAddress+list.getVideoUrl()));
                data.put("courseList",courseLists);
            }
            //播放量+1
            Course courseTemp = new Course();
            courseTemp.setCourseId(courseId);
            courseTemp.setPlayCount(course.getPlayCount()+1);
            courseService.updateCourse(courseTemp);
            //添加到用户的学习记录
            String TempId =(String) StpUtil.getLoginIdDefaultNull();
            if(TempId!=null){
                Integer userId = Integer.parseInt(TempId);
                //先判断用户的学习记录是否存在此课程
                StudyRecord studyRecord = new StudyRecord();
                studyRecord.setUserId(userId);
                studyRecord.setCourseId(courseId);
                studyRecord.setDeleteState(false);
                if(studyRecordService.queryRecordExist(userId,courseId)){
                    studyRecord.setUpdateTime(new Date());
                    studyRecordService.updateStudyRecord(studyRecord);
                }else{
                    studyRecordService.addStudyRecord(studyRecord);
                }
            }
            return ResultVO.success("课程查询成功",data);
        }
        return ResultVO.success("课程查询失败");
    }

    // 随机获取课程
    @GetMapping("/queryCourse/random")
    @ApiOperation("随机获取课程")
    public ResultVO queryCourseRandom(Integer count) {
        Integer total = courseService.queryCount();
        Random random = new Random();
        if(count>total || count<=0){count=total;}
        int pageNum = total/count;
        if(total/count-1>0) {
            pageNum = random.nextInt(total / count - 1) + 1;
        }
        List<Course> courses = courseService.queryAllCourse(pageNum,count);
        if(courses!=null){
            courses.forEach(course -> course.setCoverUrl(adminAddress+course.getCoverUrl()));
            return ResultVO.success("课程查询成功",courses);
        }
        return ResultVO.success("课程查询失败");
    }


    // 获取课程预告
    @GetMapping("/queryCourse/preview")
    @ApiOperation("获取课程预告")
    public ResultVO queryCoursePreview() {
        coursePreviewService.closeExpiration();
        List<CoursePreview> coursePreviews = coursePreviewService.queryAllCourse();
        if(coursePreviews!=null){
            HashMap<String, Object> data = new HashMap<>();
            data.put("list",coursePreviews);
            return ResultVO.success("课程预告查询成功",data);
        }
        return ResultVO.success("课程预告查询失败");
    }
}
