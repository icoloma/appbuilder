package info.spain.opencatalog.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import info.spain.opencatalog.domain.DummyPoiFactory;
import info.spain.opencatalog.domain.DummyUserFactory;
import info.spain.opencatalog.domain.DummyZoneFactory;
import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.poi.BasicPoi;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/root-context.xml")
@ActiveProfiles("dev")
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	
	private Zone tenerife;
	private Zone madrid;
	private Zone alcala;
	
	private BasicPoi teide;
	private BasicPoi puertaDelSol;
	
	@Before
	public void init(){
		mongoTemplate.dropCollection("user");
		mongoTemplate.dropCollection("zone");
		mongoTemplate.dropCollection("poi");
		createZones();
		createPois();
		createTestUser();
	}


	/**
	 * Create without zones
	 */
	@Test
	public void testCRUDUser() {
		User user = DummyUserFactory.newUser("testCreate");
		User saved = userRepository.save(user);
		String id = saved.getId();
		assertNotNull(id);
		
		User dbUser = userRepository.findOne(id);
		assertNotNull(dbUser);
		
		userRepository.delete(id);
		dbUser = userRepository.findOne(id);
		assertNull(dbUser);
	}
	
//	/**
//	 * Create poi in zone
//	 */
//	@Test
//	public void testCreatePoiInZone() {
//		BasicPoi teresitas = DummyPoiFactory.POI_PLAYA_TERESITAS;
//	}
//	
	
	private User createUserWithZones(String name, List<String> idZones){
		User user = DummyUserFactory.newUser(name);
		user.setIdZones(idZones);
		return userRepository.save(user);
	}
	
	
	
	

	@Test
	public void testFindByName(){
		
		String email = "theuser@example.com";
		User user = DummyUserFactory.newUser("SampleUSer").setEmail(email);
		mongoTemplate.save(user);
		
		Page<User> result = userRepository.findByEmailIgnoreCaseLike(email, new PageRequest(0, 10));
		assertEquals(1, result.getContent().size());
		assertEquals(result.getContent().get(0).getEmail(), user.getEmail());
		mongoTemplate.remove(user);
	}
	

	private void createZones(){
		tenerife = DummyZoneFactory.ZONE_PROVINCIA_STA_CRUZ;
		mongoTemplate.save(tenerife); 
		
		madrid = DummyZoneFactory.ZONE_MADRID_CENTRO;
		mongoTemplate.save(madrid);
		
		alcala = DummyZoneFactory.ZONE_ALCALA_HENARES;
		mongoTemplate.save(alcala);
	}	
	
	private void createPois(){
		teide = DummyPoiFactory.POI_TEIDE;
		mongoTemplate.save(teide);

		puertaDelSol = DummyPoiFactory.POI_SOL;
		mongoTemplate.save(puertaDelSol);
	}
	
	private void createTestUser() {
		User testUser = createUserWithZones("testUserZones", Lists.newArrayList(tenerife.getId(), madrid.getId()));
		assertNotNull(testUser);
	}



		
}
