package info.spain.opencatalog.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import info.spain.opencatalog.api.ZoneResource;
import info.spain.opencatalog.api.ZoneResourceAssembler;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.repository.ZoneRepository;

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
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
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
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/zone")
public class ZoneApiController extends AbstractApiController {
	
	@Autowired 
	ZoneRepository zoneRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	ZoneResourceAssembler zoneResourceAssembler;
	

	/**
	 * Search
	 */
	@RequestMapping(value="/search", method = RequestMethod.GET)
	public @ResponseBody ResourceSupport search() throws Exception {
		ResourceSupport resource = new ResourceSupport(){};
		resource.add(getSearchLinks());
		return resource;
	}
	
	/**
	 * FindAll
	 */
	@RequestMapping(method = RequestMethod.GET, produces={"application/json"})
	public @ResponseBody PagedResources<ZoneResource>  findAll( @RequestParam(value="page", defaultValue="0") int page,@RequestParam(value="size", defaultValue=DEFAULT_API_PAGE_SIZE) int size) throws Exception {
		Pageable pageable = new PageRequest(page,size,new Sort("lastModified"));
		Page<Zone> zones = zoneRepository.findAll(pageable);
		Page<ZoneResource> result = new PageImpl<>(convertToZoneResourceList(zones), pageable, zones.getTotalElements());
		PageMetadata metadata = new PagedResources.PageMetadata(result.getSize(), result.getNumber(), result.getTotalElements());
		PagedResources<ZoneResource> pagedResources = new PagedResources<ZoneResource>(result.getContent(), metadata);
		pagedResources.add(getSearchLinks());
		return pagedResources;
	}
	
	private Iterable<Link> getSearchLinks() throws Exception {
		List<Link> result = Lists.newArrayList();
		result.add(linkTo(ZoneApiController.class).slash("search").slash("byName").withRel("byName"));
		return result;
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
	public @ResponseBody Page<ZoneResource>  findByName(@RequestParam("name") String name, @RequestParam(value="page", defaultValue="0") int page,@RequestParam(value="size", defaultValue=DEFAULT_API_PAGE_SIZE) int size){
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
