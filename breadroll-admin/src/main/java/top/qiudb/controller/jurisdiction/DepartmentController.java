package top.qiudb.controller.jurisdiction;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.Department;
import top.qiudb.service.jurisdiction.DepartmentService;
import top.qiudb.util.tools.ResultVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/department")
@Api(tags="部门信息操作接口")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @GetMapping("/pageList")
    @ResponseBody
    @ApiOperation("分页查询所有部门信息")
    public ResultVO queryPageList(@RequestParam int pageNum, @RequestParam int pageSize){
        List<Department> pageDepartments=departmentService.queryPageDepartment(pageNum,pageSize);
        List<Department> totalDepartments=departmentService.queryAllDepartment();
        int total=totalDepartments.size();
        Map<String,Object> data=new HashMap<>();
        data.put("list",pageDepartments);
        data.put("total",total);
        return ResultVO.success("查询成功",data);
    }

    @GetMapping("/goToAddEditDepartment")
    @ApiOperation("跳转到添加/编辑部门")
    public String goToEditDepartment(@ApiParam("部门Id") int departmentId, Model model){
        if(departmentId!=0){
            Department department=departmentService.queryDepartmentById(departmentId);
            model.addAttribute("department",department);
        }
        return "jurisdiction/add-edit-department";
    }

    @PostMapping("/addDepartment")
    @ResponseBody
    @ApiOperation("添加部门")
    public ResultVO addDepartment(@ApiParam("部门信息")Department department){
        //查询名称是否已经存在
        Department departmentExcise=departmentService.queryExciseName(department.getDepartmentName());
        if(departmentExcise==null){
            int n=departmentService.addDepartment(department);
            if(n==1){
              return  ResultVO.success("添加成功");
            }
            return ResultVO.error("添加失败");
        }
        return ResultVO.error("部门名称已经存在");
    }

    @PostMapping("/editDepartment")
    @ResponseBody
    @ApiOperation("编辑部门")
    public ResultVO editDepartment(@ApiParam("部门信息")Department department){
        //查询名称是否已经存在
        Department departmentExcise=departmentService.queryExciseNameExceptSelf(department.getDepartmentId(),department.getDepartmentName());
        if(departmentExcise==null){
            int n=departmentService.updateDepartment(department);
            if(n==1){
                return  ResultVO.success("修改成功");
            }
            return ResultVO.error("修改失败");
        }
        return ResultVO.error("部门名称已经存在");
    }

    @GetMapping("/delDepartment")
    @ResponseBody
    @ApiOperation("删除部门")
    public ResultVO editDepartment(@ApiParam("部门Id")int  departmentId){
        int n=departmentService.deleteDepartment(departmentId);
        if(n==1){
            return  ResultVO.success("删除成功");
        }
        return ResultVO.error("删除失败");
    }

    @GetMapping("/updateDepartmentState")
    @ResponseBody
    @ApiOperation("修改部门状态")
    public ResultVO updateDepartmentState(@ApiParam("部门Id")int departmentId){
        int n=departmentService.updateDepartmentState(departmentId);
        if(n==1){
            return  ResultVO.success("修改成功");
        }
        return ResultVO.error("修改失败");
    }

    @PostMapping("/searchDepartment")
    @ResponseBody
    @ApiOperation("搜索部门")
    public ResultVO searchDepartment(@ApiParam("部门Id")String departmentId,@ApiParam("部门名称")String departmentName,@RequestParam int pageNum, @RequestParam int pageSize){
        Department department=new Department();
        if (departmentId!=null && departmentId.trim().length()!=0){
            department.setDepartmentId(Integer.parseInt(departmentId));
        }
        department.setDepartmentName(departmentName);
        List<Department> departments=departmentService.searchDepartment(department,pageNum,pageSize);
        List<Department> totalDepartment=departmentService.totalSearchDepartment(department);
        Map<String,Object> data=new HashMap<>();
        assert departmentId != null;
        if(departmentId.trim().equals("") && departmentName.trim().equals("")){
            List<Department> totalDepartments=departmentService.queryAllDepartment();
            data.put("total",totalDepartments.size());
        }else{
            data.put("total",totalDepartment.size());
        }
        data.put("list",departments);
        if(departments.size()==0){
            return ResultVO.error("无查询结果",data);
        }
        return ResultVO.success("查询成功",data);
    }
}

