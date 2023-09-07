package top.qiudb.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.User;
import top.qiudb.service.user.UserService;
import top.qiudb.util.tools.ResultVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@Api(tags="用户信息操作接口")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/pageList")
    @ApiOperation("分页查询所有用户")
    @ResponseBody
    public ResultVO pageListUser(@RequestParam int pageNum, @RequestParam int pageSize){
        List<User> users=userService.queryPageUser(pageNum,pageSize);
        List<User> totalUsers=userService.queryAllUser();
        Map<String,Object> data=new HashMap<>();
        data.put("list",users);
        data.put("total",totalUsers.size());
        return ResultVO.success("查询成功",data);
    }

    @GetMapping("/updateLocakState")
    @ApiOperation("修改用户锁定状态")
    @ResponseBody
    public ResultVO pageListUser(@ApiParam("用户Id")int userId){
        int n=userService.updateLockState(userId);
        if(n==1){
            return ResultVO.success("修改成功");
        }
        return ResultVO.error("修改失败");
    }

    @PostMapping("/searchUser")
    @ApiOperation("搜索用户")
    @ResponseBody
    public ResultVO searchUser(@ApiParam("用户Id")String userId,
                                       @ApiParam("用户姓名")String userName,
                                       @ApiParam("性别")String userGender,
                                       @ApiParam("手机号")String userPhone,
                                       @RequestParam int pageNum, @RequestParam int pageSize){
        User user=new User();
        if(userId!=null && userId.trim().length()!=0){
            user.setUserId(Integer.valueOf(userId));
        }
        user.setUserName(userName);
        user.setUserGender(userGender);
        user.setUserPhone(userPhone);
        List<User> users=userService.searchUser(user,pageNum,pageSize);
        List<User> totalUsers=userService.totalSearchUser(user);
        Map<String,Object> data=new HashMap<>();
        assert userId != null;
        if(userId.trim().equals("") && userName.trim().equals("") && userGender.trim().equals("") && userPhone.trim().equals("")){
            List<User> totals=userService.queryAllUser();
            data.put("total",totals.size());
        }else{
            data.put("total",totalUsers.size());
        }
        data.put("list",users);
        if(users.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("无查询结果",data);
    }
}
