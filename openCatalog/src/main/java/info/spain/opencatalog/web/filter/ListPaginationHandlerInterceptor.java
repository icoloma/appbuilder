package info.spain.opencatalog.web.filter;

import info.spain.opencatalog.web.filter.RequestWrapperFilter.CustomHttpServletRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.data.rest.webmvc.RepositoryEntityController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Si no exisitiera el parámetro "page" lo añade a cualquier método list*
 * de RepositoryEntityController
 * 
 * @author ehdez
 *
 */
public class ListPaginationHandlerInterceptor extends HandlerInterceptorAdapter  {
	
	private Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			if (request.getParameter("page") == null &&
				handlerMethod.getMethod().getName().startsWith("list") &&
				RepositoryEntityController.class.isAssignableFrom(((HandlerMethod) handler).getBeanType())){
				try {
					CustomHttpServletRequestWrapper wrapper = (CustomHttpServletRequestWrapper) request;
					wrapper.setParameter("page", "1");
				} catch (ClassCastException e){
					log.warn("No se puede añadir el parámetro page a la request {}. Añada el filtro 'RequestWrapperFilter' al web.xml", request.getPathInfo());
				}
			}
		}
		
		return true;
	}

}
