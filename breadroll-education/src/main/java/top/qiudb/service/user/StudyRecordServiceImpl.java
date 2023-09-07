package top.qiudb.service.user;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.StudyRecordMapper;
import top.qiudb.pojo.user.StudyRecord;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/11 9:30
 * @description 描述
 */
@Service
public class StudyRecordServiceImpl implements StudyRecordService{
    @Autowired
    StudyRecordMapper studyRecordMapper;

    @Override
    public List<StudyRecord> queryRecordByUserId(Integer userId,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return studyRecordMapper.selectRecordByUserId(userId);
    }

    @Override
    public Boolean addStudyRecord(StudyRecord studyRecord) {
        return studyRecordMapper.insertRecord(studyRecord);
    }

    @Override
    public Boolean updateStudyRecord(StudyRecord studyRecord) {
        return studyRecordMapper.updateRecord(studyRecord);
    }

    @Override
    public Boolean clearAllRecord(Integer userId) {
        return studyRecordMapper.clearAllRecord(userId);
    }

    @Override
    public Boolean queryRecordExist(Integer userId, Integer courseId) {
        return studyRecordMapper.selectRecordExist(userId,courseId);
    }

    @Override
    public Integer queryCount(Integer userId) {
        return studyRecordMapper.queryCount(userId);
    }
}
