package info.spain.opencatalog.security;

import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.domain.UserRole;
import info.spain.opencatalog.repository.UserRepository;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.google.common.collect.Sets;

/**
 *  author ehdez
 */
public class RepositoryUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			return null;
		}
		
		
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
