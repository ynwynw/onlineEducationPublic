package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/25 17:03
 * @description 角色表
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    @Id
    private Integer roleId;         //角色ID
    private String roleName;        //角色名称
    private String roleUrl;         //访问路径
    private String roleType;        //角色类别
    private Date addTime;           //添加时间
    private String roleDescribe;    //角色描述
    private Boolean roleState;      //角色状态
}
