package com.lms.swagger;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

/**
 * @author 大忽悠
 * @create 2022/9/15 11:16
 */
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerConfig {
    private Boolean open;
    /**
     * 包扫描路径
     */
    private String scanPackage;
    private String title;
    private String description;
    private String version;
    private Environment environment;

    @Autowired
    public void setEnv(Environment environment){
        this.environment=environment;
    }

    public Environment getEnv(){
        return environment;
    }
}
