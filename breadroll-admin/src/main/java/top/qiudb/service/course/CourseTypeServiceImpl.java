package top.qiudb.service.course;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.CourseTypeMapper;
import top.qiudb.pojo.CourseType;

import java.util.List;

@Service
public class CourseTypeServiceImpl implements CourseTypeService{
    @Autowired
    private CourseTypeMapper courseTypeMapper;
    @Override
    public int addCourseType(CourseType courseType) {
        return courseTypeMapper.addCourseType(courseType);
    }

    @Override
    public CourseType queryTypeIsExist(String typeName) {
        return courseTypeMapper.queryTypeIsExist(typeName);
    }

    @Override
    public List<CourseType> queryAllPageType(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseTypeMapper.queryAllPageType();
    }

    @Override
    public int updateType(CourseType courseType) {
        return courseTypeMapper.updateType(courseType);
    }

    @Override
    public CourseType queryTypeExceptSelf(int typeId, String typeName) {
        return courseTypeMapper.queryTypeExceptSelf(typeId,typeName);
    }

    @Override
    public int deleteType(int typeId) {
        return courseTypeMapper.deleteType(typeId);
    }

    @Override
    public List<CourseType> queryOnlineType() {
        return courseTypeMapper.queryOnlineType();
    }

    @Override
    public List<CourseType> queryTypeByName(CourseType courseType,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseTypeMapper.queryTypeByName(courseType);
    }

    @Override
    public List<CourseType> totalQueryTypeByName(CourseType courseType) {
        return courseTypeMapper.totalQueryTypeByName(courseType);
    }

    @Override
    public CourseType queryTypeById(int typeId) {
        return courseTypeMapper.queryTypeById(typeId);
    }

    @Override
    public int updateTypeState(int typeId) {
        return courseTypeMapper.updateTypeState(typeId);
    }
}
