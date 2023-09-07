package top.qiudb.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/11 8:42
 * @description 播放记录表
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPlay {
    @Id
    private Integer id;
    private Integer userId;
    private Integer courseId;
    private Integer listId;
    private Double playTime;
}
