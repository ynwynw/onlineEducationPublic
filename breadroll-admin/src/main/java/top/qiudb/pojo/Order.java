package top.qiudb.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
/**
 * 订单表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private Integer orderId;        //订单Id
    private String orderNo;        //订单编号
    private String orderName;       //订单名称
    private Integer userId;         //用户Id
    private String userAccount;     //用户帐号
    private String userName;        //用户名称
    private Date createTime;        //创建时间
    private Integer courseId;       //课程Id
    private String courseName;      //课程名称
    private Integer payPrice;       //支付价格
    private String orderState;     //订单状态
}
