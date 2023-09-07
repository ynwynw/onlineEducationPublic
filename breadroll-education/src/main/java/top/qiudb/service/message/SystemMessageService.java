package top.qiudb.service.message;

import top.qiudb.pojo.message.SystemMessage;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/12 22:11
 * @description 系统消息
 */
public interface SystemMessageService {
    //查询所有消息
    public List<SystemMessage> queryAllMessage(int pageNum, int pageSize);

    //查询总数
    public Integer queryCount();
}
