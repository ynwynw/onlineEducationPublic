package top.qiudb.pojo.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/20 21:18
 * @description 订单表
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    @Id
    private Integer orderId;
    private String orderNo;
    private String orderName;
    private Integer userId;
    private Date createTime;
    private Integer courseId;
    private Integer payPrice;
    private String orderState;
}
