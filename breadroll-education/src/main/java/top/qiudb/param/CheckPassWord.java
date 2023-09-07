package top.qiudb.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/4 9:27
 * @description 验证密码是否正确
 */
@ApiModel("验证密码是否正确参数")
public class CheckPassWord {
    @ApiModelProperty("帐号")
    public String userAccount;
    @ApiModelProperty("密码")
    public String passWord;
}
