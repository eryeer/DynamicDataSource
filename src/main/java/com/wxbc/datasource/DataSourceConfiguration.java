package com.wxbc.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.datasource.DataSourceException;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaochen on 2019/1/26.
 */
@Configuration
@Slf4j
@MapperScan(basePackages = {"com.wxbc.dao"})
public class DataSourceConfiguration {

    @Autowired
    DataSourceListProperties dataSourceListProperties;

    @Bean(name = "dynamicDataSource")
    @Primary
    public DataSource setDynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        if (dataSourceListProperties.getMaster() == null){
            throw new DataSourceException("Master dynamic datasource is not configured");
        }
        DataSource masterDataSource = createDataSource(dataSourceListProperties.getMaster());
        dataSourceMap.put(DynamicDataSourceHolder.MASTER, masterDataSource);
        if (!CollectionUtils.isEmpty(dataSourceListProperties.getSlaves())){
            for (String slaveKey : dataSourceListProperties.getSlaves().keySet()) {
                DataSource dataSource = createDataSource(dataSourceListProperties.getSlaves().get(slaveKey));
                dataSourceMap.put(slaveKey,dataSource);
            }
        }
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

    private DataSource createDataSource(DataSourceProperties prop) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(prop.getDriverClassName());
        dataSource.setJdbcUrl(prop.getJdbcUrl());
        dataSource.setUsername(prop.getUsername());
        dataSource.setPassword(prop.getPassword());
        dataSource.setMinimumIdle(prop.getMinimumIdle());
        dataSource.setMaximumPoolSize(prop.getMaximumPoolSize());
        dataSource.setConnectionTestQuery(prop.getConnectionTestQuery());
        return dataSource;
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