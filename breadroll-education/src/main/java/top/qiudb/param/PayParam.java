package top.qiudb.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/14 18:32
 * @description 描述
 */
@ApiModel("支付宝创建订单参数")
public class PayParam {
    @ApiModelProperty("订单号")
    public String orderNo;
    @ApiModelProperty("订单名称")
    public String orderName;
    @ApiModelProperty("支付金额")
    public String payPrice;
    @ApiModelProperty("序列号")
    public String serialNumber;
    @ApiModelProperty("姓名")
    public String userName;
    @ApiModelProperty("手机号")
    public String userPhone;
    @ApiModelProperty("邮箱")
    public String userEmail;
}
