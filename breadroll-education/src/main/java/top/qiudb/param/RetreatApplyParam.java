package top.qiudb.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/22 17:51
 * @description 描述
 */
@ApiModel("申请退出特训班")
public class RetreatApplyParam {
    @ApiModelProperty("报名ID")
    public Integer coachId;
}
