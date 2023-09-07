package top.qiudb.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/4 14:12
 * @description 验证邮箱验证码
 */
@ApiModel("验证邮箱验证码是否正确参数")
public class CheckMailCode {
    @ApiModelProperty("邮箱号")
    public String email;
    @ApiModelProperty("验证码")
    public String checkCode;
}
