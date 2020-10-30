package com.polarquant.data.autoconfigure;

import com.alibaba.druid.pool.DruidAbstractDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2020/10/30
 */
@Data
@ConfigurationProperties(value = "spring.data.presto.pool", ignoreInvalidFields = true)
public class PrestoDataSourcePoolProperties {
    Integer initialSize;
    Integer minIdle;
    Integer maxIdle;
    Integer maxActive;
    Long maxWaitMillis;
    Long timeBetweenEvictionRunsMillis = DruidAbstractDataSource.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
    Long minEvictableIdleTimeMillis = DruidAbstractDataSource.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

    PrestoSSLProperties ssl;
}
