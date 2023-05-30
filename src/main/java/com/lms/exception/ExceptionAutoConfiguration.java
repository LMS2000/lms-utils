package com.lms.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Configuration
@Slf4j
//只有目标类在类路径上才会加载bean配置
@ConditionalOnClass({ExceptionHandler.class, DuplicateKeyException.class})
@ConditionalOnProperty(prefix = "lms.global", name = "enableGlobalExceptionHandler", havingValue = "true" ,matchIfMissing = true )
public class ExceptionAutoConfiguration {


    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    @Bean
    public GlobalExceptionHandler globalExceptionHandler(){
        log.info("已启用全局异常处理器");
        return new GlobalExceptionHandler();
    }

}
