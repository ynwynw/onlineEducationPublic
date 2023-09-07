package top.qiudb.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/30 22:58
 * @description 描述
 */
@ApiModel("注册用户参数")
public class RegisterParam {
    @ApiModelProperty("帐号")
    public String userAccount;
    @ApiModelProperty("昵称")
    public String userName;
    @ApiModelProperty("密码")
    public String passWord;
    @ApiModelProperty("邮箱验证码")
    public String checkCode;
}
