package com.wdnyjx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx
 * @author:OverLord
 * @Since:2020/6/12 13:41
 * @Version:v0.0.1
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
