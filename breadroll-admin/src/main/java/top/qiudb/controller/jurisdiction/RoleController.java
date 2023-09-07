package top.qiudb.controller.jurisdiction;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.Role;
import top.qiudb.service.user.RoleService;
import top.qiudb.util.tools.ResultVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
@Api(tags="权限信息操作接口")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/pageList")
    @ResponseBody
    @ApiOperation("分页查询所有权限")
    public ResultVO queryPageListRole(@RequestParam int pageNum, @RequestParam int pageSize){
        List<Role> pageRoleList=roleService.queryPageRole(pageNum,pageSize);
        List<Role> totalRole=roleService.queryAllRole();
        Map<String,Object> data=new HashMap<>();
        data.put("total",totalRole.size());
        data.put("list",pageRoleList);
        return ResultVO.success("查询成功",data);
    }

    @PostMapping("/searchRole")
    @ResponseBody
    @ApiOperation("搜索权限")
    public ResultVO searchRole(@ApiParam("权限Id")String roleId,@ApiParam("权限名称")String roleName,@RequestParam int pageNum, @RequestParam int pageSize){
        Role role=new Role();
        if(roleId!=null && roleId.trim().length()!=0){
            role.setRoleId(Integer.parseInt(roleId));
        }
        role.setRoleName(roleName);
        List<Role> roles= roleService.searchRole(role,pageNum,pageSize);
        List<Role> totalRoles=roleService.totalSearchRole(role);
        Map<String,Object> data=new HashMap<>();
        assert roleId != null;
        if(roleId.trim().equals("") && roleName.trim().equals("")){
            List<Role> totalRole=roleService.queryAllRole();
            data.put("total",totalRole.size());
        }else{
            data.put("total",totalRoles.size());
        }
        data.put("list",roles);
        if(roles.size()==0){
            return ResultVO.error("无查询结果",data);
        }
        return ResultVO.success("查询成功",data);
    }

    @GetMapping("/goToEditRole")
    @ApiOperation("跳转到编辑权限")
    public String goToEditRole(@ApiParam("权限Id")int roleId, Model model){
        Role role=roleService.queryRoleById(roleId);
        model.addAttribute("role",role);
        return "jurisdiction/edit-jurisdiction";
    }

    @PostMapping("/editRole")
    @ResponseBody
    @ApiOperation("编辑权限(只编辑描述)")
    public ResultVO editRole(@ApiParam("权限信息") Role role){
        int n=roleService.updateRole(role);
        if(n==1){
            return  ResultVO.success("修改成功");
        }
        return ResultVO.error("修改失败");
    }

    @GetMapping("/editRoleState")
    @ResponseBody
    @ApiOperation("修改权限状态")
    public ResultVO editRoleState(@ApiParam("权限Id")int roleId){
        int n=roleService.updateRoleState(roleId);
        if(n==1){
            return  ResultVO.success("修改成功");
        }
        return ResultVO.error("修改失败");
    }
}
