package top.qiudb.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.course.CourseType;
import top.qiudb.pojo.user.CoinRecord;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/9 9:15
 * @description 课程类别
 */
@Repository
@Mapper
public interface CourseTypeMapper {
    //查询所有课程类别
    public List<CourseType> selectAllType();
}
