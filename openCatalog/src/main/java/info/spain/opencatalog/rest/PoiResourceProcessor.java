package info.spain.opencatalog.rest;

import info.spain.opencatalog.domain.Poi;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

/**
 * Permite modificar la representación HATEOAS del Poi, añadiéndole un link a la imagen
 * @author ehdez
 * 
 */
@Component
public class PoiResourceProcessor implements ResourceProcessor<Resource<Poi>> {
	
	@Override
	public Resource<Poi> process(Resource<Poi> resource) {
		Poi poi = resource.getContent();
		resource.add(new Link("poi/" + poi.getId() + "/image", "image"));
		return resource;
	}
}