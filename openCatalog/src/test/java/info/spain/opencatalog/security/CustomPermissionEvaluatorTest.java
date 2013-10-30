package info.spain.opencatalog.security;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import info.spain.opencatalog.domain.DummyPoiFactory;
import info.spain.opencatalog.domain.DummyUserFactory;
import info.spain.opencatalog.domain.DummyZoneFactory;
import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.repository.UserRepository;
import info.spain.opencatalog.repository.ZoneRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.google.common.collect.Lists;

public class CustomPermissionEvaluatorTest {
	
	
	ZoneRepository zoneRepository;
	UserRepository userRepository;
	PoiRepository poiRepository;
	
	PoiPermissionEvaluator poiPermissionEvaluator;
	
	ClassTypePermissionEvaluation evaluatorPoi;
	ClassTypePermissionEvaluation evaluatorZone; 
	
	Zone zoneTenerife = DummyZoneFactory.ZONE_PROVINCIA_STA_CRUZ.setId("zoneTenerife");
	User userTenerife = DummyUserFactory.USER1.setIdZones(Lists.newArrayList(zoneTenerife.getId()));
	BasicPoi poiTenerife = DummyPoiFactory.PLAYA_TERESITAS.setId("poiTenerife");
	BasicPoi poiSol= DummyPoiFactory.POI_SOL.setId("poiSol");
	
	@Before
	public void init(){
		zoneRepository = createMock(ZoneRepository.class);
		userRepository = createMock(UserRepository.class);
		poiRepository = createMock(PoiRepository.class);
		poiPermissionEvaluator = new PoiPermissionEvaluator();
		poiPermissionEvaluator.poiRepository = poiRepository;
		poiPermissionEvaluator.userRepository = userRepository;
		poiPermissionEvaluator.zoneRepository = zoneRepository;
		evaluatorPoi = createMock(ClassTypePermissionEvaluation.class);
		evaluatorZone = createMock(ClassTypePermissionEvaluation.class);
	}
	
	@Test
	public void testPoiPermissionEvaluatorHasPermissionObject() {
		expect(userRepository.findByEmail(userTenerife.getEmail())).andReturn(userTenerife);
		expect(zoneRepository.findOne(zoneTenerife.getId())).andReturn(zoneTenerife);
		replay(poiRepository, userRepository, zoneRepository);
		assertTrue(poiPermissionEvaluator.hasPermission(getAutenthication(userTenerife.getEmail()), poiTenerife, "save"));
		verify(userRepository,zoneRepository);
	}
	
	@Test
	public void testPoiPermissionEvaluatorHasPermissionTargetId() {
		expect(userRepository.findByEmail(userTenerife.getEmail())).andReturn(userTenerife);
		expect( zoneRepository.findOne(zoneTenerife.getId())).andReturn(zoneTenerife);
		expect( poiRepository.findOne(poiTenerife.getId())).andReturn(poiTenerife);
		replay(poiRepository, userRepository, zoneRepository);
		assertTrue(poiPermissionEvaluator.hasPermission(getAutenthication(userTenerife.getEmail()), poiTenerife.getId(), poiTenerife.getClass().getSimpleName(), "save"));
		verify(userRepository,zoneRepository, poiRepository);
	}
	
	
	@Test
	public void testCustomPermissionEvaluatorHasPermission(){
		List<ClassTypePermissionEvaluation> evaluators = Lists.newArrayList(evaluatorZone, evaluatorPoi );
		CustomPermissionEvaluator customPermissionEvaluator = new CustomPermissionEvaluator(evaluators);
		Authentication authentication = getAutenthication(userTenerife.getEmail());
		expect(evaluatorZone.isAssignableByObject(poiTenerife)).andReturn(false);
		expect(evaluatorPoi.isAssignableByObject(poiTenerife)).andReturn(true);
		expect(evaluatorPoi.hasPermission(authentication, poiTenerife, "save")).andReturn(true);
		replay(evaluatorZone, evaluatorPoi);
		assertTrue(customPermissionEvaluator.hasPermission(authentication, poiTenerife, "save"));
		verify(evaluatorZone,evaluatorPoi);
	}
	
	@Test
	public void testCustomPermissionEvaluatorByClassNoEvaluatorFound(){
		List<ClassTypePermissionEvaluation> evaluators = Lists.newArrayList(evaluatorZone);
		CustomPermissionEvaluator customPermissionEvaluator = new CustomPermissionEvaluator(evaluators);
		Authentication authentication = getAutenthication(userTenerife.getEmail());
		expect(evaluatorZone.isAssignableByObject(poiSol)).andReturn(false);
		replay(evaluatorZone, evaluatorPoi);
		assertFalse(customPermissionEvaluator.hasPermission(authentication, poiSol, "save"));
		verify(evaluatorZone,evaluatorPoi);
	}
	
	@Test
	public void testCustomPermissionEvaluatorByTypeNoEvaluatorFound(){
		List<ClassTypePermissionEvaluation> evaluators = Lists.newArrayList(evaluatorZone);
		CustomPermissionEvaluator customPermissionEvaluator = new CustomPermissionEvaluator(evaluators);
		Authentication authentication = getAutenthication(userTenerife.getEmail());
		expect(evaluatorZone.isAssignableByType("BasicPoi")).andReturn(false);
		replay(evaluatorZone, evaluatorPoi);
		assertFalse(customPermissionEvaluator.hasPermission(authentication, poiSol.getId(), "BasicPoi", "save"));
		verify(evaluatorZone,evaluatorPoi);
	}
	
	@Test
	public void testCustomPermissionEvaluatorHasPermissionTargetId(){
		List<ClassTypePermissionEvaluation> evaluators = Lists.newArrayList(evaluatorZone, evaluatorPoi );
		CustomPermissionEvaluator customPermissionEvaluator = new CustomPermissionEvaluator(evaluators);
		Authentication authentication = getAutenthication(userTenerife.getEmail());
		String targetType = poiTenerife.getClass().getSimpleName();
		expect(evaluatorZone.isAssignableByType(targetType)).andReturn(false);
		expect(evaluatorPoi.isAssignableByType(targetType)).andReturn(true);
		expect(evaluatorPoi.hasPermission(authentication, poiTenerife.getId(), targetType, "save")).andReturn(true);
		replay(evaluatorZone,evaluatorPoi);
		assertTrue(customPermissionEvaluator.hasPermission(authentication, poiTenerife.getId(), targetType, "save"));
		verify(evaluatorZone,evaluatorZone);
	}
	
	private Authentication getAutenthication(String email) {
		return  new UsernamePasswordAuthenticationToken( new org.springframework.security.core.userdetails.User(email,"whatever", new ArrayList<GrantedAuthority>()), "whatever");
	}
	

}

