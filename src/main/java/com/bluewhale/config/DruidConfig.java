package com.bluewhale.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * druid监控控制台配置
 * http://localhost:8080/bluewhale/druid/login.html
 * <p>
 * Created by curtin
 * User: curtin
 * Date: 2020/3/22
 * Time: 12:24 AM
 */

@Configuration
public class DruidConfig {

    /**
     * 配置Druid监控
     * 后台管理Servlet
     *
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServlet() {// 主要实现web监控的配置处理
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(
                new StatViewServlet(), "/druid/*");//表示进行druid监控的配置处理操作
        //IP白名单（没有配置或者为空，则允许所有访问）
        //servletRegistrationBean.addInitParameter("allow", "127.0.0.1,192.168.202.233");
        //IP黑名单（共同存在时，deny优于allow）
        //servletRegistrationBean.addInitParameter("deny", "192.168.202.234");
        servletRegistrationBean.addInitParameter("loginUsername", "root");//用户名
        servletRegistrationBean.addInitParameter("loginPassword", "root");//密码
        //是否可以重置数据源 禁用HTML页面上的"Reset All"功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;

    }

    /**
     * 配置web监控的filter
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new WebStatFilter());
        //所有请求进行监控处理
        filterRegistrationBean.addUrlPatterns("/*");
        //排除过滤文件
        filterRegistrationBean.addInitParameter("exclusions", "/static/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        //执行次序 值越小，Filter越靠前。
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();


    }
}
