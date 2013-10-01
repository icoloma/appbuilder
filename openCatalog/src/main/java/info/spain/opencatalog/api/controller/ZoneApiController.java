package info.spain.opencatalog.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import info.spain.opencatalog.api.ZoneResource;
import info.spain.opencatalog.api.ZoneResourceAssembler;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.repository.ZoneRepository;
import info.spain.opencatalog.web.controller.AbstractController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@ExposesResourceFor(BasicPoi.class)
@RequestMapping(value = "/zone")
public class ZoneApiController extends AbstractController {
	
	@Autowired 
	ZoneRepository zoneRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	ZoneResourceAssembler zoneResourceAssembler;
	
	
	
	// TODO: HEAD que devuelva los valores validos/permitidos para un PoiType concreto
	// Ej.: BEACH --> data.width, data.longitude, ...
	
	@RequestMapping(value="/search", method = RequestMethod.GET)
	public @ResponseBody ResourceSupport search() throws Exception {
		return getZoneHateoasLinks();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResourceSupport find() throws Exception {
		return getZoneHateoasLinks();
	}
	
	private ResourceSupport getZoneHateoasLinks() throws Exception {
		ResourceSupport resource = new ResourceSupport(){};
		resource.add(linkTo(ZoneApiController.class).slash("search").slash("byName").withRel("byName"));
		return resource;
	}
	
	
	/**
	 * FindById
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public @ResponseBody ZoneResource findById(@PathVariable("id") String id){
		Zone zone = zoneRepository.findOne(id);
		if (zone == null ){
			throw new NotFoundException("zone",id);
		}
		return zoneResourceAssembler.toResource(zone);
	}

	
	/**
	 * FindByName
	 */
	@RequestMapping(value="/search/byName", method=RequestMethod.GET)
	public @ResponseBody Page<ZoneResource>  findByName(@RequestParam("name") String name, @RequestParam(value="page", defaultValue="0") int page,@RequestParam(value="size", defaultValue="5") int size){
		Pageable pageable = new PageRequest(page,size,new Sort("lastModified"));
		Page<Zone> zones= zoneRepository.findByNameIgnoreCaseLike(name, pageable);
		Page<ZoneResource> result = new PageImpl<>(convertToZoneResourceList(zones), pageable, zones.getTotalElements());
		return result;
	}
	
	private List<ZoneResource> convertToZoneResourceList(Page<Zone> zones) {
		List<ZoneResource> result = new ArrayList<ZoneResource>();
		for (Zone zone: zones) {
			result.add( zoneResourceAssembler.toResource(zone));
		}
		return result;
	}
	
	/**
	 * Create
	 *  
	 * @throws IOException 
	 * @throws JSONException 
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public void save( @RequestBody Zone zone, HttpServletRequest req, HttpServletResponse res) throws IOException, JSONException {
		zone = zoneRepository.save(zone);
		res.addHeader("Location", getLocationForChildResource(req, zone.getId()));
	 }	
	
	/**
	 * Delete 
	 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public void deleteZone(@PathVariable("id") String id){
		Zone zone = zoneRepository.findOne(id);
		if (zone!= null){
			zoneRepository.delete(id);
		}
	}
		
	 
	/**
	 * Update 
	 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public void update( @RequestBody Zone zone, @PathVariable("id") String id, HttpServletRequest req, HttpServletResponse res) throws IOException, JSONException {
		
		Zone dbZone = zoneRepository.findOne(id);
	 	if (dbZone == null) {
	 		throw new NotFoundException("zone", id);
	 	}
	 	
	 	// Always override
		zone.setId(dbZone.getId());
		zoneRepository.save(zone);
	}

}
