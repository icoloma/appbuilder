package info.spain.opencatalog.web;

import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.repository.PoiRepository;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application poi page.
 */
@Controller
@RequestMapping(value = "/admin/poi")
public class PoiController {
	
	@Autowired
	private PoiRepository poiRepository;
	
	@ModelAttribute("page_title")
	public String pageName(){
		return "poi.title";
	}
	
	/**
	 * Pageable POI list
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, @PageableDefaults(sort="name.es") Pageable pageable) {
		Page<Poi>page  = poiRepository.findAll(pageable);
		model.addAttribute("page", page);
		return "admin/poi/poiList";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String update(@Valid Poi poi,BindingResult errors,  Model model) {
		if (errors.hasErrors()){
			model.addAttribute("edit","true");
			return "admin/poi/poi";
		}
		poi = poiRepository.save(poi);
		return "redirect:/admin/poi/" + poi.getId();
	}
	
	@RequestMapping(value="/new")
	public String newPoi(Model model){
		model.addAttribute("poi", new Poi());
		model.addAttribute("edit","true");
		return "admin/poi/poi";
	}

}
