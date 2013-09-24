package info.spain.opencatalog.api;

import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.image.PoiImageUtils;

import java.util.List;

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
		addImageLinks(resource);
		return resource;
	}
	
	
	
	private void addImageLinks(Resource<BasicPoi> resource){
		BasicPoi poi = resource.getContent();
		resource.add( new Link("poi/" + poi.getId() + "/image" , "image"));
		//List<String> poiImages = poiImageUtils.getPoiImageFilenames(poi.getId());
//		for (int i=0; i<poiImages.size(); i++) {
//			String image = poiImages.get(i);
//			resource.add( new Link("poi/" + poi.getId() + "/image/" + image, "image[" + i + "]"));
//			
//		}
	}
}
