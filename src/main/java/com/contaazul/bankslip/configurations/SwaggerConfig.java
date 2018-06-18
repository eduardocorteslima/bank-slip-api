package com.contaazul.bankslip.configurations;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.contaazul.bankslip.rest"))
				.paths(regex("/bankslips.*"))
				.build()
				.apiInfo(apiInfo())
				.enableUrlTemplating(true);
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Bank Slip Api", "Responsible for keeping the data on all XYZ bank tickets.", "API V1", "Terms of service",
				new Contact("Eduardo Cortes", "www.google.com", "eduardocorteslima@gmail.com"),
				"Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
	}
}
