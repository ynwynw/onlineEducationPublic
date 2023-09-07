package top.qiudb.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.ManagerRoleMapper;
import top.qiudb.pojo.Manager;
import top.qiudb.service.user.ManagerService;

import java.util.Arrays;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private ManagerRoleMapper managerAuthMapper;
    @Autowired
    private ManagerService managerService;
    @Override
    public UserDetails loadUserByUsername(String userName){
        //查询用户信息
        Manager manager;
        if(userName.contains("@")){
            manager = managerService.queryManagerByAccount(userName);
        }else{
            manager = managerService.queryManagerByName(userName);
        }

        if(manager==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        //获取权限列表
        List<String> auths = managerAuthMapper.queryRole(manager.getManagerId());
        auths.add("default");
        // 使用Spring Security内部UserDetails的实现类User，来创建User对象
        return new User(userName, manager.getPassWord(), !manager.getDeleteState(),
                true,true,
                !manager.getLockedState(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", auths)));
    }
}