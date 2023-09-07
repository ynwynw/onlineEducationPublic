package top.qiudb.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/30 22:33
 * @description 描述
 */
@ApiModel("图形验证码参数")
public class CheckCodeParam {
    @ApiModelProperty("验证码的校验key")
    public String checkCodeKey;
    @ApiModelProperty("验证码")
    public String checkCode;
}
