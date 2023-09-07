package top.qiudb.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePreviewParam {
    @Id
    private Integer previewId;          //预告Id
    private String courseName;          //课程名称
    private String description;         //课程介绍
    private String startTime;             //开课时间
    private String timeLength;          //预计时长
    private Integer teacherId;          //讲师Id
    private String teacherName;         //讲师姓名
    private String teacherDes;          //讲师介绍
    private Boolean previewState;       //预告状态
    private Boolean deleteState;        //预告状态
}
