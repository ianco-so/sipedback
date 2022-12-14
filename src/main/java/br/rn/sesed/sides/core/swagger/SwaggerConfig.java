package br.rn.sesed.sides.core.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer{
	
	@Autowired
	BuildProperties buildProperties;
	
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.any())
					.build()
					.apiInfo(new ApiInfoBuilder()
									.title(buildProperties.getName())
									.version(buildProperties.getVersion())
									.description(buildProperties.getName())
									.build());
	}
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
//            registry.addRedirectViewController("/doc/v2/api-docs", "/v2/api-docs?group=restful-api");
//            registry.addRedirectViewController("/doc/swagger-resources/configuration/ui","/swagger-resources/configuration/ui");
//            registry.addRedirectViewController("/doc/swagger-resources/configuration/security","/swagger-resources/configuration/security");
//            registry.addRedirectViewController("/doc/swagger-resources", "/swagger-resources");
    }
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
