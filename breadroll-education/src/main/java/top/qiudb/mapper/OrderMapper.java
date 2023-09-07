package top.qiudb.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.course.CoachApply;
import top.qiudb.pojo.order.Order;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/20 21:23
 * @description 订单表
 */
@Repository
@Mapper
public interface OrderMapper {
    //获取用户所有订单
    public List<Order> selectOrderByUserId(Integer userId);

    //获取用户订单总数
    public Integer queryCountByUserId(Integer userId);

    //根据id获取具体订单
    public List<Order> selectOrderById(Integer orderId);

    //添加订单
    public Boolean insertOrder(Order order);

    //更新订单
    public Boolean updateOrder(Order order);
}
