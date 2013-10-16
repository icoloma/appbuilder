package info.spain.opencatalog.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Redirige a las páginas de login y login con error de autenticación
 * 
 * @author ehdez
 */
@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/login-error")
	public String logout(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

}
