package top.qiudb.pojo.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/10 8:28
 * @description 课程讲师
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Teacher {
    @Id
    private Integer teacherId;
    private String teacherName;
    private String description;
    private String avatarUrl;
    private String teacherGender;
    private String teacherPhone;
    private String idCard;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date entryTime;
    private Date leaveTime;
    private Boolean teacherState;
    private Boolean deleteState;
}
