package top.qiudb.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.CourseTypeMapper;
import top.qiudb.pojo.course.CourseType;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/9 9:20
 * @description 描述
 */
@Service
public class CourseTypeServiceImpl implements CourseTypeService{
    @Autowired
    CourseTypeMapper courseTypeMapper;
    @Override
    public List<CourseType> queryAllType() {
        return courseTypeMapper.selectAllType();
    }
}
