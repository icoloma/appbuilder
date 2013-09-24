package info.spain.opencatalog.api;

import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.image.PoiImageUtils;

import org.springframework.beans.factory.annotation.Autowired;
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
public class PoiResourceProcessor implements ResourceProcessor<Resource<BasicPoi>> {
	
	@Autowired
	private PoiImageUtils poiImageUtils;
	
	@Override
	public Resource<BasicPoi> process(Resource<BasicPoi> resource) {
		addImageLink(resource);
		return resource;
	}
	
	private void addImageLink(Resource<BasicPoi> resource){
		BasicPoi poi = resource.getContent();
		resource.add( new Link("poi/" + poi.getId() + "/image" , "image"));
	}
}
