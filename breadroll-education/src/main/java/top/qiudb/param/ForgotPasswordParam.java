package top.qiudb.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/2 12:55
 * @description 忘记密码参数
 */

@ApiModel("忘记密码参数")
public class ForgotPasswordParam {
    @ApiModelProperty("帐号")
    public String userAccount;
    @ApiModelProperty("密码")
    public String passWord;
    @ApiModelProperty("再次密码")
    public String checkPassWord;
    @ApiModelProperty("邮箱验证码")
    public String checkCode;
}
