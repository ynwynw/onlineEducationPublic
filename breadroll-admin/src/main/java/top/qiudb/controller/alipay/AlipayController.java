package top.qiudb.controller.alipay;

import com.alipay.api.AlipayApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import top.qiudb.service.pay.AlipayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.qiudb.util.tools.ResultVO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/alipay")
@Api(tags = "支付宝虚拟支付接口")
public class AlipayController {
    private final AlipayService alipayService;
    public AlipayController(AlipayService alipayService) {
        this.alipayService = alipayService;
    }

    /*
    https://qiustudy.utools.club/alipay/create?orderNo=10060&orderName=花卷商城-华为手机支付订单&payPrice=4000
    */
    @ResponseBody
    @PostMapping(value = "/create")
    @ApiOperation("创建订单")
    public String create(@ApiParam("订单号") @RequestParam("orderNo") String orderNo,
                         @ApiParam("订单名称") @RequestParam("orderName") String orderName,
                         @ApiParam("支付金额") @RequestParam("payPrice") String payPrice) {
        //发起支付
        return alipayService.create(orderNo, orderName, payPrice);
    }


    /*
    https://qiustudy.utools.club/alipay/refund?orderNo=10060&payPrice=4000
     */
    @ResponseBody
    @PostMapping(value = "/refund")
    @ApiOperation("订单退款")
    public ResultVO refund(@ApiParam("订单号") @RequestParam("orderNo") String orderNo,
                         @ApiParam("退款金额") @RequestParam("payPrice") String payPrice) {
        //发起支付
        try {
            alipayService.refund(orderNo, payPrice);
        } catch (AlipayApiException e) {
            log.error("【支付宝支付】退款失败 error={}", e.toString());
            return ResultVO.error("退款失败");
        }
        return ResultVO.success("退款成功");
    }

    @GetMapping(value = "/paySuccess")
    @ApiOperation("支付成功同步回调接口")
    public void success(@RequestParam Map<String, String> map, HttpServletResponse response) {
        try {
            String tradeNo = map.get("out_trade_no");
            System.out.println("订单号：" + tradeNo);
            response.sendRedirect("/paySuccess");
        } catch (IOException e) {
            log.error("支付成功，但重定向失败 error={}", e.toString());
        }
    }


    @ResponseBody
    @PostMapping(value = "/payNotify")
    @ApiOperation("支付成功异步回调接口")
    public void payNotify(@RequestParam Map<String, String> map) {
        String tradeStatus = map.get("trade_status");
        if (tradeStatus.equals("TRADE_SUCCESS")) {
            String payTime = map.get("gmt_payment");
            String tradeNo = map.get("out_trade_no");
            String tradeName = map.get("subject");
            String payAmount = map.get("buyer_pay_amount");
            log.info("[支付成功] {交易时间：{},订单号：{},订单名称：{},交易金额：{}}", payTime, tradeNo, tradeName, payAmount);
        }
    }
}