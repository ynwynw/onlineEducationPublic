package top.qiudb.controller.sms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import top.qiudb.param.SendCodeMailParam;
import top.qiudb.param.SendCodePhoneParam;
import top.qiudb.param.SendEnrollMailParam;
import top.qiudb.service.mail.IMailService;
import top.qiudb.service.sms.AliYunSmsService;
import top.qiudb.util.redis.RedisUtil;
import top.qiudb.util.tools.ResultVO;
import top.qiudb.util.tools.Tools;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/24 19:59
 * @description 消息通知接口
 */
@RestController
@CrossOrigin
@RequestMapping("/sms")
@Api(tags = "消息通知接口")
public class SMSController {
    private final IMailService mailService;
    private final AliYunSmsService aliYunSmsService;
    private final RedisUtil redisUtil;
    public SMSController(IMailService mailService, AliYunSmsService aliYunSmsService, RedisUtil redisUtil) {
        this.mailService = mailService;
        this.aliYunSmsService = aliYunSmsService;
        this.redisUtil = redisUtil;
    }

    /**
     * 发送简单邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    @PostMapping("/sendSimpleMail")
    @ApiOperation("发送简单邮件")
    public ResultVO sendSimpleMail(@ApiParam("收件人") @RequestParam("to") String to,
                                   @ApiParam("主题") @RequestParam("subject") String subject,
                                   @ApiParam("内容") @RequestParam("content") String content) {
        mailService.sendSimpleMail(to,subject,content);
        return ResultVO.success("邮件发送成功");
    }

    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    @PostMapping("/sendHtmlMail")
    @ApiOperation("发送HTML邮件")
    public ResultVO sendHtmlMail(@ApiParam("收件人") @RequestParam("to") String to,
                                   @ApiParam("主题") @RequestParam("subject") String subject,
                                   @ApiParam("内容") @RequestParam("content") String content) {
        System.out.println(to);
        System.out.println(subject);
        System.out.println(content);
        boolean send = mailService.sendHtmlMail(to, subject, content);
        if(send){
            return ResultVO.success("邮件发送成功");
        }
        return ResultVO.success("邮件发送失败");
    }

    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件
     */
    @PostMapping("/sendAttachmentsMail")
    @ApiOperation("发送带附件的邮件")
    public ResultVO sendAttachmentsMail(@ApiParam("收件人") @RequestParam("to") String to,
                                 @ApiParam("主题") @RequestParam("subject") String subject,
                                 @ApiParam("内容") @RequestParam("content") String content,
                                 @ApiParam("附件路径") @RequestParam("filePath") String filePath) {
        boolean send = mailService.sendAttachmentsMail(to, subject, content,filePath);
        if(send){
            return ResultVO.success("邮件发送成功");
        }
        return ResultVO.success("邮件发送失败");
    }

    /**
     * 发送注册成功的邮箱
     * @param to 收件人
     * @param userName 用户名
     * @param passWord 密码
     */
    @PostMapping("/sendRegisterMail")
    @ApiOperation("发送注册成功邮箱信息")
    public ResultVO sendRegisterMail(@ApiParam("收件人") @RequestParam("to") String to,
                                     @ApiParam("用户名") @RequestParam("userName") String userName,
                                     @ApiParam("密码") @RequestParam("passWord") String passWord) {
        Map<String,Object> data = new HashMap<>();
        data.put("account",to);
        data.put("name",userName);
        data.put("password",passWord);
        data.put("date",new Date());
        data.put("year", Calendar.getInstance().get(Calendar.YEAR));
        boolean send = mailService.sendTemplateMail(to, "注册成功", "mail/register", data);
        if(send){
            return ResultVO.success("邮件发送成功");
        }
        return ResultVO.success("邮件发送失败");
    }

    /**
     * 发送操作验证码的邮箱
     * @param sendCodeMailParam 收件人参数
     *
     */
    @PostMapping("/sendCodeMail")
    @ApiOperation("发送操作验证码的邮箱")
    public ResultVO sendCodeMail(@RequestBody SendCodeMailParam sendCodeMailParam) {
        String to = sendCodeMailParam.to;
        String userName = sendCodeMailParam.userName;
        Map<String,Object> data = new HashMap<>();
        String code = Tools.randomCode();
        redisUtil.set(to,code,60*10);
        data.put("name",userName);
        data.put("code",code);
        data.put("date",new Date());
        data.put("year", Calendar.getInstance().get(Calendar.YEAR));
        boolean send = mailService.sendTemplateMail(to, "操作验证码", "mail/code", data);
        if(send){
            return ResultVO.success("邮件发送成功");
        }
        return ResultVO.success("邮件发送失败");
    }

    /**
     * 发送操作验证码的邮箱
     * @param sendCodePhoneParam 手机号
     */
    @PostMapping("/phoneCheckCode")
    @ApiOperation("发送短信验证码")
    public ResultVO phoneCheckCode(@RequestBody SendCodePhoneParam sendCodePhoneParam) {
        String phone = sendCodePhoneParam.phone;
        String code = Tools.randomCode();
        redisUtil.set(phone,code,60*10);
        boolean sendSms = aliYunSmsService.sendSms(phone, code);
        if(sendSms){
            return ResultVO.success("手机短信发送成功");
        }
        return ResultVO.success("手机短信发送失败");
    }
}
