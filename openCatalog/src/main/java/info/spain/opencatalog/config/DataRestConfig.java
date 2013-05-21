package info.spain.opencatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataRestConfig extends RepositoryRestMvcConfiguration {

	@Override
	@Bean
	public ObjectMapper objectMapper() {
		// TODO Auto-generated method stub
		ObjectMapper result = super.objectMapper();
		
//		result.registerModule(new SimpleModule("I18nText"){
//			private static final long serialVersionUID = -786299906589974831L;
//			@Override public void setupModule(SetupContext context) {
//				  SimpleSerializers serializers = new SimpleSerializers();
//				  SimpleDeserializers deserializers = new SimpleDeserializers();
//
//				  serializers.addSerializer(I18nText.class, new I18nTextSerializer());
//				  deserializers.addDeserializer(I18nText.class, new I18nTextDeserializer());
//
//				  context.addSerializers(serializers);
//				  context.addDeserializers(deserializers);
//			      }
//		});
		return result;
	}

	
	
	
	
	
}