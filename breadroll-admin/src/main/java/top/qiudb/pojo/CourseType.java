package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
/**
 * 课程类别表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseType {
    @Id
    private Integer typeId;         //类别Id
    private String typeName;        //类别名称
    private String description;     //类别描述
    private Boolean typeState;      //类别状态
    private Boolean deleteState;    //删除状态
}
