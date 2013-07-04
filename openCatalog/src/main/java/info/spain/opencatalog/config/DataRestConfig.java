package info.spain.opencatalog.config;

import info.spain.opencatalog.domain.poi.types.BasicPoiType;
import info.spain.opencatalog.rest.BeforeSavePoiValidator;

import org.joda.time.DateTime;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.repository.context.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

@Configuration
@Import(ApiMvcConfig.class)
@ComponentScan(basePackages = "info.spain.opencatalog.rest")
public class DataRestConfig extends RepositoryRestMvcConfiguration {
	
	@Override 
	protected void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener v) {
		  v.addValidator("beforeSave", new BeforeSavePoiValidator());
	}

	@Override
	protected void configureJacksonObjectMapper(ObjectMapper objectMapper) {
		objectMapper.registerModule(new SimpleModule("MyCustomModule") {
			private static final long serialVersionUID = -786299906589974831L;

			@Override
			public void setupModule(SetupContext context) {
				addSerializers(context);
				addDeserializers(context);
			}
			
			private void addSerializers(SetupContext context){
				SimpleSerializers serializers = new SimpleSerializers();
				serializers.addSerializer(DateTime.class, new CustomDateTimeSerializer());
				serializers.addSerializer(BasicPoiType.class, new PoiTypeSerializer());
				context.addSerializers(serializers);
			}
			
			private void addDeserializers(SetupContext context){
				SimpleDeserializers deserializers = new SimpleDeserializers();
				deserializers.addDeserializer(BasicPoiType.class, new PoiTypeDeserializer());
				context.addDeserializers(deserializers);
			}
			
		});
	}

	



}