package top.qiudb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import top.qiudb.util.tools.PropertiesUtil;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //本地存放上传文件的真实地址
        String realPathDir = PropertiesUtil.getUploadUrl();
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/resources/");
        //配置上传文件的映射关系
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:"+realPathDir);

        registry.addResourceHandler("doc.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");

        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 解决controller返回字符串中文乱码问题
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter)converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 首页
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/page").setViewName("index");
        //帐号登录
        registry.addViewController("/login").setViewName("global/login");
        //====================后台管理==============================
        registry.addViewController("/page/home").setViewName("page/home");
        //课程列表
        registry.addViewController("/course/courseList").setViewName("course/class-list");
        //课程类别
        registry.addViewController("/type/courseType").setViewName("course/course-type-list");
        //目录列表
        registry.addViewController("/directory/directoryList").setViewName("course/directory-list");
        //讲师列表
        registry.addViewController("/teacher/teacherList").setViewName("course/teacher-list");
        //特训班列表
        registry.addViewController("/special/specialClassList").setViewName("course/special-class-list");
        //添加特训班课程
        registry.addViewController("/course/addSpecialClass").setViewName("course/add-edit-special-class");
        //部门信息列表
        registry.addViewController("/department/departmentList").setViewName("jurisdiction/department-manager");
        //权限信息列表
        registry.addViewController("/role/jurisdictionList").setViewName("jurisdiction/jurisdiction-manager");
        //管理员列表
        registry.addViewController("/manager/managerList").setViewName("jurisdiction/manager-list");
        //用户列表
        registry.addViewController("/user/userList").setViewName("jurisdiction/user-list");
       //公告管理
        registry.addViewController("/message/publicAdmin").setViewName("marketing/public-administration");
        //VIP管理
        registry.addViewController("/vip/vipAdmin").setViewName("marketing/vip-manage");
        //轮播图管理
        registry.addViewController("/banner/bannerAdmin").setViewName("marketing/banner-manager");
        //课程审核
        registry.addViewController("/courseAudit").setViewName("auditing/course-audit");
        //资料管理
        registry.addViewController("/resource/resourceAdmin").setViewName("course/resource-list");
        //锦囊管理
        registry.addViewController("/article/articleAdmin").setViewName("course/article-list");
        //文章审核
        registry.addViewController("/articleAudit").setViewName("auditing/article-audit");
        //资料审核
        registry.addViewController("/resourceAudit").setViewName("auditing/resource-audit");
        //特训班审核
        registry.addViewController("/specialAudit").setViewName("auditing/special-class-audit");
        //忘记密码
        registry.addViewController("/forgetPassword").setViewName("global/forget-password");
        //课程预告
        registry.addViewController("/preview/coursePreview").setViewName("course/course-preview");
        //订单管理
        registry.addViewController("/orderInfo/orderAdmin").setViewName("marketing/order-manage");
    }
}