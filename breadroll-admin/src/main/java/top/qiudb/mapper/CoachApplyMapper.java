package top.qiudb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.qiudb.pojo.CoachApply;
import top.qiudb.pojo.CountGroupApply;

import java.util.List;

@Mapper
@Component
public interface CoachApplyMapper {
    //根据课程Id 分页查询所有未删除的数据
    List<CoachApply> queryAllPageApply(int courseId);
    //根据报名Id查询
    CoachApply queryCoachApplyById(int coachId);
    //查询所有本课程数据  做统计
    List<CoachApply> queryAllApply(int courseId);
    //同意退课 改变报名状态
    int updateApplyState(int coachId);
    //拒绝退课
    int refuseReturnApply(int coachId);
    //分组统计特训班报名人数
    List<CountGroupApply> queryCountGroupApply();
    //查询特训班申请退课的用户
    List<CoachApply> queryRetreat();
}
