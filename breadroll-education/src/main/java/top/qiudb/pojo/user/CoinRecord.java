package top.qiudb.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/8 9:40
 * @description 花卷币记录表
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CoinRecord {
    @Id
    private Integer recordId;   //记录id
    private Integer userId;     //用户id
    private Date recordTime;    //添加时间
    private String vary;        //花卷币变化
    private String remark;      //备注、原因
}
