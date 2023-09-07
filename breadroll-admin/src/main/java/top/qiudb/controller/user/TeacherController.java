package top.qiudb.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.Teacher;
import top.qiudb.service.user.TeacherService;
import top.qiudb.util.tools.ResultVO;

import java.util.*;

@Controller
@RequestMapping("/teacher")
@Api(tags="讲师信息接口")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/goToEditCourse")
    @ApiOperation("跳转到添加/编辑讲师")
    public String goToEditCourse(@ApiParam("课程Id") int teacherId, Model model){
        Teacher teacher=null;
        if (teacherId!=0){
             teacher=teacherService.queryTeacherById(teacherId);
        }
        model.addAttribute("teacher",teacher);
        return "course/add-edit-teacher";
    }

    @GetMapping("/list")
    @ResponseBody
    @ApiOperation("查询所有讲师")
    public ResultVO TeacherList(@RequestParam int pageNum, @RequestParam int pageSize){
        List<Teacher> teachers=teacherService.queryAllTeacher(pageNum,pageSize);
        List<Teacher> totalTeachers=teacherService.totalTeacher();
        int total=totalTeachers.size();
        Map<String,Object> data=new HashMap<>();
        data.put("list",teachers);
        data.put("total",total);
        return ResultVO.success("查询成功",data);
    }
    @PostMapping("/addTeacher")
    @ResponseBody
    @ApiOperation("添加讲师")
    public ResultVO AddTeacher(@ApiParam("讲师信息") Teacher teacher){
        Teacher queryTeacher=teacherService.queryTeacherByPhone(teacher.getTeacherPhone());
        if(queryTeacher!=null){
            return ResultVO.error("手机号已存在，添加失败",500);
        }
        queryTeacher=teacherService.queryTeacherByIdCard(teacher.getIdCard());
        if(queryTeacher!=null){
            return ResultVO.error("身份证号已存在，添加失败",500);
        }
        teacher.setEntryTime(new Date());
        int n=teacherService.addTeacher(teacher);
        if(n==1){
            return ResultVO.success("添加成功",200);
        }else{
            return ResultVO.error("添加失败",500);
        }
    }

    @PostMapping("/editTeacher")
    @ResponseBody
    @ApiOperation("修改讲师信息")
    public ResultVO EditTeacher(@ApiParam("讲师信息") Teacher teacher){
        Teacher queryTeacher=teacherService.queryExceptByPhone(teacher.getTeacherId(),teacher.getTeacherPhone());
        if(queryTeacher!=null){
            return ResultVO.error("手机号已存在，修改失败",500);
        }
        queryTeacher=teacherService.queryExceptByIdCard(teacher.getTeacherId(),teacher.getIdCard());
        if(queryTeacher!=null){
            return ResultVO.error("身份证号已存在，修改失败",500);
        }
        teacher.setEntryTime(new Date());
        int n=teacherService.updateTeacher(teacher);
        if(n==1){
            return ResultVO.success("修改成功",200);
        }else{
            return ResultVO.error("修改失败",500);
        }
    }

    @GetMapping("/delTeacher")
    @ResponseBody
    @ApiOperation("删除讲师")
    public ResultVO deleteTeacher(@ApiParam("讲师ID") int teacherId){
        int n=teacherService.deleteTeacher(teacherId);
        if(n==1){
            return ResultVO.success("删除成功",200);
        }else{
            return ResultVO.error("删除失败",500);
        }
    }

    @PostMapping("/selectTeacher")
    @ResponseBody
    @ApiOperation("搜索讲师")
    public ResultVO selectTeacher(@ApiParam("讲师ID")String teacherId,
                                  @ApiParam("讲师姓名")String teacherName,
                                  @ApiParam("讲师性别") String teacherGender,
                                  @RequestParam int pageNum, @RequestParam int pageSize){
        Map<String,Object> data=new HashMap<>();
        Teacher teacher=new Teacher();
        if(teacherId!=null && teacherId.trim().length()!=0){
            teacher.setTeacherId(Integer.parseInt(teacherId));
        }
        teacher.setTeacherName(teacherName);
        teacher.setTeacherGender(teacherGender);
        List<Teacher> teachers=teacherService.queryTeacher(teacher,pageNum,pageSize);
        List<Teacher> totalQueryTeachers=teacherService.totalQueryTeacher(teacher);
        assert teacherId != null;
        if(teacherId.trim().equals("") && teacherName.trim().equals("") && teacherGender.trim().equals("")){
            List<Teacher> totalTeachers=teacherService.totalTeacher();
            data.put("total",totalTeachers.size());
        }else{
            data.put("total",totalQueryTeachers.size());
        }
        data.put("list",teachers);
        if(teachers.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("无查询结果",data);
    }

    @GetMapping("/updateTeacherState")
    @ResponseBody
    @ApiOperation("修改讲师状态")
    public ResultVO updateTeacherState(@ApiParam("讲师Id") int teacherId){
        int n=teacherService.updateTeacherState(teacherId);
        if(n==1){
            return ResultVO.success("修改成功");
        }
        return ResultVO.error("修改失败");
    }
}
