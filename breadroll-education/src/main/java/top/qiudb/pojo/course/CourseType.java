package top.qiudb.pojo.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/9 9:13
 * @description 课程类别
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseType {
    @Id
    private Integer typeId;
    private String typeName;
    private String description;
    private Boolean typeState;
    private Boolean deleteState;
}
