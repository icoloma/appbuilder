package info.spain.opencatalog.web.filter;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class RequestWraperFilterTest {

	@Test
	/**
	 * Test adding the request params to the Wrapper
	 * @throws Exception
	 */
	public void testRequestWraperFilter() throws Exception {
		
		RequestWrapperFilter filter = new RequestWrapperFilter();
		MockHttpServletRequest req = new MockHttpServletRequest();
		req.addParameter("param1", "value1");
		
		RequestWrapperFilter.CustomHttpServletRequestWrapper wrapper = filter.new CustomHttpServletRequestWrapper(req);
		
		assertTrue(wrapper.params.containsKey("param1"));
		
		
	}
}
