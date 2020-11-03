package com.polarquant.data.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2020/10/30
 */
@Data
@ConfigurationProperties(value = "spring.data.presto.jdbc", ignoreInvalidFields = true)
public class PrestoJdbcProperties {
    private String driver = "com.facebook.presto.jdbc.PrestoDriver";
    private String username;
    private String password;
    private String url;

    @NestedConfigurationProperty
    PrestoSSLProperties ssl;
}
