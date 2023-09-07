package top.qiudb.service.jurisdiction;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.DepartmentMapper;
import top.qiudb.pojo.Department;

import java.util.List;
@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public List<Department> queryPageDepartment(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return departmentMapper.queryPageDepartment();
    }

    @Override
    public List<Department> queryAllDepartment() {
        return departmentMapper.queryAllDepartment();
    }

    @Override
    public Department queryDepartmentById(int departmentId) {
        return departmentMapper.queryDepartmentById(departmentId);
    }

    @Override
    public int addDepartment(Department department) {
        return departmentMapper.addDepartment(department);
    }

    @Override
    public int updateDepartment(Department department) {
        return departmentMapper.updateDepartment(department);
    }

    @Override
    public int deleteDepartment(int departmentId) {
        return departmentMapper.deleteDepartment(departmentId);
    }

    @Override
    public Department queryExciseNameExceptSelf(int departmentId,String departmentName) {
        return departmentMapper.queryExciseNameExceptSelf(departmentId,departmentName);
    }

    @Override
    public Department queryExciseName(String departmentName) {
        return departmentMapper.queryExciseName(departmentName);
    }

    @Override
    public int updateDepartmentState(int departmentId) {
        return departmentMapper.updateDepartmentState(departmentId);
    }

    @Override
    public List<Department> searchDepartment(Department department,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return departmentMapper.searchDepartment(department);
    }

    @Override
    public List<Department> totalSearchDepartment(Department department) {
        return departmentMapper.totalSearchDepartment(department);
    }

    @Override
    public List<Department> queryUseAbleDepartment() {
        return departmentMapper.queryUseAbleDepartment();
    }
}
