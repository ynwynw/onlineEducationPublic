package top.qiudb.controller.user;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.qiudb.param.UpdatePasswordParam;
import top.qiudb.param.UpdatePhoneParam;
import top.qiudb.pojo.user.User;
import top.qiudb.pojo.vip.UserVip;
import top.qiudb.service.user.UserService;
import top.qiudb.service.vip.UserVipService;
import top.qiudb.util.redis.RedisUtil;
import top.qiudb.util.tools.ResultVO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserVipService userVipService;

    // 注销帐号
    @GetMapping("/logout")
    @ApiOperation("注销登录")
    public ResultVO doLogout() {
        // 当前会话注销登录
        StpUtil.logout();
        return ResultVO.success("退出成功") ;
    }

    //修改密码
    @PostMapping("/updatePassword")
    @ApiOperation("修改密码")
    public ResultVO updatePassword(@RequestBody UpdatePasswordParam updatePasswordParam) {
        String passWord = updatePasswordParam.passWord;
        String newPassWord = updatePasswordParam.newPassWord;
        String checkPassWord = updatePasswordParam.checkPassWord;
        if(passWord==null){
            return ResultVO.error("请填写密码");
        }
        Integer userId  = StpUtil.getLoginIdAsInt();
        User user = userService.queryUserById(userId);
        if(user==null){
            return ResultVO.error("用户不存在");
        }
        if(user.getPassWord().equals(SaSecureUtil.md5BySalt(passWord, user.getUserAccount()))){
            if(newPassWord.equals(checkPassWord)){
                User tempUser = new User();
                tempUser.setUserId(user.getUserId());
                // md5加密
                String pwd = SaSecureUtil.md5BySalt(newPassWord, user.getUserAccount());
                tempUser.setPassWord(pwd);
                tempUser.setUpdateTime(new Date());
                Boolean isSuccess = userService.updateUser(tempUser);
                if(isSuccess){
                    return ResultVO.success("密码修改成功");
                }else{
                    return ResultVO.success("密码修改失败");
                }
            }
            return ResultVO.error("两次输入的密码不一致");
        }
        return ResultVO.error("身份验证失败,当前密码错误");
    }

    //修改密码
    @PostMapping("/updatePhone")
    @ApiOperation("绑定手机号")
    //@RequestHeader("satoken") String token,
    public ResultVO updatePhone(@RequestBody UpdatePhoneParam updatePhoneParam) {
        Integer userId  = StpUtil.getLoginIdAsInt();
        String userPhone = updatePhoneParam.userPhone;
        String checkCode = updatePhoneParam.checkCode;
        if(userPhone==null){
            return ResultVO.error("请填写手机号");
        }
        if(checkCode==null){
            return ResultVO.error("请填写验证码");
        }
        String code = (String)redisUtil.get(userPhone);
        if(code.equalsIgnoreCase(checkCode)){
            User existUser = userService.queryUserByPhone(userPhone);
            if(existUser!=null){
                return ResultVO.error("此手机号已被使用");
            }
            User user = new User();
            user.setUserId(userId);
            user.setUserPhone(userPhone);
            user.setUpdateTime(new Date());
            Boolean isSuccess = userService.updateUser(user);
            if(isSuccess){
                return ResultVO.success("绑定成功");
            }
            return ResultVO.error("绑定失败");
        }
        return ResultVO.error("验证码错误");
    }

    //删除帐号
    // 获取当前用户信息
    @PostMapping("/dropUser")
    @ApiOperation("注销帐号")
    public ResultVO dropUser() {
        Integer userId  = StpUtil.getLoginIdAsInt();
        User user = new User();
        user.setUserId(userId);
        user.setDeleteState(true);
        user.setUpdateTime(new Date());
        Boolean isSuccess = userService.updateUser(user);
        if(isSuccess){
            return ResultVO.success("注销成功");
        }
        return ResultVO.error("注销失败");
    }

    // 获取当前用户信息
    @GetMapping("/getUserInfo")
    @ApiOperation("获取当前用户信息")
    public ResultVO getUserInfo() {
        Integer userId  = StpUtil.getLoginIdAsInt();
        User user = userService.queryUserById(userId);
        if(user!=null){
            return ResultVO.success("查询成功",user);
        }
        return ResultVO.error("查询失败");
    }

    //修改用户信息
    @PostMapping("/updateUserInfo")
    @ApiOperation("更新用户信息")
    public ResultVO updateUserInfo(@RequestBody User user) {
        if(user==null || user.getUserId()==null){
            return ResultVO.error("请完善个人信息");
        }
        Boolean isSuccess = userService.updateUser(user);
        if(isSuccess){
            return ResultVO.success("信息更新成功");
        }
        return ResultVO.error("信息更新失败");
    }


    // 获取当前用户状态
    @PostMapping("/getUserState")
    @ApiOperation("获取当前用户状态")
    public ResultVO getUserState() {
        Map<String,Object> map = new HashMap<>();
        map.put("当前会话是否登录",StpUtil.isLogin());
        map.put("tokenName",StpUtil.getTokenName());
        map.put("tokenValue",StpUtil.getTokenValue());
        // 获取指定token对应的登录id，如果未登录，则返回 null
        map.put("用户id",StpUtil.getLoginIdByToken(StpUtil.getTokenValue()));
        map.put("id",StpUtil.getLoginIdAsInt());
        return ResultVO.success("查询成功",map);
    }

    @PostMapping("/getUserStateByToken")
    @ApiOperation("根据token获取当前用户")
    public ResultVO getUserStateByToken(String token) {
        Map<String,Object> map = new HashMap<>();
        // 获取指定token对应的登录id，如果未登录，则返回 null
        map.put("用户id",StpUtil.getLoginIdByToken(token));
        return ResultVO.success("查询成功",map);
    }

    @PostMapping("/disable")
    @ApiOperation("账号封禁")
    public ResultVO disable(String token) {
        String userId =(String) StpUtil.getLoginIdByToken(token);
        StpUtil.disable(userId, 5);
        Map<String,Object> map = new HashMap<>();
        map.put("用户id",userId);
        // 获取指定账号是否已被封禁 (true=已被封禁, false=未被封禁)
        map.put("是否被封禁",StpUtil.isDisable(userId));
        // 获取指定账号剩余封禁时间，单位：秒
        map.put("剩余封禁时间",StpUtil.getDisableTime(userId));
        //对于正在登录的账号，对其账号封禁时并不会使其立刻注销
        // 先踢下线
        StpUtil.logoutByLoginId(userId);
        // 再封禁账号
        StpUtil.disable(userId,60);
        return ResultVO.success("查询成功",map);
    }

    // 检查用户状态
    @GetMapping("/checkState")
    @ApiOperation("检查用户状态是否被封禁")
    public ResultVO checkState() {
        HashMap<String, Object> data = new HashMap<>();
        Integer userId  = StpUtil.getLoginIdAsInt();
        User user = userService.queryUserById(userId);
        //获取用户会员信息
        UserVip userVip = userVipService.queryUserVipByUserId(userId);
        if(userVip!=null) {
            LocalDate expireTime = userVip.getExpireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            data.put("isVip", true);
            if (LocalDate.now().toEpochDay() - expireTime.toEpochDay() > 0) {
                UserVip userVipTemp = new UserVip();
                userVipTemp.setId(userVip.getId());
                userVipTemp.setDeleteState(true);
                data.put("isVip", false);
                userVipService.updateUserVip(userVipTemp);
            }
        }else{
            data.put("isVip", false);
        }
        data.put("userState",true);
        if(user.getLockedState()){
            //帐号踢下线
            StpUtil.logoutByLoginId(userId);
            data.put("userState",false);
        }
        return ResultVO.success("检测成功",data);
    }
    
    public static void main(String[] args) {
		System.out.println(SaSecureUtil.md5BySalt("123456", "2827724252@qq.com"));
	}
    
}