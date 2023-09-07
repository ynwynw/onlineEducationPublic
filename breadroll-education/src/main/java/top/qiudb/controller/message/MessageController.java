package top.qiudb.controller.message;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.message.MyMessage;
import top.qiudb.pojo.message.SystemMessage;
import top.qiudb.service.message.SystemMessageService;
import top.qiudb.service.user.MyMessageService;
import top.qiudb.util.tools.ResultVO;

import java.util.HashMap;
import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/12 22:16
 * @description 系统消息
 */
@RestController
@CrossOrigin
@RequestMapping("/message")
@Api(tags = "系统消息接口")
public class MessageController {
    @Autowired
    MyMessageService myMessageService;
    @Autowired
    SystemMessageService systemMessageService;

    // 获取我的消息
    @GetMapping("/my/queryAll")
    @ApiOperation("查询所有我的消息")
    public ResultVO queryMyMessage(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        int userId  = StpUtil.getLoginIdAsInt();
        List<MyMessage> myMessages = myMessageService.queryMessageById(userId,pageNum,pageSize);
        Integer count = myMessageService.queryCount(userId);
        HashMap<String, Object> data = new HashMap<>();
        data.put("list",myMessages);
        data.put("total",count);
        return ResultVO.success("个人消息查询成功",data);
    }


    //单个消息设为已读
    @DeleteMapping("/my/read/{messageId}")
    @ApiOperation("我的消息全部设为已读")
    public ResultVO readMessage(@PathVariable(name = "messageId") Integer messageId) {
        MyMessage myMessage = new MyMessage();
        myMessage.setMessageId(messageId);
        myMessage.setReadState(true);
        Boolean isSuccess = myMessageService.updateMyMessage(myMessage);
        if(isSuccess){
            return ResultVO.success("消息已读");
        }
        return ResultVO.success("设置失败");
    }

    //个人消息设为全部已读
    @PostMapping("/my/readAll")
    @ApiOperation("我的消息全部设为已读")
    public ResultVO readMyMessage() {
        int userId  = StpUtil.getLoginIdAsInt();
        Boolean isSuccess = myMessageService.readAllMessage(userId);
        if(isSuccess){
            return ResultVO.success("全部已读");
        }
        return ResultVO.error("设置失败,请稍后再试");
    }

    //删除我的消息
    @DeleteMapping("/my/clear/{messageId}")
    @ApiOperation("删除我的消息")
    public ResultVO clearMyMessage(@PathVariable(name = "messageId") Integer messageId) {
        MyMessage myMessage = new MyMessage();
        myMessage.setMessageId(messageId);
        myMessage.setDeleteState(true);
        Boolean isSuccess = myMessageService.updateMyMessage(myMessage);
        if(isSuccess){
            return ResultVO.success("清除成功");
        }
        return ResultVO.error("清除失败,请稍后再试");
    }

    // 查询系统消息
    @GetMapping("/system/queryAll")
    @ApiOperation("查询系统消息")
    public ResultVO querySystemMessage(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                       @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        List<SystemMessage> systemMessages = systemMessageService.queryAllMessage(pageNum, pageSize);
        Integer count = systemMessageService.queryCount();
        HashMap<String, Object> data = new HashMap<>();
        data.put("list",systemMessages);
        data.put("total",count);
        return ResultVO.success("系统消息查询成功",data);
    }
}
