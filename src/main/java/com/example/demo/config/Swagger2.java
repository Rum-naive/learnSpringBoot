package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

    private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("基于Springboot+Vue的网盘系统的设计与实现")
				.description("简单的restfun风格")
				.termsOfServiceUrl("http://www.rum.com")
				.version("1.0")
				.build();
    }

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(apiInfo())
                        .select()
                        //扫描basePackage包下面的“/rest/”路径下的内容作为接口文档构建的目标
                        .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
                        .paths(PathSelectors.regex("/rest/.*"))
                        .build();
	}
	
	
}