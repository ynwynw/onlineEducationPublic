package top.qiudb.service.user;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.MyMessageMapper;
import top.qiudb.pojo.message.MyMessage;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/6 9:37
 * @description 描述
 */
@Service
public class MyMessageServiceImpl implements MyMessageService{
    @Autowired
    MyMessageMapper myMessageMapper;

    @Override
    public Boolean insertMyMessage(MyMessage myMessage) {
        return myMessageMapper.insertMyMessage(myMessage);
    }

    @Override
    public Boolean updateMyMessage(MyMessage myMessage) {
        return myMessageMapper.updateMyMessage(myMessage);
    }

    @Override
    public Boolean readAllMessage(Integer userId) {
        return myMessageMapper.readAllMessage(userId);
    }

    @Override
    public List<MyMessage> queryMessageById(Integer userId,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return myMessageMapper.queryMessageById(userId);
    }

    @Override
    public Integer queryCount(Integer userId) {
        return myMessageMapper.queryCount(userId);
    }

    @Override
    public Boolean newMessageState(Integer userId) {
        return myMessageMapper.newMessageState(userId);
    }
}
