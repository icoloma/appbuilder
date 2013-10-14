package info.spain.opencatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;

@Configuration
public class ThymeleafConfig   {
	
	
	@Bean
	public ServletContextTemplateResolver templateResolver(){
		ServletContextTemplateResolver result = new ServletContextTemplateResolver();
		result.setPrefix("/WEB-INF/thymeleaf/");
		result.setSuffix(".html");
	    result.setTemplateMode("HTML5");
	    result.setCacheable(false);
	    return result;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine(){
		SpringTemplateEngine result = new SpringTemplateEngine();
		result.addDialect(new DataAttributeDialect());
		result.addDialect(new SpringSecurityDialect());
		result.setTemplateResolver(templateResolver());
		return result;
	}
	
	@Bean
	public ThymeleafViewResolver thymeleafViewResolver(){
		ThymeleafViewResolver result = new ThymeleafViewResolver();
		result.setTemplateEngine(templateEngine());
		return result;	
	}
  
	
}