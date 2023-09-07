package top.qiudb.controller.course;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.Course;
import top.qiudb.pojo.CourseList;
import top.qiudb.service.course.CourseListService;
import top.qiudb.service.course.CourseService;
import top.qiudb.util.tools.ResultVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/directory")
@Api(tags="课程目录操作接口")
public class CourseListController {
    @Autowired
    private CourseListService courseListService;
    @Autowired
    private CourseService courseService;
    @GetMapping("/goToCourseList")
    @ApiOperation("跳转到添加/编辑课程目录")
    public String goToCourseList(@ApiParam("课程Id") int listId, Model model){
        //查询所有的在线且未发布课程
        List<Course> courses=courseService.queryAllAvalibCourse();
        model.addAttribute("courses",courses);
        if(listId!=0){
            CourseList courseList=courseListService.queryListById(listId);
            model.addAttribute("courseList",courseList);
        }
        return "course/add-edit-directory-list";
    }

    @PostMapping("/addDirectory")
    @ResponseBody
    @ApiOperation("添加目录")
    public ResultVO addDirectory(@ApiParam("添加目录信息") CourseList courseList){
        int n=courseListService.addDirectory(courseList);
        if(n==1){
            return ResultVO.success("添加成功",200);
        }else{
            return ResultVO.error("添加失败",500);
        }
    }

    @PostMapping("/queryAllDirectory")
    @ResponseBody
    @ApiOperation("分页查询所有目录")
    public ResultVO queryAllDirectory(@RequestParam int pageNum, @RequestParam int pageSize){
        List<CourseList> courseLists=courseListService.queryAllPageDirectory(pageNum,pageSize);
        List<CourseList> totalCourse=courseListService.queryAllDirectory();
        int total=totalCourse.size();
        Map<String,Object> data=new HashMap<>();
        data.put("list",courseLists);
        data.put("total",total);
        return ResultVO.success("查询成功",data);
    }

    @PostMapping("/isEdit")
    @ResponseBody
    @ApiOperation("判断是否可以修改目录")
    public ResultVO isEditDirectory(@ApiParam("课程Id")int courseId){
        Course course=courseService.queryByCourseId(courseId);
        if(course.getPublishState()){
            return ResultVO.error("课程已发布，不能修改");
        }
        return ResultVO.success("可以修改");
    }

    @PostMapping("/editDirectory")
    @ResponseBody
    @ApiOperation("修改目录")
    public ResultVO updateDirectory(@ApiParam("修改目录信息")CourseList courseList){

        int n=courseListService.updateDirectory(courseList);
        if(n==1){
            return ResultVO.success("修改成功",200);
        }else{
            return ResultVO.error("修改失败",500);
        }
    }

    @GetMapping("/delDirectory")
    @ResponseBody
    @ApiOperation("删除目录")
    public ResultVO delDirectory(@ApiParam("目录Id")int listId){
        CourseList courseList=courseListService.queryListById(listId);
        Course course=courseService.queryByCourseId(courseList.getCourseId());
        if(course.getPublishState()){
            return ResultVO.error("课程已发布，不能删除");
        }
        int n=courseListService.deleteDirectory(listId);
        if(n==1){
            return ResultVO.success("删除成功",200);
        }else{
            return ResultVO.error("删除失败",500);
        }
    }

    @PostMapping("/selCourseDirectory")
    @ResponseBody
    @ApiOperation("搜索课程目录")
    public ResultVO selectOnlineCourse(@ApiParam("目录Id")String listId,
                                       @ApiParam("目录名称")String listName,
                                       @ApiParam("课程Id")String courseName,
                                       @RequestParam int pageNum, @RequestParam int pageSize){
        CourseList courseList=new CourseList();
        if(listId!=null && listId.trim().length()!=0){
            courseList.setListId(Integer.parseInt(listId));
        }
        courseList.setCourseName(courseName);
        courseList.setListName(listName);
        List<CourseList> courseLists=courseListService.selectDirectory(courseList,pageNum,pageSize);
        List<CourseList> totalCourseLists=courseListService.totalSelectDirectory(courseList);
        Map<String,Object> data=new HashMap<>();
        assert listId != null;
        if(listId.trim().equals("")&&listName.trim().equals("")&&courseName.trim().equals("")){
            List<CourseList> totalCourse=courseListService.queryAllDirectory();
            data.put("total",totalCourse.size());
        }else{
            data.put("total",totalCourseLists.size());
        }
        data.put("list",courseLists);
        if(courseLists.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("无查询结果",data);
    }
}
