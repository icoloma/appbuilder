package info.spain.opencatalog.web.controller;

import info.spain.opencatalog.domain.MongoDbPopulator;
import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.domain.UserRole;

import java.util.Collection;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.Sets;

public class AbstractControllerTest {
	
	@Autowired
	protected WebApplicationContext wac;
	
	protected MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		MongoDbPopulator.main(new String[]{});
	}
	
	public void loginAs(User user){
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(getPrincipal(user), user.getPassword() ));
		    
	}
	
	private org.springframework.security.core.userdetails.User getPrincipal(User user ){
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), // userName
				user.getPassword(), //password
				true, // enabled
				true, // accountNonExpired 
				true, // credentialsNonExpired 
				true, // accountNonLocked
				getAuthorities(user)
				); 
	}

	private Collection<GrantedAuthority> getAuthorities(User user){
		Collection<GrantedAuthority> result = Sets.newHashSet();
		for (UserRole role : user.getRoles()) {
			result.add(new SimpleGrantedAuthority(role.toString()));
		}
		return result;
	}
	
	
}
