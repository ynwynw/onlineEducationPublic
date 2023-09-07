package top.qiudb.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/11 18:00
 * @description 用户课程表
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCourse {
    @Id
    private Integer studyId;
    private Integer userId;
    private Integer courseId;
    private Boolean deleteState;
}
