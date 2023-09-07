package top.qiudb.controller.util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.qiudb.param.CheckMailParam;
import top.qiudb.util.code.RandomValidateCodeUtil;
import top.qiudb.util.redis.RedisUtil;
import top.qiudb.util.tools.ResultVO;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/util")
@Api(tags = "工具类接口")
public class UtilController {
    private static final Logger logger = LoggerFactory.getLogger(RandomValidateCodeUtil.class);
    @Autowired
    RedisUtil redisUtil;

    //获取随机图片验证码
    @GetMapping("/checkCode")
    @ApiOperation("获取随机图片验证码")
    public void checkCode(HttpSession session, HttpServletResponse response) {
        try {
            Map<String, Object> map = RandomValidateCodeUtil.createImage();
            //存储验证码
            String code = (String) map.get("code");
            session.setAttribute("checkCode",code);
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
    public ResultVO checkImgCode(String checkCode, HttpSession session) {
        String code =(String) session.getAttribute("checkCode");
        if(checkCode==null){
            return ResultVO.error("请填写验证码");
        }
        if(code==null){
            return ResultVO.error("验证码已过期");
        }

        if(checkCode.equalsIgnoreCase(session.getAttribute("checkCode").toString())){
            return ResultVO.success("验证码正确");
        }
        return ResultVO.error("验证码错误");
    }

    //验证邮箱验证码是否正确
    @PostMapping("/check/mail")
    @ApiOperation("验证邮箱验证码是否正确")
    @ResponseBody
    public ResultVO checkMail(@RequestBody CheckMailParam checkMailParam) {
        String mail=checkMailParam.getMail();
        String checkCode=checkMailParam.getCheckCode();
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
}
