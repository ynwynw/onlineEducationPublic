package top.qiudb.controller.resource;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.message.MyMessage;
import top.qiudb.pojo.resource.ResourceList;
import top.qiudb.pojo.user.CoinRecord;
import top.qiudb.pojo.user.UserCoin;
import top.qiudb.service.resource.ResourceListService;
import top.qiudb.service.user.CoinRecordService;
import top.qiudb.service.user.MyMessageService;
import top.qiudb.service.user.UserCoinService;
import top.qiudb.util.tools.PropertiesUtil;
import top.qiudb.util.tools.ResultVO;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/16 8:28
 * @description 学习资料
 */
@RestController
@CrossOrigin
@RequestMapping("/course")
@Api(tags = "学习资料接口")
public class ResourceListController {
    @Autowired
    ResourceListService resourceListService;
    @Autowired
    UserCoinService userCoinService;
    @Autowired
    MyMessageService myMessageService;
    @Autowired
    CoinRecordService coinRecordService;

    // 获取所有学习资源
    @GetMapping("/resource/all")
    @ApiOperation("获取所有学习资源")
    public ResultVO queryResource(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(name = "pageSize", defaultValue = "15") int pageSize) {
        List<ResourceList> resourceLists = resourceListService.queryAllResource(pageNum,pageSize);
        Integer total = resourceListService.queryCount();
        HashMap<String, Object> data = new HashMap<>();
        data.put("list",resourceLists);
        data.put("total",total);
        return ResultVO.success("学习资源查询成功",data);
    }

    // 通过资源名称获取学习资源
    @GetMapping("/resource/name")
    @ApiOperation("通过资源名称获取学习资源")
    public ResultVO queryResourceByName(@RequestParam(name = "resourceName") String resourceName,
                                    @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                    @RequestParam(name = "pageSize", defaultValue = "15") int pageSize) {
        List<ResourceList> resourceLists = resourceListService.queryAllResourceByName(resourceName,pageNum,pageSize);
        Integer total = resourceListService.queryCountByName(resourceName);
        HashMap<String, Object> data = new HashMap<>();
        data.put("list",resourceLists);
        data.put("total",total);
        return ResultVO.success("学习资源查询成功",data);
    }

    // 通过资源名称获取学习资源
    @GetMapping("/resource/exist")
    @ApiOperation("检测学习资源是否存在")
    public ResultVO queryResourceByName(@RequestParam(name = "resourceId") Integer resourceId) {
        if(resourceId==null){ return ResultVO.error("资源Id不存在");}
        ResourceList resourceList = resourceListService.queryResourceById(resourceId);
        if (resourceList.getFileUrl() == null) {return ResultVO.error("资源不存在");}
        //设置文件路径
        File file = new File(PropertiesUtil.getAdminPath() + resourceList.getFileUrl());
        if (!file.exists()) {return ResultVO.error("文件不存在");}
        int userId  = StpUtil.getLoginIdAsInt();
        UserCoin userCoin = userCoinService.queryCoinById(userId);
        if(userCoin.getUserCoin()<resourceList.getBreadCoin()){
            return ResultVO.error("花卷币余额不足");
        }
        //下载学习资料后扣除对应花卷币
        userCoin.setUserCoin(userCoin.getUserCoin()-resourceList.getBreadCoin());
        userCoinService.updateCoin(userCoin);
        //花卷币记录信息
        CoinRecord coinRecord = new CoinRecord();
        coinRecord.setUserId(userId);
        coinRecord.setRecordTime(new Date());
        coinRecord.setVary("-"+resourceList.getBreadCoin());
        coinRecord.setRemark("下载《"+resourceList.getResourceName()+"》扣除"+resourceList.getBreadCoin()+"花卷币");
        coinRecordService.insertRecord(coinRecord);
        //我的消息 中添加注册消息
        MyMessage myMessage = new MyMessage();
        myMessage.setUserId(userId);
        myMessage.setTitle("扣除花卷币");
        myMessage.setContent("您兑换学习资源 <strong>《"+resourceList.getResourceName()+"》</strong> ，扣除"+resourceList.getBreadCoin()+"花卷币");
        myMessage.setUrl("#/breadRollGold");
        myMessage.setReadState(false);
        myMessage.setDeleteState(false);
        myMessageService.insertMyMessage(myMessage);
        return ResultVO.success("满足下载条件");
    }
}
