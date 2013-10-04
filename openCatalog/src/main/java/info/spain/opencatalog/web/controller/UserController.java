package info.spain.opencatalog.web.controller;


import info.spain.opencatalog.domain.ApiKeyGenerator;
import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.repository.UserRepository;
import info.spain.opencatalog.repository.ZoneRepository;
import info.spain.opencatalog.validator.UserFormValidator;
import info.spain.opencatalog.web.form.UserForm;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application User page.
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserController extends AbstractUIController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ZoneRepository zoneRepository;
	
	
	private UserFormValidator userFormValidator = new UserFormValidator();
	
	
	/**
	 * SEARCH
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String search(Model model, @PageableDefaults(sort="username") Pageable pageable, @RequestParam(value="q",required=false) String q) {
		String query = q == null ? "" : q; 
		Page<User>page  = userRepository.findByEmailIgnoreCaseLike(query, pageable);
		model.addAttribute("page", page);
		model.addAttribute("q", query);
		return "admin/user/userList";
	}
	
	/**
	 * SHOW
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public String show( @PathVariable("id") String id, Model model) {
		User user = userRepository.findOne(id);
		if (user == null){
			throw new NotFoundException("user", id);
		}
		
		model.addAttribute("user", new UserForm(user));
		addCommonDataToModel(user, model);
		return "admin/user/user";
	}
	
	/**
	 * EMPTY FORM 
	 */
	@RequestMapping(value="/new")
	public String newUser(Model model){
		model.addAttribute("user", new UserForm());
		addCommonDataToModel(null, model);
		return "admin/user/user";
	}
	
	/**
	 * CREATE 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("user") UserForm userForm ,BindingResult errors,  Model model) {

		validateOnCreate(userForm, errors);
		if (errors.hasErrors()){
			addCommonDataToModel(userForm, model);
			return "admin/user/user";
		}
		
		User user = userRepository.save(userForm.getUser().setApiKey(ApiKeyGenerator.newKey()));
		model.addAttribute(INFO_MESSAGE, "message.item.created" ) ;
		return "redirect:/admin/user/" + user.getId();
	}
	

	private void validateOnCreate(UserForm userForm, BindingResult errors){
		if (StringUtils.isEmpty(userForm.getEmail())){
			errors.rejectValue("email", "user.email.error.empty");
		} else {
			User dbUser = userRepository.findByEmail(userForm.getEmail());
			if (dbUser != null){
				errors.rejectValue("email", "user.email.error.alreadyExists");
			}
		}
		userFormValidator.validate(userForm, errors);
	}


	/**
	 * UPDATE
	 */
	@RequestMapping( value="/{id}", method=RequestMethod.PUT)
	public String update(@Valid @ModelAttribute("user") UserForm userForm,BindingResult errors,  Model model, @PathVariable("id") String id) {
		
		User dbUser = userRepository.findOne(id);

		// Si no nos ha proporcionado passwords, dejamos el que tenía
		if (StringUtils.isEmpty(userForm.getPassword())){
			userForm.setPassword(dbUser.getPassword());
			userForm.setRepassword(dbUser.getPassword());
		}
		
		validateOnUpdate(userForm, dbUser, errors);
		
		if (errors.hasErrors()){
			addCommonDataToModel(userForm, model);
			return "admin/user/user";
		}
		
		User user = userForm.getUser().setId(id);
		User.copyData(dbUser,user);
		
		userRepository.save(dbUser);
		model.addAttribute(INFO_MESSAGE,  "message.item.updated") ;
		return "redirect:/admin/user/" + id;
	}
	
	private void validateOnUpdate(UserForm userForm, User dbUser, BindingResult errors){
		// No queremos sobreescribir el APIKey
		userForm.setApiKey(dbUser.getApiKey());
		
		// No queremos sobreescribir el email
		userForm.setEmail(dbUser.getEmail());
		
		userFormValidator.validate(userForm, errors);
	}
	
	/**
	 * DELETE
	 */
	@RequestMapping( value="/{id}", method=RequestMethod.DELETE)
	public String delete( @PathVariable("id") String id, Model model) {
		userRepository.delete(id);
		model.addAttribute(INFO_MESSAGE, "message.item.deleted" ) ;
		return "redirect:/admin/user/";
	}
	
	/**
	 * Añade al modelo:
	 *  - las zonas del usuario (para mostrarlas)
	 * 	- todas las zonas (para que el usuario las pueda seleccionar
	 */
	private void addCommonDataToModel(User user, Model model){
		if (user != null && user.getIdZones() != null && user.getIdZones().size() > 0){
			model.addAttribute("zones", zoneRepository.findByIds(user.getIdZones().toArray(new String[]{})));
		}
		// TODO: Si el número de zonas crece mucho, habría que pedirlas mediante un Autocomplete
		model.addAttribute("allZones", zoneRepository.findAll());
		
	}
	
	
}
