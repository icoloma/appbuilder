package info.spain.opencatalog.web;

import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.repository.PoiRepository;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, @PageableDefaults(sort="name.es") Pageable pageable) {
		Page<Poi>page  = poiRepository.findAll(pageable);
		model.addAttribute("page_title","poi.title");
		model.addAttribute("page", page);
		return "admin/poi/poiList";
	}

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") String id, Model model, @PageableDefaults(sort="name.es") Pageable pageable) {
		Poi poi  = poiRepository.findOne(id);
		model.addAttribute("page_title","poi.title");
		model.addAttribute("poi", poi);
		return "admin/poi/poi";
	}
}
