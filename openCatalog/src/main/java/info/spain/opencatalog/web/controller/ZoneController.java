package info.spain.opencatalog.web.controller;


import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.repository.ZoneRepository;
import info.spain.opencatalog.web.form.ZoneForm;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application Zone page.
 */
@Controller
@RequestMapping(value = "/admin/zone")
public class ZoneController extends AbstractController {
	
	@Autowired
	private ZoneRepository zoneRepository;
	
	@Autowired
	private PoiRepository poiRepository;
	
	
	/**
	 * SEARCH
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String search(Model model, @PageableDefaults(sort="name") Pageable pageable, @RequestParam(value="q",required=false) String q) {
		String query = q == null ? "" : q; 
		Page<Zone>page  = zoneRepository.findByNameIgnoreCaseLike(query, pageable);
		model.addAttribute("page", page);
		model.addAttribute("q", query);
		return "admin/zone/zoneList";
	}

	
	/**
	 * SHOW
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public String show( @PathVariable("id") String id, Model model) {
		Zone zone  = zoneRepository.findOne(id);
		if (zone == null){
			throw new NotFoundException("zone", id);
		}
		model.addAttribute("zone", new ZoneForm(zone));
		return "admin/zone/zone";
	}
	
	
	/**
	 * CREATE 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("zone") ZoneForm zoneForm ,BindingResult errors,  Model model) {
		if (errors.hasErrors()){
			return "admin/zone/zone";
		}
		Zone zone = zoneRepository.save(zoneForm.getZone());
		model.addAttribute(INFO_MESSAGE, "message.item.created" ) ;
		return "redirect:/admin/zone/" + zone.getId();
	}
	
	/**
	 * EMPTY FORM 
	 */
	@RequestMapping(value="/new")
	public String newZone(Model model){
		model.addAttribute("zone", new ZoneForm());
		return "admin/zone/zone";
	}

	
	/**
	 * UPDATE
	 */
	@RequestMapping( value="/{id}", method=RequestMethod.PUT)
	public String update(@Valid @ModelAttribute("zone") ZoneForm zoneForm,BindingResult errors,  Model model, @PathVariable("id") String id) {
		
		if (errors.hasErrors()){
			return "admin/zone/zone";
		}
		
		Zone zone = zoneForm.getZone();
		zone.setId(id);
		
		zoneRepository.save(zone);
		model.addAttribute(INFO_MESSAGE,  "message.item.updated") ;
		return "redirect:/admin/zone/" + id;
	}
	
	/**
	 * DELETE
	 */
	@RequestMapping( value="/{id}", method=RequestMethod.DELETE)
	public String delete( @PathVariable("id") String id, Model model) {
		zoneRepository.delete(id);
		model.addAttribute(INFO_MESSAGE, "message.item.deleted" ) ;
		return "redirect:/admin/zone/";
	}
	
	/**
	 * ZONE POIs
	 */
	@RequestMapping( value="/{id}/poi")
	public String showPois(@PathVariable("id") String id, Model model){
		show(id,model);
		List<Poi> poiList = poiRepository.findWithInZone(id);
		model.addAttribute("markers", getMarkers(poiList));
		return "admin/zone/zonePoi";
	}

	private List<Marker> getMarkers(List<Poi> poiList){
		List<Marker> result = new ArrayList<>();
		for (Poi poi : poiList) {
			result.add( new Marker(poi));
		}
		return result;
	}
	
	public class Marker {
		String id;
		double lat;
		double lng;
		String name;

		public Marker( Poi poi){
			this.id =poi.getId();
			this.lat=poi.getLocation().getLat();
			this.lng=poi.getLocation().getLng();
			this.name=poi.getName().getEs();
		}
		public String toString(){
			return "{\"id\":\"" + id + "\", \"lat\":" + lat + ", \"lng\":" + lng + ", \"name\" :\"" + name + "\"}";
		}
		public String getId() {
			return id;
		}
		public double getLat() {
			return lat;
		}
		public double getLng() {
			return lng;
		}
		public String getName() {
			return name;
		}
		
	}

}