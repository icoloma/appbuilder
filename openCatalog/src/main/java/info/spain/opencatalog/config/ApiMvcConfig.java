package info.spain.opencatalog.config;

import info.spain.opencatalog.web.filter.ListPaginationHandlerInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@ComponentScan("info.spain.opencatalog.web.util")
@Configuration
public class ApiMvcConfig extends WebMvcConfigurationSupport {
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor( listPaginationHandlerInterceptor());
	}
	
	@Bean
	public HandlerInterceptor listPaginationHandlerInterceptor(){
		return new ListPaginationHandlerInterceptor();
	}
	
}
