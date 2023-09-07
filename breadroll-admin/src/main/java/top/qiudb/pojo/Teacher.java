package top.qiudb.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * 讲师表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Id
    private Integer teacherId;            //ID
    private String teacherName;           //讲师姓名
    private String description;           //讲师介绍
    private String avatarUrl;             //头像
    private String teacherGender;         //性别
    private String teacherPhone;          //电话
    private String idCard;                //身份证
   // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date entryTime;               //入职时间
    private Date leaveTime;               //离职时间
    private Boolean teacherState;         //状态
    private Boolean deleteState;          //删除状态
}
