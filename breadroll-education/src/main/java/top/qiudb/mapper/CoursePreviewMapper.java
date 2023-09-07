package top.qiudb.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.course.CoursePreview;
import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/17 15:03
 * @description 课程预告
 */
@Repository
@Mapper
public interface CoursePreviewMapper {
    //查询所有课程预告
    public List<CoursePreview> selectAllCourse();

    //将课程预告过期的下架
    public Boolean closeExpiration();
}
