package top.qiudb.controller.course;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.Article;
import top.qiudb.pojo.CourseType;
import top.qiudb.service.course.CourseTypeService;
import top.qiudb.util.tools.ResultVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/type")
@Api(tags="课程类型操作接口")
public class CourseTypeController {
    @Autowired
    private CourseTypeService courseTypeService;

    @GetMapping("/goToCourseType")
    @ApiOperation("跳转到添加/编辑课程类别")
    public String goToCourseType(@ApiParam("类别Id") int typeId, Model model){
        if(typeId!=0){
            CourseType courseType=courseTypeService.queryTypeById(typeId);
            model.addAttribute("courseType",courseType);
        }
        return "course/add-edit-course-type";
    }


    @GetMapping("/allType")
    @ResponseBody
    @ApiOperation("分页查询所有课程类型")
    public ResultVO queryAllType(@RequestParam int pageNum, @RequestParam int pageSize){
        List<CourseType> courseTypes=courseTypeService.queryAllPageType(pageNum,pageSize);
        List<CourseType> totalTypes=courseTypeService.queryOnlineType();
        int total=totalTypes.size();
        Map<String,Object> data =new HashMap<>();
        data.put("list",courseTypes);
        data.put("total",total);
        return ResultVO.success("查询成功",data);
    }

    @PostMapping("/addType")
    @ResponseBody
    @ApiOperation("添加课程类型")
    public ResultVO addCourseType(@ApiParam("类型信息") CourseType courseType){
        //查询要添加的类别是否存在
        CourseType queryType=courseTypeService.queryTypeIsExist(courseType.getTypeName());
        if(queryType==null){
            int n=courseTypeService.addCourseType(courseType);
            if(n==1){
                return ResultVO.success("添加成功",200);
            }else{
                return ResultVO.error("添加失败",500);
            }
        }else{
            return ResultVO.error("类别已存在",500);
        }
    }

    @PostMapping("/editType")
    @ResponseBody
    @ApiOperation("修改课程类型")
    public ResultVO editCourseType(@ApiParam("类型信息")CourseType courseType){
        //查询除自己以外是否有重复的类型
        CourseType queryExceptSelf=courseTypeService.queryTypeExceptSelf(courseType.getTypeId(),courseType.getTypeName());
        if(queryExceptSelf==null) {
            int n = courseTypeService.updateType(courseType);
            if (n == 1) {
                return ResultVO.success("修改成功", 200);
            } else {
                return ResultVO.error("修改失败", 500);
            }
        }else{
            return ResultVO.error("类别已存在", 500);
        }
    }

    @GetMapping("/delType")
    @ResponseBody
    @ApiOperation("删除课程类型")
    public ResultVO deleteCourseType(@ApiParam("类型ID")int typeId ){
        int n=courseTypeService.deleteType(typeId);
        if (n == 1) {
            return ResultVO.success("删除成功", 200);
        } else {
            return ResultVO.error("删除失败", 500);
        }
    }

    @PostMapping("/selCourseType")
    @ResponseBody
    @ApiOperation("搜索课程类别")
    public ResultVO selectOnlineCourse(@ApiParam("课程类别")String typeName,
                                       @RequestParam int pageNum,
                                       @RequestParam int pageSize){
        CourseType courseType=new CourseType();
        courseType.setTypeName(typeName);
        List<CourseType> courseTypes=courseTypeService.queryTypeByName(courseType ,pageNum,pageSize);
        List<CourseType> totalCourseType=courseTypeService.totalQueryTypeByName(courseType);
        Map<String,Object> data=new HashMap<>();
        if(typeName.trim().equals("")){
            List<CourseType> totalTypes=courseTypeService.queryOnlineType();
            data.put("total",totalTypes.size());
        }else{
            data.put("total",totalCourseType.size());
        }
        data.put("list",courseTypes);
        if(courseTypes.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("无查询结果",data);
    }

    @GetMapping("/updateTypeState")
    @ResponseBody
    @ApiOperation("修改课程类别状态")
    public ResultVO updateTypeState(@ApiParam("类别Id") int typeId){
        int n=courseTypeService.updateTypeState(typeId);
        if(n==1){
            return ResultVO.success("修改成功");
        }
        return ResultVO.error("修改失败");
    }
}
