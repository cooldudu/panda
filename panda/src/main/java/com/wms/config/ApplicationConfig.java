package com.wms.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.wms.core.utils.jdbc.IJdbcDao;
import com.wms.core.utils.jdbc.JdbcDao;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableAsync
@EnableScheduling

@EnableSpringConfigured
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@ComponentScan
public class ApplicationConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceFactory.makeDataSource();
    }

    @Bean
    public IJdbcDao jdbcDao() {
        var jdbcDao = new JdbcDao();
        jdbcDao.setDataSource(this.dataSource());
        return jdbcDao;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        return new LocalValidatorFactoryBean();
    }

}