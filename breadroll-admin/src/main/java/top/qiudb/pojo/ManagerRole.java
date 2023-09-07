package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/25 17:25
 * @description 管理员-角色关联表
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ManagerRole {
    @Id
    private Integer id;             //ID
    private Integer managerId;      //管理员ID
    private Integer roleId;         //角色ID
}
