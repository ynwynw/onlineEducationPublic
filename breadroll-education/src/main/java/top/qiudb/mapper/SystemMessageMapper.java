package top.qiudb.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.message.SystemMessage;
import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/12 22:05
 * @description 系统消息
 */
@Repository
@Mapper
public interface SystemMessageMapper {
    //查询所有消息
    public List<SystemMessage> selectAllMessage();

    //查询总数
    public Integer queryCount();
}
