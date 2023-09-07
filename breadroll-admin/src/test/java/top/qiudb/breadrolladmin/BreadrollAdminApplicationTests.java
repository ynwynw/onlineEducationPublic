package top.qiudb.breadrolladmin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import top.qiudb.pojo.CoachApply;
import top.qiudb.pojo.Role;
import top.qiudb.service.course.CoachApplyService;
import top.qiudb.service.marketing.VipService;
import top.qiudb.service.user.RoleService;
import top.qiudb.service.user.ManagerRoleService;
import top.qiudb.service.user.UserService;
import top.qiudb.util.redis.RedisUtil;
import top.qiudb.util.tools.Tools;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
class BreadrollAdminApplicationTests {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService authService;
    @Autowired
    private ManagerRoleService managerRoleService;
    @Autowired
    private VipService vipService;
    @Autowired
    private CoachApplyService coachApplyService;


    @Test
    void contextLoads() {
        redisUtil.set("user","马英发");
        System.out.println(redisUtil.get("user"));
        redisUtil.expire("user",10);
        System.out.println(redisUtil.hasKey("user"));
        System.out.println(redisUtil.getExpire("user"));
        System.out.println(redisUtil.hasKey("user"));
        redisUtil.set("1",1);
        redisUtil.set("2",2);
        redisUtil.set("3",3);
        redisUtil.set("4",4);
    }

    @Test
    void User(){
        System.out.println(userService.queryUserByName("admin"));
        List<Role> auths = authService.queryAllRole();
        System.out.println(Arrays.toString(auths.toArray()));
    }


    @Test
    void tests(){
        List<String> auths = managerRoleService.queryRole(1);
        System.out.println(Arrays.toString(auths.toArray()));
        String string = String.join(",", auths);
        System.out.println(string);
    }

    @Test
    void password(){
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String p1 = Tools.passwordEncoder("123456");
        String p2 = Tools.passwordEncoder("123456");
        String p3 = Tools.passwordEncoder("123456");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        boolean flag1 = bcryptPasswordEncoder.matches("123456",p1);
        boolean flag2 = bcryptPasswordEncoder.matches("123456",p2);
        boolean flag3 = bcryptPasswordEncoder.matches("123456",p3);
        System.out.println(flag1);
        System.out.println(flag2);
        System.out.println(flag3);
    }
    @Test
    void vip(){
        List<CoachApply> coachApplies = coachApplyService.queryRetreat();
        System.out.println(Arrays.toString(coachApplies.toArray()));
        int a[]={1,2,3};
        for (int[] ints : Arrays.asList(a)) {
            
        }
    }

}
