package com.onlineSeller.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@Configuration
@EnableSwagger2
@EnableWebMvc
public class Swagger {

	public Docket api()
	{
		
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).securitySchemes(securitySchemes())
				.securityContexts(Arrays.asList(securityContext())).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
	
	private ApiInfo apiInfo()
	{
		return new ApiInfo("E commerce", null, null, null, null, null, null, null);
	}
	
	
	private List<SecurityScheme> securitySchemes()
	{
		return Arrays.asList( new ApiKey("Bearer","Authrization","header"));
		
	}
	private SecurityContext securityContext()
	{
		
		return SecurityContext.builder()
				.securityReferences(Arrays.asList(new SecurityReference("Bearer", new AuthorizationScope[0]))).build();
		
		
	}
	
	
}
