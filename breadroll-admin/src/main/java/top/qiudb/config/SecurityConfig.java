package top.qiudb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.qiudb.config.security.MyUserDetailsService;
import top.qiudb.pojo.Role;
import top.qiudb.service.user.RoleService;
import top.qiudb.util.tools.ResultVO;
import java.io.PrintWriter;
import java.util.List;

@EnableWebSecurity // 开启WebSecurity模式
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private RoleService roleService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //定义认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //用户认证处理 密码处理
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 定制请求的授权规则
        http.authorizeRequests()
                //放行登录页面
                .antMatchers("/login").permitAll()
                .antMatchers("/goToForgetPassWord").permitAll()
                .antMatchers("/manager/changePassWord").permitAll()
                //放行swagger-ui.html资源路径过滤
                .antMatchers("/doc.html").permitAll() // 任意访问
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/page/**","/auditCause/**","/managerInfo/**").hasAuthority("default");

//        http.authorizeRequests()
//                .antMatchers("/data/**").hasAnyAuthority("data","admin")
//                .antMatchers("/course/**").hasAnyAuthority("course","admin")
//                .antMatchers("/directory/**").hasAnyAuthority("directory","admin")
//                .antMatchers("/type/**").hasAnyAuthority("type")
//                .antMatchers("/teacher/**").hasAnyAuthority("teacher")
//                .antMatchers("/special/**").hasAnyAuthority("special")
//                .antMatchers("/department/**").hasAnyAuthority("department")
//                .antMatchers("/preview/**").hasAnyAuthority("preview")
//                .antMatchers("/role/**").hasAnyAuthority("role")
//                .antMatchers("/user/**").hasAnyAuthority("user")
//                .antMatchers("/article/**").hasAnyAuthority("article")
//                .antMatchers("/vip/**").hasAnyAuthority("vip")
//                .antMatchers("/resource/**").hasAnyAuthority("resource")
//                .antMatchers("/courseAudit/**").hasAnyAuthority("courseAudit")
//                .antMatchers("/articleAudit/**").hasAnyAuthority("articleAudit")
//                .antMatchers("/specialAudit/**").hasAnyAuthority("specialAudit")
//                .antMatchers("/resourceAudit/**").hasAnyAuthority("resourceAudit")
//                .antMatchers("/orderInfo/**").hasAnyAuthority("orderInfo")
//                .antMatchers("/banner/**").hasAnyAuthority("banner")
//                .antMatchers("/message/**").hasAnyAuthority("message")
//                .antMatchers("/manager/**").hasAnyAuthority("manager");

        List<Role> roles = roleService.queryAllRole();
        String adminName = "admin";
        for (Role role : roles) {
            if(role.getRoleUrl().equals("/**/**")){
                adminName = role.getRoleName();
                break;
            }
        }
        for (Role role : roles) {
            if(role.getRoleName().equals(adminName)){
                continue;
            }
            http.authorizeRequests().antMatchers(role.getRoleUrl()).hasAnyAuthority(role.getRoleName(),adminName);
        }
        http.authorizeRequests().anyRequest().fullyAuthenticated();

        http.formLogin() // 表单登录
                //自定义登录页面
                .loginPage("/login")
                //自定义登录逻辑
                .loginProcessingUrl("/loginCheck")
                .usernameParameter("userName")
                .passwordParameter("passWord")
                //登录成功,必须是POST方式
                .successHandler((req, resp, authentication) -> {
                    Object principal = authentication.getPrincipal();
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    req.getSession(true).setAttribute("user",principal);
                    out.write(new ObjectMapper().writeValueAsString(ResultVO.success("登录成功")));
                    out.flush();
                    out.close();
                }).failureHandler((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    ResultVO resultVO = new ResultVO();
                    resultVO.setCode(500);
                    resultVO.setMessage("登录失败");
                    if (e instanceof LockedException) {
                        resultVO.setMessage("账户被锁定，请联系管理员!");
                    } else if (e instanceof CredentialsExpiredException) {
                        resultVO.setMessage("密码过期，请联系管理员!");
                    } else if (e instanceof AccountExpiredException) {
                        resultVO.setMessage("账户过期，请联系管理员!");
                    } else if (e instanceof DisabledException) {
                        resultVO.setMessage("账户被禁用，请联系管理员!");
                    } else if (e instanceof BadCredentialsException) {
                        resultVO.setMessage("用户名或者密码输入错误，请重新输入!");
                    }
                    out.write(new ObjectMapper().writeValueAsString(resultVO));
                    out.flush();
                    out.close();
                });

        //注销登录处理，注销成功跳到的地址
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(ResultVO.success("注销成功")));
                    out.flush();
                    out.close();
                })
                .permitAll();

        //关闭csrf功能:跨站请求伪造,默认只能通过post方式提交logout请求
        http.csrf().disable();
        //关闭iframe
        http.headers().frameOptions().disable();

        //记住我
        http.rememberMe();
    }

    /**
     * 忽略拦截
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略url - 会直接过滤该url - 将不会经过Spring Security过滤器链
        web.ignoring().antMatchers("/util/**","/upload/**")
                .antMatchers("/sms/**","/alipay/**");

        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/static/**");
    }
}