package top.qiudb.mapper;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.qiudb.pojo.Manager;
import top.qiudb.pojo.ManagerRole;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/25 17:29
 * @description 查询权限
 */
@Component
@Mapper
public interface ManagerRoleMapper {
    //根据managerId查询所有权限
     List<String> queryRole(Integer managerId);
     //根据managerId查询所有roleId
     List<Integer> queryRoleId(int managerId);
     //添加相应的管理员权限
     int addRole(List<ManagerRole> managerRoles);
     //根据managerId删除
     int deleteRole(int managerId);
     //根据managerId查询
     List<ManagerRole> queryByManagerId(int managerId);
}
