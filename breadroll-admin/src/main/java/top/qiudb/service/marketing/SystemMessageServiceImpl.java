package top.qiudb.service.marketing;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.SystemMessageMapper;
import top.qiudb.pojo.SystemMessage;

import java.util.List;
@Service
public class SystemMessageServiceImpl implements SystemMessageService{
    @Autowired
    private SystemMessageMapper systemMessageMapper;
    @Override
    public List<SystemMessage> queryPageMessage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return systemMessageMapper.queryPageMessage();
    }

    @Override
    public List<SystemMessage> queryTotalMessage() {
        return systemMessageMapper.queryTotalMessage();
    }

    @Override
    public int addSystemMessage(SystemMessage systemMessage) {
        return systemMessageMapper.addSystemMessage(systemMessage);
    }

    @Override
    public SystemMessage queryMessageById(int messageId) {
        return systemMessageMapper.queryMessageById(messageId);
    }

    @Override
    public int updateSystemMessage(SystemMessage systemMessage) {
        return systemMessageMapper.updateSystemMessage(systemMessage);
    }

    @Override
    public List<SystemMessage> searchSystemMessage(SystemMessage systemMessage,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return systemMessageMapper.searchSystemMessage(systemMessage);
    }

    @Override
    public List<SystemMessage> totalSearchSystemMessage(SystemMessage systemMessage) {
        return systemMessageMapper.totalSearchSystemMessage(systemMessage);
    }

    @Override
    public int deleteSystemMessage(int messageId) {
        return systemMessageMapper.deleteSystemMessage(messageId);
    }
}
