package top.qiudb.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.course.Course;
import top.qiudb.pojo.course.CourseList;
import top.qiudb.pojo.user.StudyRecord;
import top.qiudb.pojo.user.UserCourse;
import top.qiudb.pojo.user.UserPlay;
import top.qiudb.service.course.CourseListService;
import top.qiudb.service.course.CourseService;
import top.qiudb.service.user.StudyRecordService;
import top.qiudb.service.user.UserCourseService;
import top.qiudb.service.user.UserPlayService;
import top.qiudb.util.tools.PropertiesUtil;
import top.qiudb.util.tools.ResultVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/11 9:03
 * @description 用户课程相关接口
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
@Api(tags = "用户课程相关接口")
public class UserCourseController {
    @Autowired
    UserPlayService userPlayService;
    @Autowired
    CourseListService courseListService;
    @Autowired
    CourseService courseService;
    @Autowired
    StudyRecordService studyRecordService;
    @Autowired
    UserCourseService userCourseService;

    //后台服务器地址
    String adminAddress = PropertiesUtil.getAdminAddress();

    // 检测是否存在用户播放记录
    @GetMapping("/playRecord/query")
    @ApiOperation("检测是否存在用户播放记录")
    public ResultVO playRecord(@ApiParam("课程Id") Integer courseId) {
        int userId  = StpUtil.getLoginIdAsInt();
        UserPlay userPlay = userPlayService.queryUserPlay(userId, courseId);
        if(userPlay!=null){
            CourseList courseList = courseListService.queryListById(userPlay.getListId());
            if(courseList!=null){
                Map<String,Object> data = new HashMap<>();
                data.put("listName",courseList.getListName());
                data.put("url",adminAddress+courseList.getVideoUrl());
                data.put("timeLength",userPlay.getPlayTime());
                return ResultVO.success("搜索到课程播放记录",data) ;
            }
        }
        return ResultVO.success("未搜索到课程播放记录") ;
    }

    // 添加用户播放记录
    @PostMapping("/playRecord/add")
    @ApiOperation("添加用户播放记录")
    public ResultVO addPlayRecord(@RequestBody UserPlay userPlay) {
        UserPlay userPlayTemp = userPlayService.queryUserPlay(userPlay.getUserId(), userPlay.getCourseId());
        Boolean isSuccess;
        if(userPlayTemp!=null){
            userPlay.setId(userPlayTemp.getId());
            isSuccess = userPlayService.updatePlayRecord(userPlay);
        }else{
            isSuccess = userPlayService.addPlayRecord(userPlay);
        }
        if(isSuccess){
            return ResultVO.success("用户播放记录保存成功");
        }
        return ResultVO.success("用户播放记录保存失败");
    }

    // 查询用户学习记录
    @GetMapping("/studyRecord/query")
    @ApiOperation("查询用户学习记录")
    public ResultVO studyRecord(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        int userId  = StpUtil.getLoginIdAsInt();
        List<StudyRecord> studyRecords = studyRecordService.queryRecordByUserId(userId,pageNum,pageSize);
        if(studyRecords!=null && studyRecords.size()!=0){
            Integer total = studyRecordService.queryCount(userId);
            List<Integer> courseIds = new ArrayList<>();
            studyRecords.forEach(studyRecord -> courseIds.add(studyRecord.getCourseId()));
            List<Course> courses = courseService.queryCourseByIds(courseIds);
            courses.forEach(course -> course.setCoverUrl(adminAddress+course.getCoverUrl()));
            Map<String, Object> data = new HashMap<>();
            data.put("list",courses);
            data.put("total",total);
            return ResultVO.success("学习记录查询成功",data);
        }
        return ResultVO.success("学习记录查询失败");
    }

    // 清除用户学习记录
    @DeleteMapping("/studyRecord/clear/{courseId}")
    @ApiOperation("清除用户学习记录")
    public ResultVO clearStudyRecord(@PathVariable(name = "courseId") @ApiParam("课程Id") Integer courseId) {
        int userId  = StpUtil.getLoginIdAsInt();
        StudyRecord studyRecord = new StudyRecord();
        studyRecord.setUserId(userId);
        studyRecord.setCourseId(courseId);
        studyRecord.setDeleteState(true);
        Boolean isSuccess = studyRecordService.updateStudyRecord(studyRecord);
        if(isSuccess){
            return ResultVO.success("清除成功");
        }
        return ResultVO.success("清除失败");
    }

    // 清除用户学习记录
    @DeleteMapping("/studyRecord/clearAll")
    @ApiOperation("清除用户全部学习记录")
    public ResultVO clearAllStudyRecord() {
        int userId  = StpUtil.getLoginIdAsInt();
        Boolean isSuccess = studyRecordService.clearAllRecord(userId);
        if(isSuccess){
            return ResultVO.success("清除成功");
        }
        return ResultVO.success("清除失败");
    }


    // 用户加入课程
    @PostMapping("/course/add")
    @ApiOperation("用户加入课程")
    public ResultVO addCourse(@RequestBody UserCourse userCourse) {
        Boolean exist = userCourseService.queryCourseExist(userCourse.getUserId(), userCourse.getCourseId());
        if(exist){
            return ResultVO.error("该课程已添加");
        }
        Boolean isSuccess = userCourseService.addUserCourse(userCourse);
        if(isSuccess){
            return ResultVO.success("课程添加成功");
        }
        return ResultVO.error("课程添加失败");
    }

    // 用户退出课程
    @DeleteMapping("/course/exit/{courseId}")
    @ApiOperation("用户退出课程")
    public ResultVO exitCourse(@PathVariable(name = "courseId") @ApiParam("课程Id") Integer courseId) {
        int userId  = StpUtil.getLoginIdAsInt();
        UserCourse userCourse = new UserCourse();
        userCourse.setUserId(userId);
        userCourse.setCourseId(courseId);
        userCourse.setDeleteState(true);
        Boolean isSuccess = userCourseService.updateUserCourse(userCourse);
        if(isSuccess){
            return ResultVO.success("课程退出成功");
        }
        return ResultVO.error("退出课程失败");
    }

    // 查询用户课程
    @GetMapping("/course/query")
    @ApiOperation("查询用户课程")
    public ResultVO queryCourse(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        int userId  = StpUtil.getLoginIdAsInt();
        List<UserCourse> userCourses = userCourseService.queryCourseByUserId(userId, pageNum, pageSize);
        if(userCourses!=null && userCourses.size()!=0){
            Integer total = userCourseService.queryCount(userId);
            List<Integer> courseIds = new ArrayList<>();
            userCourses.forEach(userCourse -> courseIds.add(userCourse.getCourseId()));
            List<Course> courses = courseService.queryCourseByIds(courseIds);
            courses.forEach(course -> course.setCoverUrl(adminAddress+course.getCoverUrl()));
            Map<String, Object> data = new HashMap<>();
            data.put("list",courses);
            data.put("total",total);
            return ResultVO.success("用户课程查询成功",data);
        }
        return ResultVO.success("用户课程查询失败");
    }


}
