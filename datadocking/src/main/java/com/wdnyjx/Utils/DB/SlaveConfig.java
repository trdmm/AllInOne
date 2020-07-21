package com.wdnyjx.Utils.DB;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * File ...
 *
 * @Project:binding
 * @Package:com.worldnyjx.utils.DB.DataSource
 * @author:OverLord
 * @Since:2020/5/13 21:29
 * @Version:v0.0.1
 */
@Configuration
@MapperScan(basePackages = {"com.wdnyjx.Mapper.slave"}
,sqlSessionFactoryRef = "slaveSqlSessionFactory")
public class SlaveConfig {
    @Autowired
    @Qualifier("slave")
    private DataSource slaveDataSource;

    @Bean
    public SqlSessionFactory slaveSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(slaveDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/slave/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    public SqlSessionTemplate slaveSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(slaveSqlSessionFactory());
    }
}
