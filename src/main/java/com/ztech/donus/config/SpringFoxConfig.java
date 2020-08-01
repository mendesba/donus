package com.ztech.donus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SpringFoxConfig implements WebMvcConfigurer {
	public SpringFoxConfig() {
		// Constructor without parameters.
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public Docket detalheApi() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.select().apis(RequestHandlerSelectors.basePackage("com.ztech.donus")).paths(PathSelectors.any()).build()
				.apiInfo(this.informacoesApi().build());
		return docket;
	}

	private ApiInfoBuilder informacoesApi() {
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		apiInfoBuilder.title("Challenge");
		apiInfoBuilder.description("Challenge for Z-Tech");
		apiInfoBuilder.version("1.0");
		apiInfoBuilder.license("Copyright - Z-Tech");
		apiInfoBuilder.contact(this.contato());
		return apiInfoBuilder;
	}

	private Contact contato() { 
		return new Contact( "Copyright - Z-Tech", null, null); 
		}
}
