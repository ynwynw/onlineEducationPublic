package top.qiudb.controller.util;

import cn.dev33.satoken.secure.SaSecureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.qiudb.param.CheckCodeParam;
import top.qiudb.param.CheckMailCode;
import top.qiudb.param.CheckPassWord;
import top.qiudb.pojo.user.User;
import top.qiudb.service.user.UserService;
import top.qiudb.util.code.RandomValidateCodeUtil;
import top.qiudb.util.redis.RedisUtil;
import top.qiudb.util.tools.ResultVO;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/20 9:26
 * @description 工具类
 */

@Controller
@CrossOrigin
@RequestMapping("/util")
@Api(tags = "工具类接口")
public class UtilController {
    private static final Logger logger = LoggerFactory.getLogger(RandomValidateCodeUtil.class);
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    //获取随机图片验证码
    @GetMapping("/checkCode")
    @ApiOperation("获取随机图片验证码")
    public void checkCode(String checkCodeKey, HttpServletResponse response) {
        try {
            Map<String, Object> map = RandomValidateCodeUtil.createImage();
            //存储验证码
            String code = (String) map.get("code");
            redisUtil.set(checkCodeKey,code,60*2); //2分钟
            //将图片输出给浏览器
            BufferedImage image = (BufferedImage) map.get("image");
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (Exception e) {
            logger.error("获取验证码失败->message:{}",e.getMessage());
        }
    }

    //验证图形验证码是否正确
    @PostMapping("/checkImgCode")
    @ApiOperation("验证图形验证码是否正确")
    @ResponseBody
    public ResultVO checkImgCode(@RequestBody CheckCodeParam checkCodeParam) {
        String checkCodeKey = checkCodeParam.checkCodeKey;
        String checkCode = checkCodeParam.checkCode;
        System.out.println(checkCodeParam.checkCodeKey);
        System.out.println(checkCodeParam.checkCode);
        String code =(String) redisUtil.get(checkCodeKey);
        if(checkCode==null){
            return ResultVO.error("请填写验证码");
        }
        if(code==null){
            return ResultVO.error("验证码已过期");
        }
        if(checkCode.equalsIgnoreCase(code)){
            redisUtil.del(checkCodeKey);
            return ResultVO.success("验证码正确");
        }
        return ResultVO.error("验证码错误");
    }

    //验证邮箱验证码是否正确
    @PostMapping("/check/mail")
    @ApiOperation("验证邮箱验证码是否正确")
    @ResponseBody
    public ResultVO checkMail(@RequestBody CheckMailCode checkMailCode) {
        String mail = checkMailCode.email;
        String checkCode = checkMailCode.checkCode;
        String code = (String)redisUtil.get(mail);
        if(checkCode==null){
            return ResultVO.error("请填写验证码");
        }
        if(code==null){
            return ResultVO.error("验证码已过期");
        }
        if(checkCode.equalsIgnoreCase(code)){
            redisUtil.del(mail);
            return ResultVO.success("验证码正确");
        }
        return ResultVO.error("验证码错误");
    }

    //验证手机验证码是否正确
    @PostMapping("/check/phone")
    @ApiOperation("校验手机验证码是否正确")
    @ResponseBody
    public ResultVO checkPhone(@ApiParam("手机号码")  @RequestParam("phone") String phone,
                               @ApiParam("验证码")  @RequestParam("checkCode") String checkCode) {
        String code = (String)redisUtil.get(phone);
        if(checkCode==null){
            return ResultVO.error("请填写验证码");
        }
        if(code==null){
            return ResultVO.error("验证码已过期");
        }
        if(checkCode.equalsIgnoreCase(code)){
            redisUtil.del(phone);
            return ResultVO.success("验证码正确");
        }
        return ResultVO.error("验证码错误");
    }

    //验证手机验证码是否正确
    @PostMapping("/check/password")
    @ApiOperation("校验密码是否正确")
    @ResponseBody
    public ResultVO checkPassword(@RequestBody CheckPassWord checkPassWord) {
        String userAccount = checkPassWord.userAccount;
        String passWord = checkPassWord.passWord;
        if(userAccount==null){
            return ResultVO.error("请填写帐号");
        }
        User user = userService.queryUserByAccount(userAccount);
        if(user==null){
            return ResultVO.error("帐号不存在");
        }
        if(passWord==null){
            return ResultVO.error("请填写密码");
        }
        String pwd = SaSecureUtil.md5BySalt(passWord, user.getUserAccount());
        if(pwd.equals(user.getPassWord())){
            return ResultVO.success("密码正确");
        }
        return ResultVO.error("身份验证失败，密码错误");
    }
}
