package top.qiudb.mapper;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.qiudb.pojo.Role;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/25 17:05
 * @description 角色表
 */
@Mapper
@Component
public interface RoleMapper {
     List<Role> queryAllRole();
     //分页查询角色
     List<Role> queryPageRole();
     //搜索角色
     List<Role> searchRole(Role role);
     List<Role> totalSearchRole(Role role);
     //编辑角色
     int updateRole(Role role);
     //根据Id查询角色
     Role queryRoleById(int roleId);
     //修改角色状态
     int updateRoleState(int roleId);
     //查询所有可用权限
     List<Role> queryUseAbleRole();
     //通过name得到角色Id
     Integer queryRoleId(String roleName);
     //得到角色类型
     List<String> queryRoleType();
     //根据角色类型查询角色
     List<Role> queryRoleByRoleType(String roleType);
}
