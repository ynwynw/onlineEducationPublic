package top.qiudb.controller.marketing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.Order;
import top.qiudb.service.marketing.OrderService;
import top.qiudb.util.tools.ResultVO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orderInfo")
@Api(tags = "订单信息操作接口")
public class OrderManageController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/pageList")
    @ResponseBody
    @ApiOperation("分页查询所有订单")
    public ResultVO queryPageOrder(@RequestParam int pageNum, @RequestParam int pageSize){
        List<Order> pageOrder= orderService.queryPageOrder(pageNum,pageSize);
        List<Order> totalOrder=orderService.queryAllOrder();
        Map<String,Object> data=new HashMap<>();
        data.put("list",pageOrder);
        data.put("total",totalOrder.size());
        if(totalOrder.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("查询失败",data);
    }

    @PostMapping("/searchOrder")
    @ResponseBody
    @ApiOperation("搜索订单信息")
    public ResultVO searchOrder(@ApiParam("创建时间") String createTime,@RequestParam int pageNum, @RequestParam int pageSize) throws ParseException {
        Order order=new Order();
        if(createTime!=null && createTime.trim().length()!=0){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date date=simpleDateFormat.parse(createTime);
            order.setCreateTime(date);
        }
        List<Order> orders=orderService.searchOrder(order,pageNum,pageSize);
        List<Order> totalOrders=orderService.totalSearchOrder(order);
        Map<String,Object> data=new HashMap<>();
        assert createTime != null;
        if(createTime.trim().equals("")){
            List<Order> totalOrder=orderService.queryAllOrder();
            data.put("total",totalOrder.size());
        }else{
            data.put("total",totalOrders.size());
        }
        data.put("list",orders);
        if(orders.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("无查询结果",data);
    }
}
