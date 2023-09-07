package top.qiudb.pojo.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/10 8:18
 * @description 课程目录
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseList {
    @Id
    private Integer listId;
    private String listName;
    private Integer courseId;
    private String courseName;
    private String videoUrl;
    private Integer timeMinute;
    private Integer timeSecond;
    private Boolean lockState;

}
