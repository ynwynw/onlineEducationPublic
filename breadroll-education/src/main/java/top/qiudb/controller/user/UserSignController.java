package top.qiudb.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.user.CoinRecord;
import top.qiudb.pojo.user.UserCoin;
import top.qiudb.service.user.CoinRecordService;
import top.qiudb.service.user.MyMessageService;
import top.qiudb.service.user.UserCoinService;
import top.qiudb.util.redis.RedisUtil;
import top.qiudb.util.redis.UserSign;
import top.qiudb.util.tools.ResultVO;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/6 7:45
 * @description 用户签到
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
@Api(tags = "签到")
public class UserSignController {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserSign userSign;
    @Autowired
    UserCoinService userCoinService;
    @Autowired
    CoinRecordService coinRecordService;
    @Autowired
    MyMessageService myMessageService;

    // 进行签到
    @PostMapping("/sign")
    @ApiOperation("用户签到")
    public ResultVO sign() {
        //每次签到的积分,基础积分20，连续签到次数 * 5，连续签到次数最大为7
        Integer coin=20;
        Boolean isSuccess;
        int userId  = StpUtil.getLoginIdAsInt();
        LocalDate today = LocalDate.now();
        long count = userSign.getContinuousSignCount(userId, today);
        if(count>=7){count=7;}
        if(!userSign.doSign(userId, today)){
            UserCoin userCoin = userCoinService.queryCoinById(userId);
            // 减去第一天的基础积分，后面每连续签到一次，多加5个
            // 第一天签到  20个
            // 第二天签到  25个、第三天 30个，最大到7
            coin +=((int)count-1)*5;
            //更新用户积分
            if(userCoin==null){
                userCoin = new UserCoin();
                userCoin.setUserId(userId);
                userCoin.setUserCoin(coin);
                isSuccess = userCoinService.insertCoin(userCoin);
            }else{
                userCoin.setUserCoin(userCoin.getUserCoin()+coin);
                isSuccess = userCoinService.updateCoin(userCoin);
            }
            //积分是否更新成功
            if(isSuccess){
                CoinRecord coinRecord = new CoinRecord();
                coinRecord.setUserId(userId);
                coinRecord.setRecordTime(new Date());
                coinRecord.setVary("+"+coin);
                coinRecord.setRemark("签到奖励");
                coinRecordService.insertRecord(coinRecord);
                return ResultVO.success("签到成功，花卷币+"+coin);
            }else{
                return ResultVO.error("签到失败");
            }
        }
        return ResultVO.error("今日已签到") ;
    }

    // 检测今日是否签到
    @GetMapping("/checkSign")
    @ApiOperation("检测今日是否签到")
    public ResultVO checkSign() {
        int userId  = StpUtil.getLoginIdAsInt();
        LocalDate today = LocalDate.now();
        //是否签到
        boolean isSign = userSign.checkSign(userId, today);
        //查询是否有新消息
        boolean newMessage = myMessageService.newMessageState(userId);
        HashMap<String, Boolean> data = new HashMap<>();
        data.put("isSign",isSign);
        data.put("newMessage",newMessage);
        return ResultVO.success("查询成功",data) ;
    }

    //检测是否有新消息
    @GetMapping("/check/newMessage")
    @ApiOperation("检测是否有新消息")
    public ResultVO checkNewMessage() {
        int userId  = StpUtil.getLoginIdAsInt();
        //查询是否有新消息
        boolean newMessage = myMessageService.newMessageState(userId);
        return ResultVO.success("查询成功",newMessage) ;
    }

    // 本月签到次数
    @GetMapping("/signCount")
    @ApiOperation("本月签到次数")
    public ResultVO signCount() {
        int userId  = StpUtil.getLoginIdAsInt();
        LocalDate today = LocalDate.now();
        long count = userSign.getSignCount(userId, today);
        return ResultVO.success("本月签到次数",count) ;
    }

    // 本月签到次数
    @GetMapping("/coiledSignCount")
    @ApiOperation("本月连续签到次数")
    public ResultVO coiledSignCount() {
        int userId  = StpUtil.getLoginIdAsInt();
        LocalDate today = LocalDate.now();
        long count = userSign.getContinuousSignCount(userId, today);
        return ResultVO.success("本月连续签到次数",count) ;
    }
}
