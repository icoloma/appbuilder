package info.spain.opencatalog.api;

import info.spain.opencatalog.api.controller.ZoneApiController;
import info.spain.opencatalog.domain.Zone;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ZoneResourceAssembler extends ResourceAssemblerSupport<Zone, ZoneResource> {

	public ZoneResourceAssembler(){
		super(ZoneApiController.class, ZoneResource.class);
	}

	@Override
	public ZoneResource toResource(Zone zone) {
		ZoneResource res = createResourceWithId(zone.getId(), zone);
		res.copyFrom(zone);
		return res;
	}

}
