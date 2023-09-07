package top.qiudb.breadrolleducation;

import cn.dev33.satoken.secure.SaSecureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.qiudb.pojo.course.*;
import top.qiudb.pojo.message.SystemMessage;
import top.qiudb.pojo.order.Order;
import top.qiudb.pojo.resource.Article;
import top.qiudb.pojo.user.CoinRecord;
import top.qiudb.pojo.user.User;
import top.qiudb.pojo.vip.Vip;
import top.qiudb.service.course.*;
import top.qiudb.service.message.SystemMessageService;
import top.qiudb.service.order.OrderService;
import top.qiudb.service.resource.ArticleService;
import top.qiudb.service.user.CoinRecordService;
import top.qiudb.service.user.MyMessageService;
import top.qiudb.service.user.UserService;
import top.qiudb.service.vip.UserVipService;
import top.qiudb.util.markdown.MarkdownUtils;
import top.qiudb.util.redis.RedisUtil;
import top.qiudb.util.redis.UserSign;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest
class BreadrollEducationApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    UserSign userSign;
    @Autowired
    MyMessageService myMessageService;
    @Autowired
    CoinRecordService coinRecordService;
    @Autowired
    CourseTypeService courseTypeService;
    @Autowired
    CourseListService courseListService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;
    @Autowired
    SystemMessageService systemMessageService;
    @Autowired
    UserVipService userVipService;
    @Autowired
    CoursePreviewService coursePreviewService;
    @Autowired
    ArticleService articleService;
    @Autowired
    CoachApplyService coachApplyService;
    @Autowired
    OrderService orderService;
    @Autowired
    RedisUtil redisUtil;

    @Test
    void contextLoads() {
        User user = new User();
        user.setUserAccount("1325554003@qq.com");
        user.setUserName("马英发");
        // md5加密
        String pws = SaSecureUtil.md5BySalt("123456", user.getUserAccount());
        user.setPassWord(pws);
        Boolean isSuccess = userService.insertUser(user);
        if(isSuccess){
            System.out.println("注册成功");
        }else{
            System.out.println("注册失败");
        }
    }
    @Test
    void login() {
        User user = userService.queryUserByAccount("1325554003@qq.com");
        if(user==null){
            System.out.println("用户不存在");
            return;
        }
        String pws = SaSecureUtil.md5BySalt("123456", user.getUserAccount());
        if(pws.equals(user.getPassWord())){
            System.out.println("登录成功");
            return;
        }
        System.out.println("登录失败");
    }
    private static String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void TestA(){
        User user = new User();
        user.setUserName("马英发");
        user.setUserId(1);
        redisUtil.set("user",user);

        User u = (User) redisUtil.get("user");
        System.out.println(u.getUserId());
        System.out.println(u.getUserName());

    }

    @Test
    void test() {
        LocalDate today = LocalDate.parse("2021-05-10");
        //LocalDate today = LocalDate.now();
        { // doSign
            boolean signed = userSign.doSign(1000, today);
            if (signed) {
                System.out.println("您已签到：" + formatDate(today, "yyyy-MM-dd"));
            } else {
                System.out.println("签到完成：" + formatDate(today, "yyyy-MM-dd"));
            }
        }

        { // checkSign
            boolean signed = userSign.checkSign(1000, today);
            if (signed) {
                System.out.println("您已签到：" + formatDate(today, "yyyy-MM-dd"));
            } else {
                System.out.println("尚未签到：" + formatDate(today, "yyyy-MM-dd"));
            }
        }

        { // getSignCount
            long count = userSign.getSignCount(1000, today);
            System.out.println("本月签到次数：" + count);
        }

        { // getContinuousSignCount
            long count = userSign.getContinuousSignCount(1000, today);
            System.out.println("连续签到次数：" + count);
        }

        { // getFirstSignDate
            LocalDate date = userSign.getFirstSignDate(1000, today);
            System.out.println("本月首次签到：" + formatDate(date, "yyyy-MM-dd"));
        }

        { // getSignInfo
            System.out.println("当月签到情况：");
            Map<String, Boolean> signInfo = new TreeMap<>(userSign.getSignInfo(1000, today));
            for (Map.Entry<String, Boolean> entry : signInfo.entrySet()) {
                System.out.println(entry.getKey() + ": " + (entry.getValue() ? "√" : "-"));
            }
        }
    }
}
