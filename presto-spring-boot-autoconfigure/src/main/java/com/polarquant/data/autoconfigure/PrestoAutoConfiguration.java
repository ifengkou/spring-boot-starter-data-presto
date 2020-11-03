package com.polarquant.data.autoconfigure;

import com.alibaba.druid.pool.DruidDataSource;
import io.prestosql.jdbc.PrestoConnection;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.util.Properties;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2020/10/30
 */
@Configuration
@ConditionalOnClass(PrestoConnection.class)
@EnableConfigurationProperties({PrestoJdbcProperties.class, PrestoDataSourcePoolProperties.class})
public class PrestoAutoConfiguration {

    @Bean(name = "prestoJdbcTemplate")
    public JdbcTemplate prestoJdbcTemplate(@Qualifier("prestoDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "prestoDataSource")
    @ConditionalOnMissingBean(name = {"prestoDataSource"})
    public DataSource prestoDataSource(PrestoJdbcProperties jdbcProperties, PrestoDataSourcePoolProperties poolProperties) {
        // druid dataSource
        DruidDataSource druidDataSource = new DruidDataSource();
        // jdbc
        druidDataSource.setDriverClassName(jdbcProperties.getDriver());
        druidDataSource.setUsername(jdbcProperties.getUsername());
        if(null != jdbcProperties.getPassword() && !"".equals(jdbcProperties.getPassword())){
            druidDataSource.setPassword(jdbcProperties.getPassword());
        }
        druidDataSource.setUrl(jdbcProperties.getUrl());

        //SSL
        Properties sslProperties = new Properties();
        PrestoSSLProperties ssl = jdbcProperties.getSsl();
        if(ssl !=null && ssl.enabled){
            sslProperties.put("SSL","true");
            if(ssl.getKeyStorePath() != null && !"".equals(ssl.getKeyStorePath())){
                File file = new File(ssl.getKeyStorePath());
                if(file.exists()){
                    sslProperties.put("SSLKeyStorePath",ssl.getKeyStorePath());
                }
            }else {
                sslProperties.put("SSLKeyStorePath",PrestoAutoConfiguration.class.getClassLoader().getResource("presto.jks").getPath());
            }
            if(ssl.getKeyStorePassword() != null && !"".equals(ssl.getKeyStorePassword())){
                sslProperties.put("SSLKeyStorePassword",ssl.getKeyStorePassword());
            }
        }
        druidDataSource.setConnectProperties(sslProperties);
        // pool
        if(poolProperties.getMinIdle() !=null ){
            druidDataSource.setMinIdle(poolProperties.getMinIdle());
        }
        if(poolProperties.getMaxIdle() !=null ){
            druidDataSource.setMaxIdle(poolProperties.getMaxIdle());
        }
        if(poolProperties.getMaxActive() !=null ){
            druidDataSource.setMaxActive(poolProperties.getMaxActive());
        }
        if(poolProperties.getInitialSize() !=null ){
            druidDataSource.setInitialSize(poolProperties.getInitialSize());
        }
        if(poolProperties.getMinEvictableIdleTimeMillis() !=null ){
            druidDataSource.setMinEvictableIdleTimeMillis(poolProperties.getMinEvictableIdleTimeMillis());
        }
        if(poolProperties.getTimeBetweenEvictionRunsMillis() !=null ){
            druidDataSource.setTimeBetweenConnectErrorMillis(poolProperties.getTimeBetweenEvictionRunsMillis());
        }
        if(poolProperties.getMaxWaitMillis() !=null ){
            druidDataSource.setMaxWait(poolProperties.getMaxWaitMillis());
        }
        return druidDataSource;
    }
}
