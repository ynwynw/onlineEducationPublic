package top.qiudb.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/11 9:20
 * @description 学习记录表
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudyRecord {
    @Id
    private Integer recordId;
    private Integer userId;
    private Integer courseId;
    private Boolean deleteState;
    private Date updateTime;
}
