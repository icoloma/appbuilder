package info.spain.opencatalog.config;

import java.io.IOException;

import org.joda.time.DateTime;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

@Configuration
@Import(ApiMvcConfig.class)
@ComponentScan(basePackages = "info.spain.opencatalog.rest")
public class DataRestConfig extends RepositoryRestMvcConfiguration {

	@Override
	protected void configureJacksonObjectMapper(ObjectMapper objectMapper) {
		objectMapper.registerModule(new SimpleModule("MyCustomModule") {
			private static final long serialVersionUID = -786299906589974831L;

			@Override
			public void setupModule(SetupContext context) {
				SimpleSerializers serializers = new SimpleSerializers();
				serializers.addSerializer(DateTime.class, new CustomDateTimeSerializer());
				context.addSerializers(serializers);
				
			}
		});
	}

	public class CustomDateTimeSerializer extends JsonSerializer<DateTime> {

		@Override
		public Class<DateTime> handledType() {
			return DateTime.class;
		}

		@Override
		public void serialize(DateTime dateTime, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
			jgen.writeString(dateTime.toString()); // ISO8601
		}
	}


}