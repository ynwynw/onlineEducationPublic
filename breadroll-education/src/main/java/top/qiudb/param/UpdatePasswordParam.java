package top.qiudb.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/4 8:24
 * @description 修改密码参数
 */
@ApiModel("修改密码参数")
public class UpdatePasswordParam {
    @ApiModelProperty("密码")
    public String passWord;
    @ApiModelProperty("新密码")
    public String newPassWord;
    @ApiModelProperty("再次密码")
    public String checkPassWord;
}
