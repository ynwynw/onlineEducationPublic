package top.qiudb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.qiudb.pojo.Department;

import java.util.List;

@Mapper
@Component
public interface DepartmentMapper {
    //分页查询部门
    List<Department> queryPageDepartment();
    //查询所有的部门统计数量
    List<Department> queryAllDepartment();
    //根据部门Id查询
    Department queryDepartmentById(int departmentId);
    //添加部门
    int addDepartment(Department department);
    //修改部门
    int updateDepartment(Department department);
    //删除部门
    int deleteDepartment(int departmentId);
    //查询除自己之外的部门名称是否存在
    Department queryExciseNameExceptSelf(@Param("departmentId")int departmentId,@Param("departmentName") String departmentName);
    //查询部门名称是否存在
    Department queryExciseName(String departmentName);
    //修改部门状态
    int updateDepartmentState(int departmentId);
    //搜索部门
    List<Department> searchDepartment(Department department);
    List<Department> totalSearchDepartment(Department department);
    //查询所有可用部门
    List<Department> queryUseAbleDepartment();
}
