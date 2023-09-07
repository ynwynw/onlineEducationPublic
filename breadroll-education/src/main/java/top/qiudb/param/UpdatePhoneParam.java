package top.qiudb.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/4 8:24
 * @description 绑定手机号参数
 */
@ApiModel("绑定手机号参数")
public class UpdatePhoneParam {
    @ApiModelProperty("手机号")
    public String userPhone;
    @ApiModelProperty("验证码")
    public String checkCode;
}
