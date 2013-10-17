package info.spain.opencatalog.security;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import info.spain.opencatalog.domain.DummyUserFactory;
import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;


public class RepositoryUserDetailsServiceTest {
	
	RepositoryUserDetailsService repositoryUserDetailsService;
	UserRepository userRepository;
	
	@Before
	public void init(){
		userRepository = createMock(UserRepository.class);
		repositoryUserDetailsService = new RepositoryUserDetailsService();
		repositoryUserDetailsService.userRepository = userRepository;
	}
	
	@Test
	public void testLoadUserByUsernameIsNull() {
		expect( userRepository.findByEmail("unknowedUser")).andReturn(null);
		replay(userRepository);
		assertNull(repositoryUserDetailsService.loadUserByUsername("unknowedUser"));
		verify(userRepository);
	}
	
	@Test
	public void testLoadUserByUsername() {
		User root = DummyUserFactory.ROOT;
		expect( userRepository.findByEmail(root.getEmail())).andReturn(root);
		replay(userRepository);
		UserDetails result = repositoryUserDetailsService.loadUserByUsername(root.getEmail()); 
		assertNotNull(result);
		assertEquals(root.getEmail(), result.getUsername());
		
	}

}
