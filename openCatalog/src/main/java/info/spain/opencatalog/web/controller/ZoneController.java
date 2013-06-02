package info.spain.opencatalog.web.controller;


import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.repository.ZoneRepository;
import info.spain.opencatalog.web.form.ZoneForm;

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


}
