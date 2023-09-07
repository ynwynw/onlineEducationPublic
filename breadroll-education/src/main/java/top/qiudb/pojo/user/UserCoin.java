package top.qiudb.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/6 8:21
 * @description 用户积分表（花卷币）
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCoin {
    private Integer id;         //记录id
    private Integer userId;     //用户ID
    private Integer userCoin;   //花卷币个数
}
