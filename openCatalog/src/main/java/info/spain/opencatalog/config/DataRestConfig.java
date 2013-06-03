package info.spain.opencatalog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@Configuration
@Import( ApiMvcConfig.class)
@ComponentScan( basePackages="info.spain.opencatalog.rest" )
public class DataRestConfig extends RepositoryRestMvcConfiguration  {
	
	
//  FIXME: No lo registra bien, hay que usar @ConvertWith en los métodos de PoiRepository
//	@Override
//	protected void configureConversionService(ConfigurableConversionService conversionService) {
//		conversionService.addConverter(String[].class, Poi.class, new PointConverter());
//		conversionService.addConverter(String[].class, Distance.class, new DistanceConverter());
//	}
	

	
}