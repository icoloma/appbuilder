package info.spain.opencatalog.web.controller;

import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.repository.UserRepository;

import java.text.Normalizer;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractUIController extends AbstractController {
	
	public static final String INFO_MESSAGE = "infoMessage";
	
	@Autowired
	protected MessageSource messageSource;
	
	@Autowired
	protected UserRepository userRepository;
	
	/**
	 * Make current user available to all requests
	 * @param request
	 * @return current User
	 */
	@ModelAttribute("currentUser")
	public User currentUser(HttpServletRequest request){
		return (User) request.getAttribute("currentUser");
	}
	
	/**
	 * Añade el parámetro "infoMessage" si existe y hay traducción al Model
	 * Esto se hace par poder mostrar mensajes tras un redirect.
	 */
	@ModelAttribute(value=INFO_MESSAGE)
	public void messages(@RequestParam(required=false) String infoMessage, Locale locale, Model model){
		messageSource.getMessage(infoMessage, new Object[]{}, infoMessage, locale);
		model.addAttribute(INFO_MESSAGE, infoMessage);
	}
	
	/** Normaliza un texto para poder compararlo en las ordenaciones */ 
	protected String normalizeText(String text){
		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase();
	}
	
}
