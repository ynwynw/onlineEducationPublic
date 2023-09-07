package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
/**
 * 部门表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    private Integer departmentId;           //部门Id
    private String departmentName;          //部门名称
    private String description;             //部门描述
    private Date updateTime;                //更新时间
    private Boolean departmentState;        //部门状态
    private Boolean deleteState;            //删除状态
}
