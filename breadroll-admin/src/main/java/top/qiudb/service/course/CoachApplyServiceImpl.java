package top.qiudb.service.course;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.CoachApplyMapper;
import top.qiudb.pojo.CoachApply;
import top.qiudb.pojo.CountGroupApply;

import java.util.List;
@Service
public class CoachApplyServiceImpl implements CoachApplyService{
    @Autowired
    private CoachApplyMapper coachApplyMapper;
    @Override
    public List<CoachApply> queryAllPageApply(int courseId,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return coachApplyMapper.queryAllPageApply(courseId);
    }

    @Override
    public CoachApply queryCoachApplyById(int coachId) {
        return coachApplyMapper.queryCoachApplyById(coachId);
    }

    @Override
    public int updateApplyState(int coachId) {
        return coachApplyMapper.updateApplyState(coachId);
    }

    @Override
    public int refuseReturnApply(int coachId) {
        return coachApplyMapper.refuseReturnApply(coachId);
    }

    @Override
    public List<CountGroupApply> queryCountGroupApply() {
        return coachApplyMapper.queryCountGroupApply();
    }

    @Override
    public List<CoachApply> queryAllApply(int courseId) {
        return coachApplyMapper.queryAllApply(courseId);
    }

    @Override
    public List<CoachApply> queryRetreat() {
        return coachApplyMapper.queryRetreat();
    }
}
