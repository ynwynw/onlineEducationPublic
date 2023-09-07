package top.qiudb.pojo.vip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/14 20:29
 * @description 用户VIP
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserVip {
    @Id
    private Integer id;
    private Integer userId;
    private Integer vipId;
    private Date openTime;
    private Date expireTime;
    private Boolean deleteState;
}
