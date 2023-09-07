package top.qiudb.pojo.vip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/14 14:56
 * @description VIP详情表
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vip {
    @Id
    private Integer vipId;
    private String vipName;
    private String vipMark;
    private String vipIcon;
    private Integer timeLength;
    private Integer price;
    private Integer breadCoin;
    private Boolean vipState;
    private Boolean deleteState;
}
