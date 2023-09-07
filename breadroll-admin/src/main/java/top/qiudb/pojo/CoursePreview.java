package top.qiudb.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Date;

/**
 * 课程预告表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePreview {
    @Id
    private Integer previewId;          //预告Id
    private String courseName;          //课程名称
    private String description;         //课程介绍
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate startTime;             //开课时间
    private String timeLength;          //预计时长
    private Integer teacherId;          //讲师Id
    private String teacherName;         //讲师姓名
    private String teacherDes;          //讲师介绍
    private Boolean previewState;       //预告状态
    private Boolean deleteState;        //预告状态
}
