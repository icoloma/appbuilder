package info.spain.opencatalog.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Wrapper para poder añadir parámetros a la request.
 * 
 * Usado por el ListPaginationHandlerInterceptor
 * 
 * @author ehdez
 *
 */
public class RequestWrapperFilter implements Filter {
	
	public static class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
		
		Map<String,String[]> params = new HashMap<String,String[]>();

		public CustomHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
			params.putAll(request.getParameterMap());
		}

		@Override
		public String getParameter(String name) {
			String[] results = params.get(name);
			return (results == null)? null: results[0];
		}
		
		public void setParameter(String name, String value){
			params.put(name, new String[]{value});
		}



		@Override
		public Map<String, String[]> getParameterMap() {
			return params;
		}

		
		@Override
		public String[] getParameterValues(String name) {
			return params.get(name);
		}
		
		
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		chain.doFilter( new CustomHttpServletRequestWrapper((HttpServletRequest) req), resp);
	}
	
	@Override
	public void destroy() {}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

}
