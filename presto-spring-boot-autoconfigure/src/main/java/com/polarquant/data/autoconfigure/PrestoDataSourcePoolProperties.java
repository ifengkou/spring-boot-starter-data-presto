package com.polarquant.data.autoconfigure;

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
    Long timeBetweenEvictionRunsMillis;
    Long minEvictableIdleTimeMillis;
}
