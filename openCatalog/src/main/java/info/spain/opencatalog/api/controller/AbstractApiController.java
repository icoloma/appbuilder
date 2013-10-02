package info.spain.opencatalog.api.controller;

import info.spain.opencatalog.web.controller.AbstractController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class AbstractApiController extends AbstractController {
	
	protected static final String DEFAULT_API_PAGE_SIZE = "10";
	
	@ExceptionHandler()
	public ResponseEntity<String> handleException(Exception ex) {
		String body = "{ \"message\" : \"%s\" }";
		String message = ex.getMessage().replaceAll("\"", "\\\"");
		return  new ResponseEntity<String>(String.format(body, message), HttpStatus.INTERNAL_SERVER_ERROR);
	  }

}
