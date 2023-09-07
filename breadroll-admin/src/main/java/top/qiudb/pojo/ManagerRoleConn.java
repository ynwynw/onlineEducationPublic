package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Date;
/**
 * 管理和角色的联合表
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerRoleConn {
    @Id
    private Integer managerId;          //管理员ID
    private String managerAccount;      //帐号
    private String managerName;         //姓名
    private String passWord;            //密码
    private Integer departmentId;       //部门ID
    private String departmentName;      //部门名称
    private String roleIds;            //管理员所对应的角色Id
    private String checkCode;
}
