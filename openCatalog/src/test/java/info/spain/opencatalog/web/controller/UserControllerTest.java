package info.spain.opencatalog.web.controller;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.domain.UserFactory;
import info.spain.opencatalog.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration({ "classpath:/spring/root-context.xml", "classpath:/spring/mvc-config.xml"})
@ActiveProfiles("dev")
public class UserControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private UserRepository repo;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	
	@Test
	public void testNewForm() throws Exception{
		this.mockMvc.perform( get("/admin/user/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/user/user"));
	}	
	@Test
	public void testSearchWithQuery() throws Exception {
		// With query
		this.mockMvc.perform( get("/admin/user").param("q", "some criteria"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/user/userList"));
	}
	
	@Test
	public void testSearchWithoutQuery() throws Exception {
		// without query
		this.mockMvc.perform( get("/admin/user"))
		.andExpect(status().isOk())
		.andExpect(view().name("admin/user/userList"));
	}
	
	
	@Test
	public void userNotFound() throws Exception {
		// without query
		this.mockMvc.perform( get("/admin/user/notFound"))
		.andExpect(status().isInternalServerError());
	}
	
	
	@Test
	public void createDuplicate() throws Exception {
		
		User user = UserFactory.newUser("userTest");
		
		// POST ONCE
		MockHttpServletRequestBuilder post1 = post("/admin/user")
			.param("email", user.getEmail())
			.param("name", user.getName())
			.param("password", user.getPassword())
			.param("repassword", user.getPassword())
			;
		
		MvcResult result = this.mockMvc.perform( post1 ).andExpect(status().isMovedTemporarily()).andReturn();
		String location = result.getResponse().getHeader("Location");
		String id = location.substring("/admin/user/".length(), location.indexOf('?'));
		
		
		// POST twice
		result = this.mockMvc.perform( post1 ).andExpect(status().isOk()).andReturn();
		
		assertTrue(result.getResponse().getContentAsString().contains("There already exists a user with that email"));
		
		
		// DELETE
		this.mockMvc.perform(delete("/admin/user/" + id))
		    .andExpect(status().isMovedTemporarily());
		
	}
	
	/**
	 * Test CRUD of a User
	 * @throws Exception
	 */
	@Test
	public void userCRUD() throws Exception {
		repo.deleteAll();
		User user = UserFactory.newUser("userTest");
		
		// Test POST
		MockHttpServletRequestBuilder thePost = post("/admin/user")
			.param("email", user.getEmail())
			.param("name", user.getName())
			.param("password", user.getPassword())
			.param("repassword", user.getPassword())
			;
		
		// POST
		MvcResult result = this.mockMvc.perform( thePost ).andExpect(status().isMovedTemporarily()).andReturn();
		String location = result.getResponse().getHeader("Location");
		assertTrue( location.contains("message.item.created"));
		String id = location.substring("/admin/user/".length(), location.indexOf('?'));
		
		User repoUser = repo.findOne(id);
		testEquals(user, repoUser);
		
		// Is API Key has been assigned?
		assertNotNull(repoUser.getApiKey());
		
		
		// Test GET
		result = this.mockMvc.perform( get("/admin/user/{id}", id))
				.andExpect(status().isOk())
				.andExpect(view().name("admin/user/user"))
				.andReturn();
		
		
		
		// Test UPDATE 
		User update = new User()
			.setName("xxx")
			.setPassword(user.getPassword() + "-modified")
			;

		
		MockHttpServletRequestBuilder thePut = put("/admin/user/" + id)
				.param("name", update.getName())
				.param("password", update.getPassword())
				.param("repassword", update.getPassword())
				;
		
		result = this.mockMvc.perform(thePut)
			    .andExpect(status().isMovedTemporarily())
			    .andReturn();
		
		repoUser = repo.findOne(id);
		
		update.setEmail(repoUser.getEmail()); // como no se puede cambiar, lo ponemos a mano para la comparación
		
		testEquals(update, repoUser);
		
		
		// TEST DELETE
		result = this.mockMvc.perform(delete("/admin/user/" + id))
			    .andExpect(status().isMovedTemporarily())
			    .andReturn();
		
		repoUser = repo.findOne(id);
		assertNull(repoUser);
		
		
    }
	
	private void testEquals(User expected, User actual){
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getPassword(), actual.getPassword());
		assertEquals(expected.getEmail(), actual.getEmail());
	}
	
	
	


}
