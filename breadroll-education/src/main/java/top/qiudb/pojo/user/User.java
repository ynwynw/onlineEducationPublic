package top.qiudb.pojo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/30 17:35
 * @description 普通用户
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private Integer userId;     //用户ID
    private String userAccount; //帐号
    private String userName;    //用户名
    private String passWord;    //密码
    private String userGender;  //性别
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date userBirthday;  //出生日期
    private String IdCard;      //身份证
    private String learnTarget; //学习目标
    private Date registerTime;  //注册时间
    private Date loginTime;     //登录时间
    private String avatarUrl;   //用户头像
    private String backgroundUrl;   //背景图片
    private String description; //个人描述
    private String userPhone;   //用户手机号
    private Date updateTime;    //更新时间
    private Boolean lockedState;    //锁定状态
    private Boolean deleteState;    //删除状态
}
