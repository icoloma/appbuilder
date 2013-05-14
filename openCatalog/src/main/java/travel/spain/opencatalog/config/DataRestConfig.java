package travel.spain.opencatalog.config;

import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

public class DataRestConfig extends RepositoryRestMvcConfiguration {
	
	
/*
	  @Override protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
	    
		 config.addResourceMappingForDomainType(Person.class)
	          .addResourceMappingFor("lastName")
	          .setPath("surname"); // Change 'lastName' to 'surname' in the JSON
	    
	    config.addResourceMappingForDomainType(Person.class)
	          .addResourceMappingFor("siblings")
	          .setRel("siblings")
	          .setPath("siblings"); // Pointless in this example,
	                                // but shows how to change 'rel' and 'path' values.
	  }
*/
	}