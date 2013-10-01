package info.spain.opencatalog.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import info.spain.opencatalog.api.controller.PoiApiController;
import info.spain.opencatalog.api.controller.PoiImageApiController;
import info.spain.opencatalog.domain.poi.BasicPoi;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PoiResourceAssembler extends ResourceAssemblerSupport<BasicPoi, PoiResource> {

	public PoiResourceAssembler(){
		super(PoiApiController.class, PoiResource.class);
	}

	@Override
	public PoiResource toResource(BasicPoi poi) {
		PoiResource res = createResourceWithId(poi.getId(), poi);
		res.add(linkTo(PoiImageApiController.class, poi.getId()).withRel("image"));
		res.copyFrom(poi);
		return res;
	}

}
