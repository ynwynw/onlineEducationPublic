package top.qiudb.service.order;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.OrderMapper;
import top.qiudb.pojo.order.Order;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/20 21:34
 * @description 描述
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<Order> queryOrderByUserId(Integer userId,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return orderMapper.selectOrderByUserId(userId);
    }

    @Override
    public Integer queryCountByUserId(Integer userId) {
        return orderMapper.queryCountByUserId(userId);
    }

    @Override
    public List<Order> queryOrderById(Integer orderId) {
        return orderMapper.selectOrderById(orderId);
    }

    @Override
    public Boolean addOrder(Order order) {
        return orderMapper.insertOrder(order);
    }

    @Override
    public Boolean updateOrder(Order order) {
        return orderMapper.updateOrder(order);
    }
}
