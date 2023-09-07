package top.qiudb.service.message;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.SystemMessageMapper;
import top.qiudb.pojo.message.SystemMessage;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/12 22:12
 * @description 描述
 */
@Service
public class SystemMessageServiceImpl implements SystemMessageService{
    @Autowired
    SystemMessageMapper systemMessageMapper;

    @Override
    public List<SystemMessage> queryAllMessage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return systemMessageMapper.selectAllMessage();
    }

    @Override
    public Integer queryCount() {
        return systemMessageMapper.queryCount();
    }
}
