package top.qiudb.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/30 22:41
 * @description 发送手机验证码
 */
@ApiModel("发送手机验证码参数")
public class SendCodePhoneParam {
    @ApiModelProperty("手机号")
    public String phone;
}
