package info.spain.opencatalog.web.filter;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

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
		
		filter.destroy();
		
	}
	
	@Test
	public void lifeCycleFilter() throws Exception{
		RequestWrapperFilter filter = new RequestWrapperFilter();
		
		FilterChain mockChain =  new FilterChain() {
				@Override
			public void doFilter(ServletRequest arg0, ServletResponse arg1) throws IOException, ServletException {
				// do nothing
			}
		}; 
			
		filter.init(null);
		filter.doFilter(new MockHttpServletRequest(), new MockHttpServletResponse(), mockChain);
		filter.destroy();
		
	}
}
