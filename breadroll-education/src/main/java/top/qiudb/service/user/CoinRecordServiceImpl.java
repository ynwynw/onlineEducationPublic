package top.qiudb.service.user;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.CoinRecordMapper;
import top.qiudb.pojo.user.CoinRecord;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/8 10:01
 * @description 描述
 */
@Service
public class CoinRecordServiceImpl implements CoinRecordService{
    @Autowired
    CoinRecordMapper coinRecordMapper;

    @Override
    public Boolean insertRecord(CoinRecord coinRecord) {
        return coinRecordMapper.insertRecord(coinRecord);
    }

    @Override
    public List<CoinRecord> queryRecordById(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return coinRecordMapper.queryRecordById(userId);
    }

    @Override
    public Integer queryCount(Integer userId) {
        return coinRecordMapper.queryCount(userId);
    }
}
