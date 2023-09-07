package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
/**
 * 线上课程审核表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseAudit {
    @Id
    private Integer auditId;                //课程审核Id
    private Integer courseId;               //课程Id
    private String courseName;              //课程名称
    private Integer reviewerId;             //审核人Id
    private String reviewer;                //审核人
    private Integer publisherId;            //发布人Id
    private String publisher;               //发布人姓名
    private Integer auditState;             //审核状态
    private Date auditTime;                 //审核时间
    private Boolean deleteState;            //删除状态
    private String remark;                  //审核备注
}
