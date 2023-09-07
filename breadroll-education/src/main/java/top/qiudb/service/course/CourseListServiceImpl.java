package top.qiudb.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.CourseListMapper;
import top.qiudb.mapper.CourseMapper;
import top.qiudb.pojo.course.CourseList;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/10 8:25
 * @description 描述
 */
@Service
public class CourseListServiceImpl implements CourseListService{
    @Autowired
    CourseListMapper courseListMapper;

    @Override
    public List<CourseList> queryListByCourseId(Integer courseId) {
        return courseListMapper.selectListByCourseId(courseId);
    }

    @Override
    public CourseList queryListById(Integer listId) {
        return courseListMapper.selectListById(listId);
    }
}
