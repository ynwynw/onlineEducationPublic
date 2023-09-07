package top.qiudb.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.ManagerRoleMapper;
import top.qiudb.pojo.ManagerRole;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/25 17:40
 * @description 描述
 */
@Service
public class ManagerRoleServiceImpl implements ManagerRoleService {
    @Autowired
    ManagerRoleMapper managerRoleMapper;

    @Override
    public List<String> queryRole(Integer managerId) {
        return managerRoleMapper.queryRole(managerId);
    }

    @Override
    public List<Integer> queryRoleId(int managerId) {
        return managerRoleMapper.queryRoleId(managerId);
    }

    @Override
    public int addRole(List<ManagerRole> managerRoles) {
        return managerRoleMapper.addRole(managerRoles);
    }

    @Override
    public int deleteRole(int managerId) {
        return managerRoleMapper.deleteRole(managerId);
    }

    @Override
    public List<ManagerRole> queryByManagerId(int managerId) {
        return managerRoleMapper.queryByManagerId(managerId);
    }
}
