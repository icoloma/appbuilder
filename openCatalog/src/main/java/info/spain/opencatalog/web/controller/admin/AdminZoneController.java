package info.spain.opencatalog.web.controller.admin;


import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.web.controller.ZoneController;
import info.spain.opencatalog.web.form.ZoneForm;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application Admin Zone page.
 */
@Controller
@RequestMapping(value = "/admin/zone")
public class AdminZoneController extends ZoneController {
	

	/**
	 * SHOW
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public String show( @PathVariable("id") String id, Model model) {
		super.show(id, model);
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
		
		Zone zone = zoneForm.getZone().setId(id);
		Zone dbZone = zoneRepository.findOne(id); 
		Zone.copyData(dbZone, zone);
		
		zoneRepository.save(dbZone);
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
	 * My Zones
	 */
	@RequestMapping(value="/myZones", method = RequestMethod.GET)
	public String userZones(Model model, HttpServletRequest request) {
		User user = currentUser(request);
		if (user.getIdZones() == null || user.getIdZones().isEmpty()){
			model.addAttribute("userZones", new ArrayList<Zone>());
		} else {
			model.addAttribute("userZones", zoneRepository.findAll(user.getIdZones()));
		}
		return "admin/zone/myZones";
	}

	

	
}
