package top.qiudb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
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
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyHandlerInterceptor())
                //拦截路径
                .addPathPatterns("/**")
                //放行路径
                .excludePathPatterns("/global/**","/alipay/**","/sms/**","/course/**","/upload/**","/util/**")
                .excludePathPatterns("/vip/**","/banner/**","/static/**","/")
                //放行swagger文档
                .excludePathPatterns("/doc.html","/swagger-resources/**","/webjars/**","/v2/**","/api/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 首页
        registry.addViewController( "/" ).setViewName( "forward:/static/index.html" );
        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
    }

}