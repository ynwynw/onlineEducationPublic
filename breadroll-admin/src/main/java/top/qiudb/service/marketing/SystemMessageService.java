package top.qiudb.service.marketing;

import top.qiudb.pojo.SystemMessage;

import java.util.List;

public interface SystemMessageService {
    //分页查询公告
    List<SystemMessage> queryPageMessage(int pageNum, int pageSize);
    //查询所有公告 统计数量
    List<SystemMessage> queryTotalMessage();
    //添加公告
    int addSystemMessage(SystemMessage systemMessage);
    //根据Id查询公告
    SystemMessage queryMessageById(int messageId);
    //编辑公告
    int updateSystemMessage(SystemMessage systemMessage);
    //分页搜索公告
    List<SystemMessage> searchSystemMessage(SystemMessage systemMessage,int pageNum, int pageSize);
    //分页搜索公告  统计
    List<SystemMessage> totalSearchSystemMessage(SystemMessage systemMessage);
    //根据Id删除公告
    int deleteSystemMessage(int messageId);
}
