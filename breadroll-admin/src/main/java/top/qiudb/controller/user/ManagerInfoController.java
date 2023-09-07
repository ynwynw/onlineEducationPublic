package top.qiudb.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.qiudb.controller.loginInfo.LoginController;
import top.qiudb.pojo.Manager;
import top.qiudb.service.user.ManagerService;
import top.qiudb.util.tools.ResultVO;
import top.qiudb.util.tools.Tools;

import javax.management.MalformedObjectNameException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/managerInfo")
@Api(tags="管理员信息操作接口")
public class ManagerInfoController {
    @Autowired
    private ManagerService managerService;

    @GetMapping("/goToBaseInfo")
    @ApiOperation("跳转到管理员基本信息界面")
    public String goToBaseInfo(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager Manager=(Manager) session.getAttribute("manager");
        //根据昵称获取当前用户信息
        Manager manager=managerService.queryManagerByName(Manager.getManagerName());
        model.addAttribute("manager",manager);
        return "selfInfo/user-setting";
    }
    @GetMapping("/goToChangePassWord")
    @ApiOperation("跳转到管理员修改密码界面")
    public String goToChangePassWord(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager Manager=(Manager) session.getAttribute("manager");
        //根据昵称获取当前用户信息
        Manager manager=managerService.queryManagerByName(Manager.getManagerName());
        model.addAttribute("manager",manager);
        return "selfInfo/user-password";
    }
    @GetMapping("/testPassWord")
    @ApiOperation("验证旧密码是否正确")
    @ResponseBody
    public ResultVO testOldPassWord(String oldPassWord,HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager manager=(Manager) session.getAttribute("manager");
        //加密后的旧密码
        String encryptPassWord=Tools.passwordEncoder(oldPassWord);
        String passWord=managerService.queryPassWord(manager.getManagerName());
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        boolean flag=bCryptPasswordEncoder.matches(oldPassWord,passWord);
        if(!flag){
            return ResultVO.error("旧密码输入错误");
        }
        return ResultVO.success("旧密码输入正确");
    }

    @PostMapping("/editManagerInfo")
    @ResponseBody
    @ApiOperation("编辑管理员信息")
    public ResultVO editManagerInfo(@ApiParam("管理员信息")Manager manager,HttpServletRequest request){
        Manager IsExciseManager=managerService.queryExceptSelfIsExcise(manager.getManagerName(), manager.getManagerId());
        if(IsExciseManager==null){
            Manager isExcisePhone=managerService.queryExceptsPhone(manager.getManagerId(), manager.getManagerPhone());
            if(isExcisePhone==null){
                int n=managerService.updateManagerInfo(manager);
                if(n==1){
                    request.getSession(true).setAttribute("userName",manager.getManagerName());
                    return ResultVO.success("保存成功",200);
                }else{
                    return ResultVO.error("保存失败",500);
                }
            }
            return ResultVO.error("手机号已存在");
        }
        return ResultVO.error("管理员昵称已存在",500);
    }

    @PostMapping("/changePassWord")
    @ResponseBody
    @ApiOperation("修改管理员密码")
    public ResultVO changePassWord(String managerId ,String newPassWord,HttpServletRequest request){
        //加密后的密码
        String passWord= Tools.passwordEncoder(newPassWord);
        int ManagerId=Integer.parseInt(managerId);
        int n=managerService.updatePassWord(ManagerId,passWord);
        if(n==1){
            HttpSession session=request.getSession(true);
            session.removeAttribute("manager");
            return ResultVO.success("修改成功");
        }
        return ResultVO.error("修改失败");
    }
}
