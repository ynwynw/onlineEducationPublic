package top.qiudb.pojo.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/9 9:41
 * @description 课程表
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Course {
    private Integer courseId;
    private String courseName;
    private Integer typeId;
    private String typeName;
    private String description;
    private Integer teacherId;
    private String courseTime;
    private String courseSecond;
    private Boolean vipState;
    private Boolean online;
    private Date putTime;
    private Date updateTime;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date startTime;
    private Integer publisherId;
    private String publisher;
    private Double price;
    private Boolean publishState;
    private Boolean auditState;
    private Boolean deleteState;
    private Integer playCount;
    private String coverUrl;
}
