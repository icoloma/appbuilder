package info.spain.opencatalog.web.interceptor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.repository.UserRepository;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class CurrentUserHandlerInterceptorTest {
	
	
	@Test
	public void testAuthenticationIsNull(){
		CurrentUserHandlerInterceptor interceptor = new CurrentUserHandlerInterceptor();
		assertNull(interceptor.getCurrentUser(null));
	}


	@Test
	public void testAuthenticationIsAnonymousUser(){
		CurrentUserHandlerInterceptor interceptor = new CurrentUserHandlerInterceptor();
		Authentication authentication = new UsernamePasswordAuthenticationToken("anonymousUser", "whatever");
		assertNull(interceptor.getCurrentUser(authentication));
	}

	@Test
	public void testAuthenticationIsUser(){
		String email = "user@example.com";
		User user = new User().setEmail(email);
		
		UserRepository userRepository = createMock(UserRepository.class);
		
		expect(userRepository.findByEmail(email)).andReturn(user);

		replay(userRepository);
		
		CurrentUserHandlerInterceptor interceptor = new CurrentUserHandlerInterceptor();
		interceptor.userRepository = userRepository;
		
		
		Authentication authentication = new UsernamePasswordAuthenticationToken( new org.springframework.security.core.userdetails.User(email,"whatever", new ArrayList<GrantedAuthority>()), "whatever");
		User result = interceptor.getCurrentUser(authentication);
		assertEquals(user, result);
		
		verify(userRepository);
	}



}
