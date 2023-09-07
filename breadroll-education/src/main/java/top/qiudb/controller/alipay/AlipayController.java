package top.qiudb.controller.alipay;

import cn.dev33.satoken.stp.StpUtil;
import com.alipay.api.AlipayApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.qiudb.param.PayParam;
import top.qiudb.pojo.course.CoachApply;
import top.qiudb.pojo.course.Course;
import top.qiudb.pojo.message.MyMessage;
import top.qiudb.pojo.order.Order;
import top.qiudb.pojo.user.CoinRecord;
import top.qiudb.pojo.user.User;
import top.qiudb.pojo.user.UserCoin;
import top.qiudb.pojo.vip.UserVip;
import top.qiudb.pojo.vip.Vip;
import top.qiudb.service.course.CoachApplyService;
import top.qiudb.service.course.CourseService;
import top.qiudb.service.mail.IMailService;
import top.qiudb.service.order.OrderService;
import top.qiudb.service.pay.AlipayService;
import top.qiudb.service.user.CoinRecordService;
import top.qiudb.service.user.MyMessageService;
import top.qiudb.service.user.UserCoinService;
import top.qiudb.service.vip.UserVipService;
import top.qiudb.service.vip.VipService;
import top.qiudb.util.redis.RedisUtil;
import top.qiudb.util.tools.PropertiesUtil;
import top.qiudb.util.tools.ResultVO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@CrossOrigin
@RequestMapping("/alipay")
@Api(tags = "支付宝虚拟支付接口")
public class AlipayController {
    @Autowired
    private AlipayService alipayService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    VipService vipService;
    @Autowired
    UserVipService userVipService;
    @Autowired
    UserCoinService userCoinService;
    @Autowired
    CoinRecordService coinRecordService;
    @Autowired
    MyMessageService myMessageService;
    @Autowired
    CourseService courseService;
    @Autowired
    OrderService orderService;
    @Autowired
    CoachApplyService coachApplyService;
    @Autowired
    IMailService mailService;

