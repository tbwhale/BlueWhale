package com.bluewhale.config;

import com.google.common.collect.Lists;
import com.bluewhale.globle.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 注册过滤器到容器
 * Created by curtin
 * User: curtin
 * Date: 2020/3/22
 * Time: 2:21 PM
 */
@Configuration
@Component
public class RestFilterConfig {

    private final AuthTokenFilter filter;

    @Autowired
    public RestFilterConfig(AuthTokenFilter filter) {
        this.filter = filter;
    }

    @Bean
    public FilterRegistrationBean<AuthTokenFilter> filterRegistrationBeanAuthTokenFilter() {

        FilterRegistrationBean<AuthTokenFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(filter);

        //设置（模糊）匹配的url
        List<String> urlPatterns = Lists.newArrayList();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);//配置过滤规则
        registrationBean.setName("authTokenFilter");//设置过滤器名称
        //设置init参数  过滤掉需要监控的文件 排除
        registrationBean.addInitParameter("exclusions", "/signIn,/static/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,/druid");
        registrationBean.setOrder(2); //执行次序 值越小，Filter越靠前。
        registrationBean.setEnabled(true);

        return registrationBean;
    }
}
