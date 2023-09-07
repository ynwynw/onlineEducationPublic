package top.qiudb.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.user.StudyRecord;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/11 9:22
 * @description 学习记录表
 */
@Repository
@Mapper
public interface StudyRecordMapper {
    // 通过用户id查询学习记录
    public List<StudyRecord> selectRecordByUserId(Integer userId);

    //判断学习记录是否存在
    public Boolean selectRecordExist(@Param("userId") Integer userId,@Param("courseId") Integer courseId);

    // 添加学习记录
    public Boolean insertRecord(StudyRecord studyRecord);

    // 删除学习记录
    public Boolean updateRecord(StudyRecord studyRecord);

    // 清除用户全部学习记录
    public Boolean clearAllRecord(Integer userId);

    //查询总数
    public Integer queryCount(Integer userId);
}
