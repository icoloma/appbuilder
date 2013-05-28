package info.spain.opencatalog.web;

import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.repository.PoiRepository;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	 * PAGEABLE LIST
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, @PageableDefaults(sort="name.es") Pageable pageable) {
		Page<Poi>page  = poiRepository.findAll(pageable);
		model.addAttribute("page", page);
		return "admin/poi/poiList";
	}
	
	/**
	 * CREATE 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Poi poi,BindingResult errors,  Model model) {
		if (errors.hasErrors()){
			return "admin/poi/poi";
		}
		poi = poiRepository.save(poi);
		model.addAttribute(INFO_MESSAGE, "message.item.created" ) ;
		return "redirect:/admin/poi/" + poi.getId();
	}
	
	/**
	 * EMPTY FORM 
	 */
	@RequestMapping(value="/new")
	public String newPoi(Model model){
		model.addAttribute("poi", new Poi());
		return "admin/poi/poi";
	}
	
	@RequestMapping(value="/tags")
	public @ResponseBody String getAllTags(Locale locale){
		StringBuffer result = new StringBuffer("{\"tags\":[");
		Tag[] values = Tag.values();
		for (int i = 0; i < values.length; i++) {
			Tag tag = values[i];
			if (i>0){
				result.append(",");
			}
//			result.append("{\"tag\":\"").append(messageSource.getMessage(tag.toString(), new Object[]{}, locale)).append("\"}");	
			result.append("{\"tag\":\"").append(tag.toString()).append("\"}");
		}
		result.append("]}");
		return result.toString();
		
	}

}
