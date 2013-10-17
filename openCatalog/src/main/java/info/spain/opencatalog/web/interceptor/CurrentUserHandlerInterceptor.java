package info.spain.opencatalog.web.interceptor;

import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Set the current user as a request attribute
 * @author ehdez
 *
 */
public class CurrentUserHandlerInterceptor implements HandlerInterceptor {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User currentUser = getCurrentUser(SecurityContextHolder.getContext().getAuthentication());
		request.setAttribute("currentUser", currentUser);
		return true;
	}

	User getCurrentUser(Authentication authentication){
		
		if (authentication == null || "anonymousUser".equals(authentication.getPrincipal())) { 
			return null;
		}
		
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		return userRepository.findByEmail(user.getUsername());
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		// Do nothing
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// Do nothing
	}
	
	

}
