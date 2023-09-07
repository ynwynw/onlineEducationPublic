package top.qiudb.service.user;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.ManagerMapper;
import top.qiudb.pojo.Manager;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService{
    @Autowired
    private ManagerMapper managerMapper;
    @Override
    public Manager queryManagerByName(String managerName) {
        return managerMapper.queryManagerByName(managerName);
    }

    @Override
    public List<Manager> queryPageManager(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return managerMapper.queryPageManager();
    }

    @Override
    public List<Manager> queryAllManager() {
        return managerMapper.queryAllManager();
    }

    @Override
    public int updateLockState(int managerId) {
        return managerMapper.updateLockState(managerId);
    }

    @Override
    public Manager queryManagerById(int managerId) {
        return managerMapper.queryManagerById(managerId);
    }

    @Override
    public Manager queryManagerByAccount(String managerAccount) {
        return managerMapper.queryManagerByAccount(managerAccount);
    }

    @Override
    public List<Manager> searchManager(Manager manager, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return managerMapper.searchManager(manager);
    }

    @Override
    public List<Manager> totalSearchManager(Manager manager) {
        return managerMapper.totalSearchManager(manager);
    }

    @Override
    public int addManager(Manager manager) {
        return managerMapper.addManager(manager);
    }

    @Override
    public int updateManagerDepartment(Manager manager) {
        return managerMapper.updateManagerDepartment(manager);
    }

    @Override
    public int updateManagerInfo(Manager manager) {
        return managerMapper.updateManagerInfo(manager);
    }

    @Override
    public int queryManagerIdByAccount(String managerAccount) {
        return managerMapper.queryManagerIdByAccount(managerAccount);
    }

    @Override
    public Manager queryNameIsExcise(String managerName) {
        return managerMapper.queryNameIsExcise(managerName);
    }

    @Override
    public Manager queryAccountIsExcise(String managerAccount) {
        return managerMapper.queryAccountIsExcise(managerAccount);
    }

    @Override
    public int updatePassWord(int managerId,String passWord) {
        return managerMapper.updatePassWord(managerId,passWord);
    }

    @Override
    public String queryPassWord(String managerName) {
        return managerMapper.queryPassWord(managerName);
    }

    @Override
    public int changePassWord(String managerAccount, String passWord) {
        return managerMapper.changePassWord(managerAccount,passWord);
    }

    @Override
    public int deleteManager(int managerId) {
        return managerMapper.deleteManager(managerId);
    }

    @Override
    public Manager queryExceptSelfIsExcise(String managerName, int managerId) {
        return managerMapper.queryExceptSelfIsExcise(managerName, managerId);
    }

    @Override
    public Manager queryExceptsPhone(int managerId, String managerPhone) {
        return managerMapper.queryExceptsPhone(managerId,managerPhone);
    }

}
