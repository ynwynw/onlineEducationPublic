package top.qiudb.service.course;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.CoursePreviewMapper;
import top.qiudb.pojo.CoursePreview;

import java.util.List;

@Service
public class CoursePreviewServiceImpl implements CoursePreviewService{
    @Autowired
    private CoursePreviewMapper coursePreviewMapper;
    @Override
    public List<CoursePreview> pageCoursePreview(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return coursePreviewMapper.pageCoursePreview();
    }

    @Override
    public List<CoursePreview> totalCoursePreview() {
        return coursePreviewMapper.totalCoursePreview();
    }

    @Override
    public CoursePreview queryCoursePreviewById(int previewId) {
        return coursePreviewMapper.queryCoursePreviewById(previewId);
    }

    @Override
    public List<CoursePreview> searchCoursePreview(CoursePreview coursePreview,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return coursePreviewMapper.searchCoursePreview(coursePreview);
    }

    @Override
    public List<CoursePreview> totalSearchCoursePreview(CoursePreview coursePreview) {
        return coursePreviewMapper.totalSearchCoursePreview(coursePreview);
    }

    @Override
    public int addCoursePreview(CoursePreview coursePreview) {
        return coursePreviewMapper.addCoursePreview(coursePreview);
    }

    @Override
    public int updateCoursePreview(CoursePreview coursePreview) {
        return coursePreviewMapper.updateCoursePreview(coursePreview);
    }

    @Override
    public int updatePreviewState(int previewId) {
        return coursePreviewMapper.updatePreviewState(previewId);
    }

    @Override
    public int deleteCoursePreview(int previewId) {
        return coursePreviewMapper.deleteCoursePreview(previewId);
    }

    @Override
    public int OFFCoursePreview(int previewId) {
        return coursePreviewMapper.OFFCoursePreview(previewId);
    }
}
