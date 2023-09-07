package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountTeacherCourse {
    private String teacherName;
    private Integer courseCount;
    private Integer playCount;
}
