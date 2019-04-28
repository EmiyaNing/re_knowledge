package com.example.re_knowledge.config;

import com.example.re_knowledge.interceptor.loginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.*;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new loginInterceptor()).addPathPatterns("/**").excludePathPatterns("/error").excludePathPatterns("/login").excludePathPatterns("/signup").excludePathPatterns("/**/css/*.css", "/**/js/*.js", "/**/img/*.png", "/**/img/*.jpg", "/**/img/*.jpeg", "/**/fonts/*","/**/images/*.jpg").excludePathPatterns("/**/video/**","/**/lib/solar/stylesheets/*.css","/**/lib/solar/javascripts/*.js","/**/lib/Layui/*.js","/**/lib/Layui/font/**","/**/lib/Layui/lay/modules/*.js","/**/lib/Layui/images/face/*.gif","/**/lib/Layui/css/modules/**", "/**/editor.md-master/**", "/**/editor.md-master/css/**", "/**/editor.md-master/lib/codemirror/**", "/**/editor.md-master/**/**/**","/**/editor.md-master/**/**", "/**/video/**", "/**/ppt/**");
        super.addInterceptors(registry);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        registry.addResourceHandler("/css/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/css/");
        registry.addResourceHandler("/img/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/img/");
        registry.addResourceHandler("/lib/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/lib/");
        registry.addResourceHandler("/fonts/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/fonts/");
        registry.addResourceHandler("/js/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/js/");
        registry.addResourceHandler("/images/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/images/");
        registry.addResourceHandler("/user/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/user/");
        registry.addResourceHandler("/editor.md-master/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/editor.md-master/");
        registry.addResourceHandler("/video/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/video/");
        registry.addResourceHandler("/**/css/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/css/");
        registry.addResourceHandler("/**/img/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/img/");
        registry.addResourceHandler("/**/lib/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/lib/");
        registry.addResourceHandler("/**/fonts/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/fonts/");
        registry.addResourceHandler("/**/js/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/js/");
        registry.addResourceHandler("/**/images/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/images/");
        registry.addResourceHandler("/**/user/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/user/");
        super.addResourceHandlers(registry);
    }

}
