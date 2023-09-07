package top.qiudb.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.course.CoachApply;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/20 20:43
 * @description 特训班报名表
 */
@Repository
@Mapper
public interface CoachApplyMapper {
    //根据用户id获取特训班报名信息
    public List<CoachApply> selectCoachApplyByUserId(Integer userId);

    //获取用户报名信息总数
    public Integer queryCountByUserId(Integer userId);

    //根据用户id和课程id获取
    public CoachApply selectOnlyCoachApply(@Param("userId") Integer userId,@Param("courseId") Integer courseId);

    //根据课程id查询课程
    public CoachApply selectCoachApplyById(Integer coachId);

    //添加报名信息
    public Boolean insertCoachApply(CoachApply coachApply);

    //修改报名信息
    public Boolean updateCoachApply(CoachApply coachApply);

    //将已开课的报名信息 报名状态修改为4 （已开课）
    public Boolean startedClasses();
}
