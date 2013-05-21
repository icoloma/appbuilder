package info.spain.opencatalog.web;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application poi page.
 */
@Controller
public class PoiController {

	@RequestMapping(value = "/admin/poi", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		model.addAttribute("page_title","poi.title");
		return "admin/poi/poiList";
	}

}
