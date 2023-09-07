package top.qiudb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.qiudb.pojo.CoursePreview;

import java.util.List;

@Mapper
@Component
public interface CoursePreviewMapper {
    //分页查询所有预告
    List<CoursePreview> pageCoursePreview();
    //查询所有预告  统计数量
    List<CoursePreview> totalCoursePreview();
    //根据Id查询
    CoursePreview queryCoursePreviewById(int previewId);
    //搜索课程预告
    List<CoursePreview> searchCoursePreview(CoursePreview coursePreview);
    //搜索课程预告 统计
    List<CoursePreview> totalSearchCoursePreview(CoursePreview coursePreview);
    //添加课程预告
    int addCoursePreview(CoursePreview coursePreview);
    //修改课程预告
    int updateCoursePreview(CoursePreview coursePreview);
    //修改预告状态
    int updatePreviewState(int previewId);
    //删除课程预告
    int deleteCoursePreview(int previewId);
    //关闭课程预告状态
    int OFFCoursePreview(int previewId);
}
