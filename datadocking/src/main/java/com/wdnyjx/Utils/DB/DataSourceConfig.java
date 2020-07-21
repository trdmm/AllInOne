package com.wdnyjx.Utils.DB;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * File ...
 *
 * @Project:binding
 * @Package:com.worldnyjx.utils.DB.DataSource
 * @author:OverLord
 * @Since:2020/5/13 21:07
 * @Version:v0.0.1
 */
@Configuration
public class DataSourceConfig {
    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    @Qualifier("master")
    public DataSource masterDataSource() {
        //TODO type 指定hikari
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }
}
