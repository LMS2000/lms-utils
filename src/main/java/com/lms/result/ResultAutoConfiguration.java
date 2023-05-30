package com.lms.result;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Slf4j
@ConditionalOnClass(WebMvcConfigurer.class)
@ConditionalOnProperty(prefix = "lms.global",name= "enableGlobalResponse",havingValue = "true",matchIfMissing = true)
public class ResultAutoConfiguration implements WebMvcConfigurer {



    @ConditionalOnMissingBean(ResponseAdvice.class)
    @Bean
    public ResponseAdvice responseAdvice(){
        log.info("已启用统一全局响应配置");
        return new ResponseAdvice();
    }

    /**
     * 避免返回String类型时,优先选择StringHttpMessageConverter的问题。
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }
}
