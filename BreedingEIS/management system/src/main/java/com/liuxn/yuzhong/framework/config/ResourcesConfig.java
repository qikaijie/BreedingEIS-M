package com.liuxn.yuzhong.framework.config;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.liuxn.yuzhong.app.interceptor.APPAuthInterceptor;
import com.liuxn.yuzhong.common.constant.Constants;
import com.liuxn.yuzhong.framework.interceptor.RepeatSubmitInterceptor;

import cn.hutool.core.util.StrUtil;

/**
 * 通用配置
 * 
 * @author ruoyi
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer
{
	private static final String ORIGINS[] = new String[] { "POST", "GET", "PUT", "OPTIONS", "DELETE" };
	
    @Autowired
    private RepeatSubmitInterceptor repeatSubmitInterceptor;
    
    @Autowired
    private APPAuthInterceptor appAuthInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        /** 本地文件上传路径 */
        registry.addResourceHandler(Constants.RESOURCE_PREFIX + "/**").addResourceLocations("file:" + YuZhongConfig.getProfile() + "/");

        /** swagger配置 */
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 自定义拦截规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
        registry.addInterceptor(appAuthInterceptor).addPathPatterns("/app/**");
    }
  
    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOrigin("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }	
    
    /**
     * 默认解析器 其中locale表示默认语言
     */
    @Bean
    LocaleResolver localeResolver() {
    	return new I18nLocaleResolver();
    }
    
    /**
     * 获取请求头国际化信息
     */
    static class I18nLocaleResolver implements LocaleResolver {
 
        @NotNull
        @Override
        public Locale resolveLocale(HttpServletRequest httpServletRequest) {
            String language = httpServletRequest.getHeader("lang");
            Locale locale = Locale.getDefault();
            if (StrUtil.isNotBlank(language)) {
                String[] split = language.split("_");
                locale = new Locale(split[0], split[1]);
            }
            return locale;
        }
 
        @Override
        public void setLocale(@NotNull HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
 
        }
    }

}