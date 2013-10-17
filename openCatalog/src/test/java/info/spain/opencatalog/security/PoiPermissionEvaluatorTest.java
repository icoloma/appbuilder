package info.spain.opencatalog.security;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import info.spain.opencatalog.domain.DummyPoiFactory;
import info.spain.opencatalog.domain.DummyUserFactory;
import info.spain.opencatalog.domain.DummyZoneFactory;
import info.spain.opencatalog.repository.UserRepository;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class PoiPermissionEvaluatorTest {
	
	PoiPermissionEvaluator evaluator;
	
	@Before
	public void init(){
		evaluator = new PoiPermissionEvaluator();
		evaluator.userRepository = createMock(UserRepository.class);
	}
	
	@Test
	public void testIsAssignableByObject(){
		assertTrue(evaluator.isAssignableByObject(DummyPoiFactory.APARTMENT));
		assertFalse(evaluator.isAssignableByObject(DummyZoneFactory.ZONE_ALCALA_HENARES));
		assertFalse(evaluator.isAssignableByObject(DummyUserFactory.ROOT));
	}
	
	@Test
	public void testIsAssignableByType(){
		PoiPermissionEvaluator evaluator = new PoiPermissionEvaluator();
		assertTrue(evaluator.isAssignableByType("BasicPoi"));
		assertFalse(evaluator.isAssignableByType("whatever"));
	}
	
	@Test
	public void testUserIsAnonymous(){
		Authentication authentication = new UsernamePasswordAuthenticationToken("anonymousUser", "whatever");
		assertNull(evaluator.getCurrentUser(authentication));
	}

	
	@Test
	public void testEvaluatePoiInUserZonesNoUser(){
		String email = "unknown@example.com";
		org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(email, "whatever", new ArrayList<GrantedAuthority>());
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, "whatever");
		expect(evaluator.userRepository.findByEmail(email)).andReturn(null);
		assertFalse(evaluator.evaluatePoiInUserZones(authentication, null,null));
	}
	
	@Test
	public void testIsPoiInUserZonesNoZones(){
		assertFalse(evaluator.isPoiInUserZones(null,null));
	}

}
