package com.wxbc.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaochen on 2019/1/26.
 */
@Configuration
@MapperScan(basePackages = {"com.wxbc.dao"})
public class DataSourceConfiguration {


    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "hikari.master")
    public HikariDataSource setMasterDataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "slave1DataSource")
    @ConfigurationProperties(prefix = "hikari.slave1")
    public HikariDataSource setSlave1DataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "slave2DataSource")
    @ConfigurationProperties(prefix = "hikari.slave2")
    public HikariDataSource setSlave2DataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DataSource setDynamicDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                           @Qualifier("slave1DataSource") DataSource slave1DataSource,
                                           @Qualifier("slave2DataSource") DataSource slave2DataSource) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DynamicDataSourceHolder.MASTER, masterDataSource);
        dataSourceMap.put(DynamicDataSourceHolder.SLAVE + "-1", slave1DataSource);
        dataSourceMap.put(DynamicDataSourceHolder.SLAVE + "-2", slave2DataSource);
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dynamicDataSource") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;


    }
}