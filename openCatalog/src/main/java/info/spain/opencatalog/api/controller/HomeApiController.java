package info.spain.opencatalog.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeApiController extends AbstractApiController {
	
	@RequestMapping("/")
	public @ResponseBody ResourceSupport home() throws Exception {
		return getHateoasLinks();
	}
	
	@RequestMapping(value="/api")
	public String api() throws Exception {
		/* 
		 * Cuando accedemos a "/api" nos da un 404 Not Found
		 * la soluciñón pasa por hacer una redirección a "/api/"
		 * 
		 * Ojo, si no hacemos esto el cálculo de las URL HATEOAS quedarían sin el /api/_whatever_
		 */
		return "redirect:/api/";
	}
	
	private ResourceSupport getHateoasLinks() throws Exception {
		ResourceSupport resource = new ResourceSupport(){};
		resource.add(linkTo(PoiApiController.class).withRel("poi"));
		resource.add(linkTo(ZoneApiController.class).withRel("zone"));
		return resource;
	}
	

}
