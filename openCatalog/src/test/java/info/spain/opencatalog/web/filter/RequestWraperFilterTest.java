package info.spain.opencatalog.web.filter;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class RequestWraperFilterTest {

	/**
	 * Test adding the request params to the Wrapper
	 * @throws Exception
	 */
	@Test
	public void testRequestWraperFilter() throws Exception {
		RequestWrapperFilter filter = new RequestWrapperFilter();
		MockHttpServletRequest req = new MockHttpServletRequest();
		
		String reqParamName  = "param1";
		String reqParamValue = "value1";
		req.addParameter(reqParamName, reqParamValue);
		RequestWrapperFilter.CustomHttpServletRequestWrapper wrapper = filter.new CustomHttpServletRequestWrapper(req);

		assertTrue(wrapper.params.containsKey(reqParamName));
		assertEquals(reqParamValue, wrapper.getParameter(reqParamName));
		assertEquals(1, wrapper.getParameterMap().size());
		
		
		// Adding new parameter
		
		String newParamName ="nParam";
		String newParamValue ="nValue";
		
		wrapper.setParameter(newParamName, newParamValue);
		
		assertTrue(wrapper.params.containsKey(newParamName));
		assertEquals(newParamValue, wrapper.getParameter(newParamName));
		assertEquals(2, wrapper.getParameterMap().size());
		
		
	}
}
