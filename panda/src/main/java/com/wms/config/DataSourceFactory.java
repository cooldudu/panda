package com.wms.config;

import com.wms.core.utils.common.ResourceBundleUtil;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {
    public static DataSource makeDataSource(){
        var ds = new HikariDataSource();
        ds.setDriverClassName(ResourceBundleUtil.getString("application","spring.datasource.driver-class-name"));
        ds.setJdbcUrl(ResourceBundleUtil.getString("application","spring.datasource.url"));
        ds.setUsername(ResourceBundleUtil.getString("application","spring.datasource.username"));
        ds.setPassword(ResourceBundleUtil.getString("application","spring.datasource.password"));
        ds.setConnectionTestQuery(ResourceBundleUtil.getString("application","db.testSql"));
        ds.setMinimumIdle(ResourceBundleUtil.getInteger("application","db.minConn"));
        ds.setMaximumPoolSize(ResourceBundleUtil.getInteger("application","db.maxConn"));
        ds.setConnectionTimeout(ResourceBundleUtil.getLong("application","db.connTimeout"));
        return ds;
    }
}
