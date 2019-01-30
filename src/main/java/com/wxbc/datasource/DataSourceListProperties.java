package com.wxbc.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties("hikari")
@Data
@Component
public class DataSourceListProperties {
    Map<String,DataSourceProperties> slaves;
    DataSourceProperties master;
}
