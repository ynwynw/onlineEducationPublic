package top.qiudb.service.marketing;

import org.apache.ibatis.annotations.Param;
import top.qiudb.pojo.Order;

import java.util.List;

public interface OrderService {
    //分页查询订单
    List<Order> queryPageOrder(int pageNum, int pageSize);
    //查询所有订单 统计数量
    List<Order> queryAllOrder();
    //搜索订单
    List<Order> searchOrder(Order order,int pageNum, int pageSize);
    //搜索订单  统计
    List<Order> totalSearchOrder(Order order);
    //通过用户Id和课程Id查询
    Order queryOrderById(int userId,int courseId);
}
