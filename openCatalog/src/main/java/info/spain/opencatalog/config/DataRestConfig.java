package info.spain.opencatalog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@Import( RepositoryRestMvcConfiguration.class)
@ComponentScan( basePackages="info.spain.opencatalog.rest" )
public class DataRestConfig extends WebMvcConfigurationSupport {

	
}