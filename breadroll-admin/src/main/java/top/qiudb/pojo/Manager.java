package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Date;
/**
 *  管理员表
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
    @Id
    private Integer managerId;          //管理员ID
    private String managerAccount;      //帐号
    private String managerName;         //姓名
    private String passWord;            //密码
    private String managerGender;       //性别
    private String managerPhone;        //手机号
    private Date registerTime;          //注册时间
    private String avatarUrl;           //头像
    private Integer departmentId;       //部门ID
    private String departmentName;      //部门名称
    private String idCard;              //身份证
    private Date updateTime;            //更新时间
    private Boolean lockedState;        //是否锁定
    private Boolean deleteState;        //是否删除
}
