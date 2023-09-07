package top.qiudb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.qiudb.pojo.SystemMessage;

import java.util.List;

@Mapper
@Component
public interface SystemMessageMapper {
    //分页查询公告
    List<SystemMessage> queryPageMessage();
    //查询所有公告 统计数量
    List<SystemMessage> queryTotalMessage();
    //添加公告
    int addSystemMessage(SystemMessage systemMessage);
    //根据Id查询公告
    SystemMessage queryMessageById(int messageId);
    //编辑公告
    int updateSystemMessage(SystemMessage systemMessage);
    //分页搜索公告
    List<SystemMessage> searchSystemMessage(SystemMessage systemMessage);
    //分页搜索公告  统计
    List<SystemMessage> totalSearchSystemMessage(SystemMessage systemMessage);
    //根据Id删除公告
    int deleteSystemMessage(int messageId);
}
