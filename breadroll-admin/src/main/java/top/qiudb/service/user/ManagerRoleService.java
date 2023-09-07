package top.qiudb.service.user;

import top.qiudb.pojo.ManagerRole;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/25 17:39
 * @description 查询权限
 */
public interface ManagerRoleService {
    public List<String> queryRole(Integer managerId);
    //根据managerId查询所有roleId
    List<Integer> queryRoleId(int managerId);
    //添加相应的管理员权限
    int addRole(List<ManagerRole> managerRoles);
    //根据managerId删除
    int deleteRole(int managerId);
    //根据managerId查询
    List<ManagerRole> queryByManagerId(int managerId);
}
