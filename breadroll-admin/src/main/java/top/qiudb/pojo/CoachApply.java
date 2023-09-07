package top.qiudb.pojo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * 特训班报名表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachApply {
    @Id
    private Integer coachId;    //报名Id
    private Integer courseId;   //课程Id
    private String courseName;  //课程名称
    private Integer userId;     //用户Id
    private String userName;    //用户姓名
    private String userPhone;   //用户电话
    private String userEmail;   //用户邮箱
    private Integer applyState;  //申请状态
    private Boolean deleteState;  //删除状态
}
