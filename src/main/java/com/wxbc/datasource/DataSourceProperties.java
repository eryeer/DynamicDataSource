package com.wxbc.datasource;

import lombok.Data;

@Data
public class DataSourceProperties {
    private String driverClassName;
    private String jdbcUrl;
    private String username;
    private String password;
    private Integer minimumIdle;
    private Integer maximumPoolSize;
    private String connectionTestQuery;

}
