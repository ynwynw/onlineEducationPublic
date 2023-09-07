package top.qiudb.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/30 23:04
 * @description 描述
 */
@ApiModel("注册用户参数")
public class LoginParam {
    @ApiModelProperty("帐号")
    public String userAccount;
    @ApiModelProperty("密码")
    public String passWord;
    @ApiModelProperty("验证码Key")
    public String checkCodeKey;
    @ApiModelProperty("验证码")
    public String checkCode;
}
