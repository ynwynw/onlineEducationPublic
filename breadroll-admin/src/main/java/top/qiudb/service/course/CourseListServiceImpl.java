package top.qiudb.service.course;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.CourseListMapper;
import top.qiudb.pojo.CourseList;

import java.util.List;
@Service
public class CourseListServiceImpl implements CourseListService{
    @Autowired
    private CourseListMapper courseListMapper;
    @Override
    public List<CourseList> queryAllPageDirectory(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseListMapper.queryAllPageDirectory();
    }

    @Override
    public int addDirectory(CourseList courseList) {
        return courseListMapper.addDirectory(courseList);
    }

    @Override
    public int updateDirectory(CourseList courseList) {
        return courseListMapper.updateDirectory(courseList);
    }

    @Override
    public int deleteDirectory(int listId) {
        return courseListMapper.deleteDirectory(listId);
    }

    @Override
    public CourseList queryListById(int listId) {
        return courseListMapper.queryListById(listId);
    }

    @Override
    public List<CourseList> queryAllDirectory() {
        return courseListMapper.queryAllDirectory();
    }

    @Override
    public List<CourseList> selectDirectory(CourseList courseList, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseListMapper.selectDirectory(courseList);
    }

    @Override
    public List<CourseList> totalSelectDirectory(CourseList courseList) {
        return courseListMapper.totalSelectDirectory(courseList);
    }

    @Override
    public List<CourseList> queryDirectoryByCourseId(int courseId) {
        return courseListMapper.queryDirectoryByCourseId(courseId);
    }

    @Override
    public int deleteCourseDirectory(int courseId) {
        return courseListMapper.deleteCourseDirectory(courseId);
    }

    @Override
    public int updateCourseNameByCourseId(int courseId, String courseName) {
        return courseListMapper.updateCourseNameByCourseId(courseId,courseName);
    }

    @Override
    public List<CourseList> queryByCourseId(int courseId) {
        return courseListMapper.queryByCourseId(courseId);
    }
}
