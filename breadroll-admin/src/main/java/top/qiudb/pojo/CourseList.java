package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
/**
 * 课程目录表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseList {
    @Id
    private Integer listId;         //目录Id
    private String listName;        //目录名称
    private Integer courseId;       //课程Id
    private String courseName;      //课程名称
    private String videoUrl;        //视频url
    private Integer timeMinute;      //视频时长分钟
    private Integer timeSecond;      //视频时长秒
    private Boolean lockState;      //锁定状态
}