    /*
    https://qiustudy.utools.club/alipay/create?orderNo=10060&orderName=花卷商城-华为手机支付订单&payPrice=4000
    */
    @ResponseBody
    @PostMapping(value = "/create")
    @ApiOperation("创建订单")
    public ResultVO create(@RequestBody PayParam payParam) {
        //发起支付
        String orderNo = payParam.orderNo;
        String orderName = payParam.orderName;
        String payPrice = payParam.payPrice;
        String serialNumber = payParam.serialNumber;
        if(payParam.userName!=null && payParam.userName.length()!=0){
            User user = new User();
            user.setUserId(StpUtil.getLoginIdAsInt());
            user.setUserName(payParam.userName);
            user.setUserPhone(payParam.userPhone);
            user.setUserAccount(payParam.userEmail);
            String[] s = serialNumber.split("_");
            Integer courseId = Integer.parseInt(s[1]);
            CoachApply coachApply = coachApplyService.queryOnlyCoachApply(user.getUserId(), courseId);
            if(coachApply!=null){
                return ResultVO.error("特训班已经报名成功");
            }
            redisUtil.set(serialNumber,user,60*5);   //5分钟
        }
        redisUtil.set(orderNo,serialNumber,60*5);   //5分钟
        return ResultVO.success("调用成功",alipayService.create(orderNo, orderName, payPrice));
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
            String payAmount = map.get("total_amount");
            String serialNumber =(String) redisUtil.get(tradeNo);
            User user = (User) redisUtil.get(serialNumber);
            if(user!=null){
                //报名特训班的回调接口
                String[] s = serialNumber.split("_");
                Integer courseId = Integer.parseInt(s[1]);
                Course course = courseService.querySpecialCourseById(courseId);
                //添加订单信息
                Order order = new Order();
                order.setOrderNo(tradeNo);
                order.setOrderName("报名"+course.getCourseName());
                order.setUserId(user.getUserId());
                order.setCourseId(course.getCourseId());
                double price = Double.parseDouble(payAmount);
                order.setPayPrice((int) price);
                order.setOrderState("已支付");
                Boolean orderSuccess = orderService.addOrder(order);
                //添加报名信息
                CoachApply coachApply = new CoachApply();
                coachApply.setCourseId(course.getCourseId());
                coachApply.setCourseName(course.getCourseName());
                coachApply.setStartTime(course.getStartTime());
                coachApply.setUserId(user.getUserId());
                coachApply.setUserName(user.getUserName());
                coachApply.setUserPhone(user.getUserPhone());
                coachApply.setUserEmail(user.getUserAccount());
                Boolean coachSuccess = coachApplyService.addCoachApply(coachApply);
                if(orderSuccess && coachSuccess){
                    response.sendRedirect(PropertiesUtil.getCallBack() + "/#/specialDetail?id="+courseId+"&serialNumber=" + serialNumber);
                    //我的消息中添加特训班报名成功通知
                    MyMessage myMessage = new MyMessage();
                    myMessage.setUserId(user.getUserId());
                    myMessage.setTitle("特训班报名成功");
                    myMessage.setContent("恭喜您，<strong>《"+course.getCourseName()+"》</strong>报名成功！");
                    myMessage.setUrl("#/orderCenter");
                    myMessage.setReadState(false);
                    myMessage.setDeleteState(false);
                    myMessageService.insertMyMessage(myMessage);
                    //发送入学通知书
                    Map<String,Object> data = new HashMap<>();
                    data.put("userName",user.getUserName());
                    data.put("courseName",course.getCourseName());
                    data.put("startTime",course.getStartTime());
                    data.put("localDate", new Date());
                    boolean send = mailService.sendTemplateMail(user.getUserAccount(), "在线教育-入学通知书", "mail/admissionNotice", data);
                }else{
                    response.sendRedirect(PropertiesUtil.getCallBack() + "/#/specialDetail?id="+courseId+"&serialNumber=ERROR");
                }
            }else{
                //开通VIP的回调接口
                String[] s = serialNumber.split("_");
                Integer vipId = Integer.parseInt(s[1]);
                Integer userId = Integer.parseInt(s[2]);
                Vip vip = vipService.queryVipById(vipId);
                UserVip userVip = userVipService.queryUserVipByUserId(userId);
                Boolean isSuccess;
                if(userVip==null){
                    userVip = new UserVip();
                    userVip.setUserId(userId);
                    userVip.setVipId(vip.getVipId());
                    userVip.setOpenTime(new Date());
                    if(vip.getTimeLength()==-1){
                        userVip.setExpireTime(new Date(253402214400000L));
                    }else{
                        LocalDate expireDate = LocalDate.now().plusDays(vip.getTimeLength());
                        ZonedDateTime zonedDateTime = expireDate.atStartOfDay(ZoneId.systemDefault());
                        userVip.setExpireTime(Date.from(zonedDateTime.toInstant()));
                    }
                    isSuccess = userVipService.insertUserVip(userVip);
                }else{
                    //获取到期时间
                    LocalDate localDate = userVip.getExpireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    //往后续
                    LocalDate expireDate;
                    if(vip.getTimeLength()==-1){
                        userVip.setExpireTime(new Date(253402214400000L));     //将时间设定到9999年
                    }else if(localDate.isBefore(LocalDate.of(9999, 12, 1))){
                        expireDate = localDate.plusDays(vip.getTimeLength());
                        ZonedDateTime zonedDateTime = expireDate.atStartOfDay(ZoneId.systemDefault());
                        //设置到期时间
                        userVip.setExpireTime(Date.from(zonedDateTime.toInstant()));
                    }
                    Vip vipOld = vipService.queryVipById(userVip.getVipId());
                    //根据时长，判断那个VIP权限大，将权限大的更新为当前VIP  （比如以前是个月会员，后面续费年会员，会员标识应该显示年会员）
                    if(vipOld.getTimeLength()<vip.getTimeLength() && vipOld.getTimeLength()!=-1){
                        userVip.setVipId(vip.getVipId());
                    }
                    if(vip.getTimeLength()==-1){      //永久VIP的权限最大
                        userVip.setVipId(vip.getVipId());
                    }
                    isSuccess = userVipService.updateUserVip(userVip);
                }
                if(isSuccess){
                    response.sendRedirect(PropertiesUtil.getCallBack() + "/#/memberDetails?serialNumber=" + serialNumber);
                    //会员开通成功后赠送对应花卷币
                    UserCoin userCoin = userCoinService.queryCoinById(userId);
                    userCoin.setUserCoin(userCoin.getUserCoin()+vip.getBreadCoin());
                    userCoinService.updateCoin(userCoin);
                    //花卷币记录信息
                    CoinRecord coinRecord = new CoinRecord();
                    coinRecord.setUserId(userId);
                    coinRecord.setRecordTime(new Date());
                    coinRecord.setVary("+"+vip.getBreadCoin());
                    coinRecord.setRemark("开通"+vip.getVipName()+"赠送"+vip.getBreadCoin()+"花卷币");
                    coinRecordService.insertRecord(coinRecord);
                    //我的订单中添加记录
                    Order order = new Order();
                    order.setOrderNo(tradeNo);
                    order.setOrderName("开通" + vip.getVipName());
                    order.setUserId(userId);
                    order.setPayPrice(vip.getPrice());
                    order.setOrderState("已支付");
                    orderService.addOrder(order);
                    //我的消息 中添加开通会员通知
                    MyMessage myMessage = new MyMessage();
                    myMessage.setUserId(userId);
                    myMessage.setTitle("开通"+vip.getVipName()+"成功");
                    myMessage.setContent("恭喜您，"+vip.getVipName()+"开通成功！赠送您"+vip.getBreadCoin()+"花卷币，可到订单中心查看详情！");
                    myMessage.setUrl("#/orderCenter");
                    myMessage.setReadState(false);
                    myMessage.setDeleteState(false);
                    myMessageService.insertMyMessage(myMessage);
                }else{
                    response.sendRedirect(PropertiesUtil.getCallBack() + "/#/memberDetails?serialNumber=ERROR");
                }
            }
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