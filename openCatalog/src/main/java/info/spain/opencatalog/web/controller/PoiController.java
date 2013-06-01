package info.spain.opencatalog.web.controller;


import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.web.form.PoiForm;

import java.util.Locale;

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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application poi page.
 */
@Controller
@RequestMapping(value = "/admin/poi")
public class PoiController extends AbstractController {
	
	@Autowired
	private PoiRepository poiRepository;
	
	
	/**
	 * SEARCH
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String search(Model model, @PageableDefaults(sort="name.es") Pageable pageable, @RequestParam(value="q",required=false) String q) {
		String query = q == null ? "" : q; 
		Page<Poi>page  = poiRepository.findByNameEsLike(query, pageable);
		model.addAttribute("page", page);
		model.addAttribute("q", query);
		return "admin/poi/poiList";
	}

	
	/**
	 * SHOW
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public String show( @PathVariable("id") String id, Model model) {
		Poi poi  = poiRepository.findOne(id);
		if (poi == null){
			throw new NotFoundException("poi", id);
		}
		PoiForm poiForm = new PoiForm(poi);
		model.addAttribute("poi", poiForm);
		return "admin/poi/poi";
	}
	
	/**
	 * CREATE 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("poi") PoiForm poiForm ,BindingResult errors,  Model model) {
		if (errors.hasErrors()){
			return "admin/poi/poi";
		}
		Poi poi = poiRepository.save(poiForm.getPoi());
		model.addAttribute(INFO_MESSAGE, "message.item.created" ) ;
		return "redirect:/admin/poi/" + poi.getId();
	}
	
	/**
	 * EMPTY FORM 
	 */
	@RequestMapping(value="/new")
	public String newPoi(Model model){
		model.addAttribute("poi", new PoiForm());
		return "admin/poi/poi";
	}

	
	/**
	 * UPDATE
	 */
	@RequestMapping( value="/{id}", method=RequestMethod.PUT)
	public String update(@Valid @ModelAttribute("poi") PoiForm poiForm,BindingResult errors,  Model model, @PathVariable("id") String id) {
		
		if (errors.hasErrors()){
			return "admin/poi/poi";
		}

		Poi poi = poiForm.getPoi();
		poi.setId(id);
		
		poiRepository.save(poi);
		model.addAttribute(INFO_MESSAGE,  "message.item.updated") ;
		return "redirect:/admin/poi/" + id;
	}
	
	/**
	 * DELETE
	 */
	@RequestMapping( value="/{id}", method=RequestMethod.DELETE)
	public String delete( @PathVariable("id") String id, Model model) {
		poiRepository.delete(id);
		model.addAttribute(INFO_MESSAGE, "message.item.deleted" ) ;
		return "redirect:/admin/poi/";
	}


	
	/**
	 * TAG LIST
	 * @param term
	 * Generates a list of tags [ {"id":"...", "label":"...", "value":"..." } ]
	 * Used by jquery.tagedit.js
	 */
	@RequestMapping(value="/tags", produces="application/json")
	public @ResponseBody String getAllTags(@RequestParam String term,Locale locale){
		StringBuffer result = new StringBuffer("[");
		Tag[] values = Tag.values();
		boolean empty = true;
		for (int i = 0; i < values.length; i++) {
			Tag tag = values[i];
			String txt = messageSource.getMessage("tags." + tag.toString(), new Object[]{}, locale);
			if (txt.toLowerCase().contains(term.toLowerCase())){
				if (i>0 && ! empty){
					result.append(",");
				}
				result.append("{\"id\":\"").append(tag.getId()).append("\", \"label\":\"").append(txt).append("\", \"value\":\"").append(txt).append("\"}");
				empty = false;
			}
		}
		result.append("]");
		return result.toString();
		
	}

}
