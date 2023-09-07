package top.qiudb.controller.jurisdiction;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.*;
import top.qiudb.service.jurisdiction.DepartmentService;
import top.qiudb.service.user.ManagerRoleService;
import top.qiudb.service.user.ManagerService;
import top.qiudb.service.user.RoleService;
import top.qiudb.util.redis.RedisUtil;
import top.qiudb.util.tools.ResultVO;
import top.qiudb.util.tools.Tools;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/manager")
@Api(tags="管理员信息操作接口")
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ManagerRoleService managerRoleService;
    @Autowired
    RedisUtil redisUtil;
    @GetMapping("/pageList")
    @ResponseBody
    @ApiOperation("分页查询管理员")
    public ResultVO managerLister(@RequestParam int pageNum, @RequestParam int pageSize){
        List<Manager> pageManagers=managerService.queryPageManager(pageNum,pageSize);
        List<Manager> totalManagers=managerService.queryAllManager();
        Map<String,Object> data=new HashMap<>();
        data.put("list",pageManagers);
        data.put("total",totalManagers.size());
        return ResultVO.success("查询成功",data);
    }

    @GetMapping("/goToEditManager")
    @ApiOperation("跳转到添加/编辑管理员")
    public String goToEditCourse(@ApiParam("管理员Id") int managerId, Model model){
        //查询所有可用部门
        List<Department> departments=departmentService.queryUseAbleDepartment();
        //查询所有可用权限
        List<Role> roles=roleService.queryUseAbleRole();
        List<String> roleTypes= roleService.queryRoleType();
        Map<String,Object> data=new HashMap<>();

        for (String roleType : roleTypes) {
            data.put(roleType,roleService.queryRoleByRoleType(roleType));
        }
        model.addAttribute("data",data);
        model.addAttribute("departments",departments);
        if(managerId!=0){
            Manager manager=managerService.queryManagerById(managerId);
            List<Integer> selfRoles=managerRoleService.queryRoleId(managerId);
            model.addAttribute("manager",manager);
            model.addAttribute("selfRoles",selfRoles);
        }
        //添加时判断名字是否重复
        return "jurisdiction/add-edit-manager";
    }


    @PostMapping("/getRole")
    @ResponseBody
    @ApiOperation("权限级联")
    public ResultVO queryRoleByRoleType(@ApiParam("权限类型")String roleType){
        List<Role> roles= roleService.queryRoleByRoleType(roleType);
        if(roles.size()!=0){
            return ResultVO.success("查询成功",roles);
        }
        return ResultVO.success("");
    }

    @GetMapping("/updateLockState")
    @ResponseBody
    @ApiOperation("改变管理员锁定状态")
    public ResultVO updateLockState(@ApiParam("管理员Id")int managerId, HttpServletRequest request){
        //获取当前管理员Id
        Manager manager=(Manager)request.getSession().getAttribute("manager");
        int presentManagerId=manager.getManagerId();
        if(managerId==1){
            return ResultVO.error("不可对超级管理员进行禁用操作");
        }
        //判断禁用的是否为自己
        if (managerId==presentManagerId){
            return ResultVO.error("不可对自己进行禁用操作");
        }
        int n=managerService.updateLockState(managerId);
        if(n==1){
            return ResultVO.success("修改成功");
        }
        return ResultVO.error("修改失败");
    }

    @PostMapping("/searchManager")
    @ResponseBody
    @ApiOperation("搜索管理员")
    public ResultVO selectManager(@ApiParam("管理员Id")String managerId,
                                       @ApiParam("管理员名称")String managerName,
                                       @ApiParam("性别")String managerGender,
                                       @ApiParam("手机号")String managerPhone,
                                       @ApiParam("部门名称")String departmentName,
                                       @RequestParam int pageNum, @RequestParam int pageSize){

        Manager manager=new Manager();
        if(managerId!=null&&managerId.trim().length()!=0){
            manager.setManagerId(Integer.parseInt(managerId));
        }
        manager.setManagerName(managerName);
        manager.setManagerGender(managerGender);
        manager.setManagerPhone(managerPhone);
        manager.setDepartmentName(departmentName);
        List<Manager> managers=managerService.searchManager(manager,pageNum,pageSize);
        List<Manager> totalManagers=managerService.totalSearchManager(manager);
        Map<String,Object> data=new HashMap<>();
        if((managerId==null || managerId.trim().equals("")) && (departmentName==null || departmentName.trim().equals("")) && (managerName ==null || managerName.trim().equals("")) && (managerGender==null|| managerGender.trim().equals("")) && (managerPhone==null || managerPhone.trim().equals(""))){
            List<Manager> totalManager=managerService.queryAllManager();
            data.put("total",totalManager.size());
        }else{
            data.put("total",totalManagers.size());
        }
        data.put("list",managers);
        if(managers.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("无查询结果",data);
    }

    @PostMapping("/addManager")
    @ResponseBody
    @ApiOperation("添加管理员")
    public ResultVO addManager(@ApiParam("管理员信息") ManagerRoleConn managerRoleConn){
        String mail=managerRoleConn.getManagerAccount();
        String checkCode=managerRoleConn.getCheckCode();
        String code = (String)redisUtil.get(mail);
        //判断账号是否存在
        Manager managerAccountExcise=managerService.queryAccountIsExcise(managerRoleConn.getManagerAccount());
        if(managerAccountExcise!=null){
            return ResultVO.error("账号已经存在");
        }

        if(checkCode==null){
            return ResultVO.error("请填写验证码");
        }
        if(code==null){
            return ResultVO.error("验证码已过期");
        }
        if(!checkCode.equalsIgnoreCase(code)){
            return ResultVO.error("验证码错误");
        }
        redisUtil.del(mail);
        //判断昵称是否存在
        Manager managerNameExcise=managerService.queryNameIsExcise(managerRoleConn.getManagerName());
        if(managerNameExcise!=null){
            return ResultVO.error("昵称已经存在");
        }

        String roleIds=managerRoleConn.getRoleIds();
        roleIds=roleIds.replace("\"","");
        roleIds=roleIds.substring(0,roleIds.length());
        String[] roles=roleIds.split(",");
        //设置manager的值
        Manager manager=new Manager();
        manager.setManagerAccount(managerRoleConn.getManagerAccount());
        manager.setManagerName(managerRoleConn.getManagerName());
        manager.setPassWord(Tools.passwordEncoder(managerRoleConn.getPassWord()));
        manager.setDepartmentId(managerRoleConn.getDepartmentId());
        manager.setDepartmentName(managerRoleConn.getDepartmentName());
        //执行添加
        int n=managerService.addManager(manager);
        if(n==1){
            //管理员添加成功后根据managerAccount获取相应的managerId
            int managerId=manager.getManagerId();
            //根据分配的权限名称  获取相应的权限Id
            List<ManagerRole> managerRoles=new ArrayList<>();
            for (String roleId : roles) {
                ManagerRole managerRole = new ManagerRole();
                managerRole.setManagerId(managerId);
                managerRole.setRoleId(Integer.parseInt(roleId));
                managerRoles.add(managerRole);
            }
            //在managerRole表中给管理员添加相应的权限
            int i=managerRoleService.addRole(managerRoles);
            if(i!=0){
                return ResultVO.success("添加成功",200);
            }
        }
        return ResultVO.error("添加失败",500);
    }

    @PostMapping("/editManagerPower")
    @ResponseBody
    @ApiOperation("编辑管理员权力")
    public ResultVO editManagerPower(@ApiParam("管理员信息")ManagerRoleConn managerRoleConn){
        String roleName=managerRoleConn.getRoleIds();
        roleName=roleName.replace("\"","");
        roleName=roleName.substring(0,roleName.length());
        String[] roleNameArray=roleName.split(",");
        Manager managerInfo=new Manager();
        managerInfo.setManagerId(managerRoleConn.getManagerId());
        managerInfo.setDepartmentName(managerRoleConn.getDepartmentName());
        managerInfo.setDepartmentId(managerRoleConn.getDepartmentId());
        //编辑部门
        int n=managerService.updateManagerDepartment(managerInfo);
        //编辑角色
        if(n==1){
            List<ManagerRole> managerRoles = new ArrayList<>();
            for (String roleId : roleNameArray) {
                ManagerRole managerRole = new ManagerRole();
                managerRole.setManagerId(managerRoleConn.getManagerId());
                managerRole.setRoleId(Integer.parseInt(roleId));
                managerRoles.add(managerRole);
            }
            //部门编辑好之后  修改相应的权限
            //删除之前的权限
            if(managerRoleService.queryByManagerId(managerRoleConn.getManagerId()).size()!=0) {
                int m = managerRoleService.deleteRole(managerRoleConn.getManagerId());
                if (m != 0) {
                    //添加新的权限
                    int i = managerRoleService.addRole(managerRoles);
                    if (i != 0) {
                        return ResultVO.success("修改成功", 200);
                    }
                    return ResultVO.error("修改失败", 500);
                }
                return ResultVO.error("失败", 500);
            }else{
                int i = managerRoleService.addRole(managerRoles);
                if (i != 0) {
                    return ResultVO.success("修改成功", 200);
                }
                return ResultVO.error("修改失败", 500);
            }
        }
        return ResultVO.error("修改失败",500);
    }

    @PostMapping("/changePassWord")
    @ResponseBody
    @ApiOperation("修改管理员密码")
    public ResultVO changePassWord(@ApiParam("管理员账号")String managerAccount,@ApiParam("管理员新密码")String newPassWord){
        Manager manager=managerService.queryAccountIsExcise(managerAccount);
        String passWord= Tools.passwordEncoder(newPassWord);
        if(manager!=null){
            int n= managerService.changePassWord(managerAccount,passWord);
            if(n==1){
                return ResultVO.success("修改成功");
            }
            return ResultVO.error("修改失败");
        }
        return ResultVO.error("输入帐号不存在");
    }
    @GetMapping("/deleteManager")
    @ResponseBody
    @ApiOperation("删除管理员")
    public ResultVO deleteManager(@ApiParam("管理员Id")int managerId){
        int n=managerService.deleteManager(managerId);
        if(n==1){
            return ResultVO.success("删除成功");
        }
        return ResultVO.success("删除失败");
    }
}
