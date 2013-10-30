package info.spain.opencatalog.web.controller;

import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.repository.ZoneRepository;
import info.spain.opencatalog.web.form.ZoneForm;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/zone")
public class ZoneController extends AbstractUIController{
	
	@Autowired
	protected ZoneRepository zoneRepository;
	
	@Autowired
	protected PoiRepository poiRepository;
	
	/**
	 * SEARCH
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String search(Model model, @PageableDefaults(sort="name") Pageable pageable, @RequestParam(value="q",required=false) String q){
		String query = q == null ? "" : q; 
		Page<Zone>page  = zoneRepository.findByNameIgnoreCaseLike(query, pageable);
		model.addAttribute("page", page);
		model.addAttribute("q", query);
		return "zone/zoneList";
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
		return "zone/zone";
	}
	
	/**
	 * ZONE POIs
	 */
	@RequestMapping(value="/{id}/poi")
	public String showPois(@PathVariable("id") String id, Model model, Principal principal){
		show(id,model);
		List<BasicPoi> poiList = poiRepository.findWithInZone(id);
		model.addAttribute("poiList", filterPoisIfNotAuthenticated(poiList, principal));
		return "zone/zonePoi";
	}
	
	/**
	 * 
	 * @param pois
	 * @param auth
	 * @return The published Pois if the user is not authenticated else all pois
	 */
	private List<BasicPoi> filterPoisIfNotAuthenticated(List<BasicPoi> pois, Principal principal){
		if (principal == null || principal.getName().equals("anonymousUser")){
			List<BasicPoi> result = Lists.newArrayList();
			for (BasicPoi p : pois) {
				if (p.isPublished()){
					result.add(p);
				}
			}
			return result;
		}
		return pois;
	}
	

}
