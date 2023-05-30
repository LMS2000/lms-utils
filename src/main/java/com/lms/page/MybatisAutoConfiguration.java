package com.lms.page;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@Slf4j
//只有目标类在类路径上才会加载bean配置
@ConditionalOnClass({MybatisPlusInterceptor.class, PaginationInnerInterceptor.class})
@ConditionalOnProperty(prefix = "lms.global",name= "enablePage",havingValue = "true",matchIfMissing = true)
public class MybatisAutoConfiguration {

    /**
     * 装配mybatisplus分页插件
     * @return
     */
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        log.info("MyBatis-Plus分页插件配置成功!");
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 装配日期注入器
     * @return
     */
    @ConditionalOnMissingBean(DateMetaObjectHandler.class)
    @Bean
    public DateMetaObjectHandler dateMetaObjectHandler(){
        return new DateMetaObjectHandler();
    }
}
