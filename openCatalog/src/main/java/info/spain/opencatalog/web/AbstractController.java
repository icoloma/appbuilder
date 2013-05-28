package info.spain.opencatalog.web;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractController {
	
	public static final String INFO_MESSAGE = "infoMessage";
	
	@Autowired
	protected MessageSource messageSource;
	
	/**
	 * Añade el parámetro "infoMessage" si existe y hay traducción al Model
	 * Esto se hace par poder mostrar mensajes tras un redirect.
	 */
	@ModelAttribute(value=INFO_MESSAGE)
	public void messages(@RequestParam(required=false) String infoMessage, Locale locale, Model model){
		try {
			messageSource.getMessage(infoMessage, new Object[]{} , locale);
			model.addAttribute(INFO_MESSAGE, infoMessage);
		} catch( Exception e ){
			// do nothing, not valid key
		}
	}

}
