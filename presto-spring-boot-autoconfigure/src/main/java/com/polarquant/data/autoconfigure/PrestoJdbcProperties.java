package com.polarquant.data.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * presto jdbc properties
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2020/10/30
 */
@Data
@ConfigurationProperties(value = "spring.data.presto.jdbc", ignoreInvalidFields = true)
public class PrestoJdbcProperties {
    /**
     * the jdbc driver
     */
    private String driver = "com.facebook.presto.jdbc.PrestoDriver";
    private String username;
    private String password;
    private String url;

    /**
     * https://prestodb.io/docs/current/admin/resource-groups.html
     */
    private String applicationName;

    @NestedConfigurationProperty
    PrestoSessionProperties session;

    @NestedConfigurationProperty
    PrestoSSLProperties ssl;
}
