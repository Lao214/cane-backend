package com.example.integratedHub.config;

import com.example.integratedHub.entity.properties.MinioProperties;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author xiaozq
 * @Date 2022/11/17 9:54
 * <p>@Description: 注意：这里不能用@Data,需手动写gitter，setter
 * reason：与@ConfigurationProperties一起用的时候，配置文件的值不能赋值给对应类属性，属性值均为null</p>
 */
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfig {

    @Autowired
    private MinioProperties minioProperties;

    /**
     * 获取MinioClient
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

}

