package com.polarquant.data.autoconfigure;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2020/10/30
 */
@Data
@NoArgsConstructor
public class PrestoSSLProperties {
    Boolean enabled = Boolean.FALSE;
    String keyStorePath;
    String keyStorePassword;
    //The location of the Java TrustStore file that will be used to validate HTTPS server certificates.
    String trustStorePath;
    String trustStorePassword;
}
