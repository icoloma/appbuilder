package info.spain.opencatalog.web;

import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.repository.PoiRepository;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles CRUD requests for the application poi page.
 */
@Controller
@RequestMapping(value = "/admin/poi/{id}")
public class PoiCRUDController extends AbstractController {
	
	@Autowired
	private PoiRepository poiRepository;
	
	
	@ModelAttribute()
	public Poi loadPoi(@PathVariable("id") String id){
		Poi poi  = poiRepository.findOne(id);
		return poi;
	}
	
	
	
	/**
	 * SHOW
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String show(String id, Model model, @PageableDefaults(sort="name.es") Pageable pageable) {
		model.addAttribute("tags", Tag.values());
		return "admin/poi/poi";
	}

	/**
	 * PRE-EDIT 
	 */
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String id, Model model, @PageableDefaults(sort="name.es") Pageable pageable) {
		return "admin/poi/poi";
	}

	/**
	 * UPDATE
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid Poi poi,BindingResult errors,  Model model, @PathVariable("id") String id) {
		if (errors.hasErrors()){
			return "admin/poi/poi";
		}
		poiRepository.save(poi);
		model.addAttribute(INFO_MESSAGE,  "message.item.updated") ;
		return "redirect:/admin/poi/" + id;
	}
}
