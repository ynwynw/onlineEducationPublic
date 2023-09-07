package top.qiudb.service.course;

import top.qiudb.pojo.CoachApply;
import top.qiudb.pojo.CountGroupApply;

import java.util.List;

public interface CoachApplyService {
    //分页查询所有未删除的数据
    List<CoachApply> queryAllPageApply(int courseId,int pageNum, int pageSize);
    //根据报名Id查询
    CoachApply queryCoachApplyById(int coachId);
    //查询所有本课程数据  做统计
    List<CoachApply> queryAllApply(int courseId);
    //改变报名状态
    int updateApplyState(int coachId);
    //拒绝退课
    int refuseReturnApply(int coachId);
    //分组统计特训班报名人数
    List<CountGroupApply> queryCountGroupApply();
    //查询特训班申请退课的用户
    List<CoachApply> queryRetreat();
}
