package info.spain.opencatalog.api.controller;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
       

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration({ "classpath:APITest-config.xml"})
@ActiveProfiles("dev")
public class HomeAPIControllerTest {

	private HomeApiController controller = new HomeApiController();
	
	@Test
	public void testHomeWithSlash() throws Exception{
		ResourceSupport response = controller.home();
		assertNotNull(response);
		assertNotNull(response.getLink("poi"));
		assertNotNull(response.getLink("zone"));
	}
	
	@Test
	public void testHomeWithOutSlash() throws Exception{
		String response = controller.api();
		assertEquals("redirect:/api/", response);
	}
}
