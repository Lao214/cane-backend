package com.example.integratedHub.entity.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 苏运浩
 * @version 1.0
 * @description: TODO
 * @date 2023/8/11 4:52 PM
 */
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    /**
     * 连接地址
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * 域名
     */
    private String nginxHost;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getNginxHost() {
        return nginxHost;
    }

    public void setNginxHost(String nginxHost) {
        this.nginxHost = nginxHost;
    }
}

