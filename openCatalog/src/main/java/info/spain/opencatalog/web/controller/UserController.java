package info.spain.opencatalog.web.controller;


import info.spain.opencatalog.domain.ApiKeyGenerator;
import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.repository.UserRepository;
import info.spain.opencatalog.validator.UserFormValidator;
import info.spain.opencatalog.web.form.UserForm;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
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

/**
 * Handles requests for the application User page.
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserController extends AbstractController {
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	
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
		return "admin/user/user";
	}
	
	/**
	 * EMPTY FORM 
	 */
	@RequestMapping(value="/new")
	public String newUser(Model model){
		model.addAttribute("user", new UserForm());
		return "admin/user/user";
	}
	
	/**
	 * CREATE 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("user") UserForm userForm ,BindingResult errors,  Model model) {
		if (errors.hasErrors()){
			return "admin/user/user";
		}
		User user = userRepository.save(userForm.getUser().setApiKey(ApiKeyGenerator.newKey()));
		model.addAttribute(INFO_MESSAGE, "message.item.created" ) ;
		return "redirect:/admin/user/" + user.getId();
	}
	


	/**
	 * UPDATE
	 */
	@RequestMapping( value="/{id}", method=RequestMethod.PUT)
	public String update(@Valid @ModelAttribute("user") UserForm userForm,BindingResult errors,  Model model, @PathVariable("id") String id) {
		
		// FIXME: automatizar custom validation
		new UserFormValidator().validate(userForm, errors);
		
		if (errors.hasErrors()){
			return "admin/user/user";
		}
		User dbUser = userRepository.findOne(id);
		
		User user = userForm.getUser();
		
		// No queremos sobreescribir el APIKey
		user.setApiKey(dbUser.getApiKey());

		// No queremos sobreescribir el email
		user.setEmail(dbUser.getEmail());

		if (StringUtils.isBlank(user.getPassword())){
			// No queremos sobreescribir el password si no se ha especificado
			user.setPassword(dbUser.getPassword());
		}
		
		User.copyData(dbUser,user);
		
		userRepository.save(dbUser);
		model.addAttribute(INFO_MESSAGE,  "message.item.updated") ;
		return "redirect:/admin/user/" + id;
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
	
	
}
