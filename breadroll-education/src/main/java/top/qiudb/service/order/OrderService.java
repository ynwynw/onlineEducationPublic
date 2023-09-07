package top.qiudb.service.order;

import top.qiudb.pojo.order.Order;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/20 21:33
 * @description 订单表
 */
public interface OrderService {
    //获取用户所有订单
    public List<Order> queryOrderByUserId(Integer userId,int pageNum, int pageSize);

    //获取用户订单总数
    public Integer queryCountByUserId(Integer userId);

    //根据id获取具体订单
    public List<Order> queryOrderById(Integer orderId);

    //添加订单
    public Boolean addOrder(Order order);

    //更新订单
    public Boolean updateOrder(Order order);
}
