package info.spain.opencatalog.config;

import info.spain.opencatalog.domain.poi.types.BasicPoiType;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.DefaultFormattingConversionService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

@Configuration
public class ApiConfig {

	@Bean 
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		configureJacksonObjectMapper(objectMapper);
		return objectMapper;
	}
	
	
	
	@Bean 
	public DefaultFormattingConversionService defaultConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		return conversionService;
	}
	
	
	
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
				deserializers.addDeserializer(DateTime.class, new CustomDateTimeDeserializer());
				context.addDeserializers(deserializers);
			}
			
		});
	}
	
}
