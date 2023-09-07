package top.qiudb.param;

import io.swagger.annotations.ApiParam;
import lombok.Data;
/**
 * 注册管理员 验证输入的验证码 参数类
 * */
@Data
public class CheckMailParam {
   private String mail;
   private String checkCode;
}
