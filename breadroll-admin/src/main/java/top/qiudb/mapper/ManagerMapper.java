package top.qiudb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.qiudb.pojo.Manager;

import java.util.List;

@Component
@Mapper
public interface ManagerMapper {
     //根据名字查询
     Manager queryManagerByName(String managerName);
     //分页查询全部管理员
     List<Manager> queryPageManager();
     //查询全部管理员
     List<Manager> queryAllManager();
     //改变管理员锁定状态
     int updateLockState(int managerId);
     //根据Id查询
     Manager queryManagerById(int managerId);
     //根据帐号查询
     Manager queryManagerByAccount(String managerAccount);
     //搜索管理员
     List<Manager> searchManager(Manager manager);
     //搜索管理员  统计
     List<Manager> totalSearchManager(Manager manager);
     //添加管理员
     int addManager(Manager manager);
     //修改管理员部门
     int updateManagerDepartment(Manager manager);
     //修改管理员信息
     int updateManagerInfo(Manager manager);
     //根据账号获取管理员Id
     int queryManagerIdByAccount(String managerAccount);
     //判断昵称是否存在
     Manager queryNameIsExcise(String managerName);
     //账号是否存在
     Manager queryAccountIsExcise(String managerAccount);
     //根据managerId修改管理员密码
     int updatePassWord(@Param("managerId")int managerId,@Param("passWord")String passWord);
     //根据管理员昵称查询旧密码
     String queryPassWord(String managerName);
     //根据帐号修改密码
     int changePassWord(@Param("managerAccount")String managerAccount,@Param("passWord")String passWord);
     //删除管理员
     int deleteManager(int managerId);
     //判断除自己以外的昵称是否存在
     Manager queryExceptSelfIsExcise(@Param("managerName")String managerName,@Param("managerId")int managerId);
     //查询手机号是否存在
     Manager queryExceptsPhone(@Param("managerId")int managerId,@Param("managerPhone")String managerPhone);
}
