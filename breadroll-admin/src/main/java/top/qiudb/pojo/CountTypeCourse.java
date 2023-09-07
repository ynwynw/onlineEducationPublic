package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountTypeCourse {
    private String typeName;        //类别名称
    private Integer countType;      //类别数量
}
