package com.dousnl.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public Docket createRestApi(){
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
                .apis(RequestHandlerSelectors.basePackage("com.dousnl.controller"))
                .paths(PathSelectors.any())
                .build();
	}
	
	private ApiInfo apiInfo(){
		return new ApiInfo("Spring Boot中使用Swagger2构建RESTful APIs",
			      "更多Spring Boot相关文章请关注：http://blog.didispace.com/",
			      "1.0",
			      "http://blog.didispace.com/",
			      new Contact("极策", "http://exmind.com", "tospur-api@exmind.com"),
			      null,
			      null);
	}
}
