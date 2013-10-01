package info.spain.opencatalog.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import info.spain.opencatalog.api.PoiResource;
import info.spain.opencatalog.api.PoiResourceAssembler;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.image.PoiImageUtils;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.web.controller.AbstractController;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.Metrics;
import org.springframework.data.mongodb.core.geo.Point;
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
@RequestMapping(value = "/poi")
public class PoiApiController extends AbstractController {
	
	@Autowired 
	PoiRepository poiRepository;
	
	@Autowired
	PoiImageUtils poiImageUtils;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	PoiResourceAssembler poiResourceAssembler;
	
	
	// TODO: HEAD que devuelva los valores validos/permitidos para un PoiType concreto
	// Ej.: BEACH --> data.width, data.longitude, ...
	
	@RequestMapping(value="/search", method = RequestMethod.GET)
	public @ResponseBody ResourceSupport search() throws Exception {
		return getPoiHateoasLinks();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResourceSupport find() throws Exception {
		return getPoiHateoasLinks();
	}
	
	private ResourceSupport getPoiHateoasLinks() throws Exception {
		ResourceSupport resource = new ResourceSupport(){};
		resource.add(linkTo(PoiApiController.class).slash("search").slash("byName").withRel("byName"));
		resource.add(linkTo(PoiApiController.class).slash("search").slash("byLocationNear").withRel("byLocationNear"));
		resource.add(linkTo(PoiApiController.class).slash("search").slash("custom").withRel("custom"));
		return resource;
	}
	
	
	
	/**
	 * Create
	 *  
	 * @throws IOException 
	 * @throws JSONException 
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public void saveBacic( HttpServletRequest req, HttpServletResponse res) throws IOException, JSONException {
		JSONObject json = getJSON(req.getInputStream());
		String jsonType= json.getString("type");
		Class<? extends BasicPoi> clazz = PoiTypeID.valueOf(jsonType).getPoiClass();
		BasicPoi poi = objectMapper.readValue(json.toString(), clazz);
		poi.getSyncInfo()
			.setLastUpdate(null)  // Always override
			.setImported(false)   // Always override 
			.setOriginalId(null)  // Always override 
			.setSync(false);      // Always override 
		savePoi(poi, req,res);
	 }
	
	private JSONObject getJSON(InputStream inputStream) throws  IOException, JSONException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer);
		return new JSONObject(writer.toString());
	}
	 
	
	private void savePoi(BasicPoi poi, HttpServletRequest req, HttpServletResponse res)  {
		 poi.validate();
		 poi = poiRepository.save(poi);
		 res.addHeader("Location", getLocationForChildResource(req, poi.getId()));
	 }	
	 
	/**
	 * Update 
	 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public void update( @PathVariable("id") String idPoi, HttpServletRequest req, HttpServletResponse res) throws IOException, JSONException {
		BasicPoi dbPoi = poiRepository.findOne(idPoi);
	 	if (dbPoi == null) {
	 		throw new NotFoundException("poi", idPoi);
	 	}
	 	JSONObject json = getJSON(req.getInputStream());
	 	Class<? extends BasicPoi> clazz = dbPoi.getPoiType().getId().getPoiClass();
		BasicPoi poi = objectMapper.readValue(json.toString(), clazz);
		// Always override
		poi.setId(dbPoi.getId());
		poi.getSyncInfo()
			.setLastUpdate(dbPoi.getSyncInfo().getLastUpdate())  	// always override
			.setImported(dbPoi.getSyncInfo().isImported())  		// always override
			.setOriginalId(dbPoi.getSyncInfo().getOriginalId());	// always override  
		if (!dbPoi.getSyncInfo().isImported()){
			poi.getSyncInfo().setSync(false);
		}
		savePoi(poi, req,res);
	}
	 
	 
	/**
	 * Delete 
	 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public void deletePoi(@PathVariable("id") String idPoi){
		BasicPoi poi = poiRepository.findOne(idPoi);
		if (poi != null){
			poiImageUtils.deletePoiImages(idPoi);
			poiRepository.delete(idPoi);
		}
		
	}
	
	/**
	 * FindById
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public @ResponseBody PoiResource findById(@PathVariable("id") String id){
		BasicPoi poi = poiRepository.findOne(id);
		if (poi == null ){
			throw new NotFoundException("poi",id);
		}
		return poiResourceAssembler.toResource(poi);
	}
	
	/**
	 * FindByName
	 */
	@RequestMapping(value="/search/byName", method=RequestMethod.GET)
	public @ResponseBody Page<PoiResource>  findByName(@RequestParam("name") String name, @RequestParam(value="page", defaultValue="0") int page,@RequestParam(value="size", defaultValue="5") int size){
		Pageable pageable = new PageRequest(page,size,new Sort("lastModified"));
		Page<BasicPoi> pois = poiRepository.findByNameEsLikeIgnoreCase(name, pageable);
		Page<PoiResource> result = new PageImpl<>(convertToPoiResourceList(pois), pageable, pois.getTotalElements());
		return result;
	}
	
	/**
	 * FindByLocationWithin
	 */
	@RequestMapping(value="/search/byLocationNear", method=RequestMethod.GET)
	public @ResponseBody Page<PoiResource>  findByLocation( 
			@RequestParam("lat") double lat,
			@RequestParam("lng") double lng, 
			@RequestParam("radius") double radius, 
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="size", defaultValue="5") int size){
		
		Pageable pageable = new PageRequest(page,size,new Sort("lastModified"));
		Point location = new Point(lng,lat);
		Distance distance = new Distance(radius,Metrics.KILOMETERS);
		Page<BasicPoi> pois = poiRepository.findByLocationNear(location, distance, pageable);
		Page<PoiResource> result = new PageImpl<>(convertToPoiResourceList(pois), pageable, pois.getTotalElements());
		return result;
	}

	/**
	 * Custom Search
	 */
	@RequestMapping(value="/search/custom")
	public @ResponseBody Page<PoiResource> customSearch(@RequestBody(required=false) SearchQuery searchQuery, @RequestParam(value="page", defaultValue="0") int page,@RequestParam(value="size", defaultValue="5") int size) {
		Pageable pageable = new PageRequest(page,size,new Sort("lastModified"));
		if (searchQuery == null) {
			searchQuery = new SearchQuery();
		}
		Page<BasicPoi> pois = poiRepository.findCustom(searchQuery, pageable);
		Page<PoiResource> result = new PageImpl<>(convertToPoiResourceList(pois), pageable, pois.getTotalElements());
		return result;
	}

	private List<PoiResource> convertToPoiResourceList(Page<BasicPoi> pois) {
		List<PoiResource> result = new ArrayList<PoiResource>();
		for (BasicPoi poi : pois) {
			result.add( poiResourceAssembler.toResource(poi));
		}
		return result;
	}

}
