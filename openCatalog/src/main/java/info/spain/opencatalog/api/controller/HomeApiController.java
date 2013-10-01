package info.spain.opencatalog.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.web.controller.AbstractController;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ExposesResourceFor(BasicPoi.class)
@RequestMapping(value = "/")
public class HomeApiController extends AbstractController {
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResourceSupport find() throws Exception {
		return getHateoasLinks();
	}
	
	private ResourceSupport getHateoasLinks() throws Exception {
		ResourceSupport resource = new ResourceSupport(){};
		resource.add(linkTo(PoiApiController.class).withRel("poi"));
		resource.add(linkTo(ZoneApiController.class).withRel("zone"));
		return resource;
	}
	

}
