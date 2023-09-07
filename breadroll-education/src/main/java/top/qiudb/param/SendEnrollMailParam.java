package top.qiudb.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/30 22:41
 * @description 描述
 */
@ApiModel("发送邮箱验证码参数")
public class SendEnrollMailParam {
    @ApiModelProperty("收件人")
    public String to;
    @ApiModelProperty("用户名")
    public String userName;
}
