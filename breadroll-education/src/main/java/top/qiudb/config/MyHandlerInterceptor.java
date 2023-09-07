package top.qiudb.config;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/27 9:37
 * @description 自定义拦截器
 */
public class MyHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        StpUtil.checkLogin();
        return true;
    }
}
