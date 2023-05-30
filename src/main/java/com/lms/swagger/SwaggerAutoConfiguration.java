package com.lms.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;



/**
 * @author dhy
 */
@EnableKnife4j
@EnableOpenApi
@Slf4j
@Configuration
@EnableConfigurationProperties(SwaggerConfig.class)
public class SwaggerAutoConfiguration {

    /**
     * <P>
     *     如果是开发环境,关闭该服务
     * </P>
     */
    @Bean
    @ConditionalOnProperty(prefix = "swagger", name = "open", havingValue = "true")
    public Docket createRestApi(SwaggerConfig swaggerConfig) {
        Environment environment = swaggerConfig.getEnvironment();
        String scanPackage = swaggerConfig.getScanPackage();

        Profiles pro = Profiles.of("pro");

        //如果是开发环境,返回true
        boolean isAccept = environment.acceptsProfiles(pro);
        log.info("当前扫描的controller包路径为: {}",scanPackage);
        log.info("当前环境为: {},swagger功能是否开启: {}",environment.getActiveProfiles().length==0?environment.getDefaultProfiles():environment.getActiveProfiles(),!isAccept?"开启":"关闭");
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo(swaggerConfig))
                .enable(!isAccept)
                .select()
                .apis(RequestHandlerSelectors.basePackage(scanPackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(SwaggerConfig swaggerConfig) {
        return new ApiInfoBuilder()
                .title(swaggerConfig.getTitle())
                .description(swaggerConfig.getDescription())
                .version(swaggerConfig.getVersion())
                .build();
    }

}