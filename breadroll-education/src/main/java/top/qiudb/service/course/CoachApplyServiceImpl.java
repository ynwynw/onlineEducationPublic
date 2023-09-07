package top.qiudb.service.course;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.CoachApplyMapper;
import top.qiudb.pojo.course.CoachApply;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/20 21:02
 * @description 描述
 */
@Service
public class CoachApplyServiceImpl implements CoachApplyService{
    @Autowired
    CoachApplyMapper coachApplyMapper;

    @Override
    public List<CoachApply> queryCoachApplyByUserId(Integer userId,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return coachApplyMapper.selectCoachApplyByUserId(userId);
    }

    @Override
    public Integer queryCountByUserId(Integer userId) {
        return coachApplyMapper.queryCountByUserId(userId);
    }

    @Override
    public CoachApply queryOnlyCoachApply(Integer userId, Integer courseId) {
        return coachApplyMapper.selectOnlyCoachApply(userId,courseId);
    }

    @Override
    public CoachApply queryCoachApplyById(Integer coachId) {
        return coachApplyMapper.selectCoachApplyById(coachId);
    }

    @Override
    public Boolean addCoachApply(CoachApply coachApply) {
        return coachApplyMapper.insertCoachApply(coachApply);
    }

    @Override
    public Boolean updateCoachApply(CoachApply coachApply) {
        return coachApplyMapper.updateCoachApply(coachApply);
    }

    @Override
    public Boolean startedClasses() {
        return coachApplyMapper.startedClasses();
    }
}
