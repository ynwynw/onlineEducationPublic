package top.qiudb.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.CoursePreviewMapper;
import top.qiudb.pojo.course.CoursePreview;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/17 15:14
 * @description 描述
 */
@Service
public class CoursePreviewServiceImpl implements CoursePreviewService{
    @Autowired
    CoursePreviewMapper coursePreviewMapper;

    @Override
    public List<CoursePreview> queryAllCourse() {
        return coursePreviewMapper.selectAllCourse();
    }

    @Override
    public Boolean closeExpiration() {
        return coursePreviewMapper.closeExpiration();
    }
}
