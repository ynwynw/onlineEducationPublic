package top.qiudb.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.user.CoinRecord;
import top.qiudb.pojo.message.MyMessage;
import top.qiudb.pojo.user.UserCoin;
import top.qiudb.service.user.CoinRecordService;
import top.qiudb.service.user.MyMessageService;
import top.qiudb.service.user.UserCoinService;
import top.qiudb.util.tools.ResultVO;

import java.util.HashMap;
import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/6 8:38
 * @description 用户积分接口
 */
@RestController
@CrossOrigin
@Api(tags = "用户其它操作接口")
public class UserCoinController {
    @Autowired
    UserCoinService userCoinService;
    @Autowired
    CoinRecordService coinRecordService;

    // 查询用户积分
    @GetMapping("/coin/queryCoin")
    @ApiOperation("查询用户积分")
    public ResultVO queryCoin() {
        int userId  = StpUtil.getLoginIdAsInt();
        UserCoin userCoin = userCoinService.queryCoinById(userId);
        if(userCoin!=null){
            return ResultVO.success("积分查询成功",userCoin.getUserCoin());
        }
        return ResultVO.success("用户积分不存在",0);
    }

    // 查询用户积分记录
    @GetMapping("/coin/queryCoinRecord")
    @ApiOperation("查询用户积分记录")
    public ResultVO queryCoinRecord(@RequestParam int pageNum, @RequestParam int pageSize) {
        if(pageNum==0){ pageNum=1; }
        if(pageSize==0){ pageSize=5; }
        int userId  = StpUtil.getLoginIdAsInt();
        List<CoinRecord> coinRecords = coinRecordService.queryRecordById(userId,pageNum,pageSize);
        Integer count = coinRecordService.queryCount(userId);
        HashMap<String, Object> data = new HashMap<>();
        data.put("list",coinRecords);
        data.put("total",count);
        if(coinRecords!=null){
            return ResultVO.success("积分记录查询成功",data);
        }
        return ResultVO.success("积分记录查询失败",null);
    }
}
