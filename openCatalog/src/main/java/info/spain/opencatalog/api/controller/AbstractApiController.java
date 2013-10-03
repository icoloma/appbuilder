package info.spain.opencatalog.api.controller;

import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.web.controller.AbstractController;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.common.base.Strings;

public class AbstractApiController extends AbstractController {
	
	protected static final String DEFAULT_API_PAGE_SIZE = "10";
	
	@ExceptionHandler()
	public ResponseEntity<String> handleException(Exception ex) {
		log.error(getStackTrace(ex));
		return getResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
		return getResponse(ex, HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<String> getResponse(Exception ex, HttpStatus status) {
		return new ResponseEntity<String>( getMessage(ex), status);
	} 
	
	private String getMessage(Exception ex){
		String body = "{ \"message\" : \"%s\" }";
		String text = Strings.nullToEmpty(ex.getMessage()); 
		String message = text.replaceAll("\"", "\\\"");
		return String.format(body, message);
	}

	protected String getStackTrace(Throwable aThrowable) {
	    Writer result = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(result);
	    aThrowable.printStackTrace(printWriter);
	    return result.toString();
	  }
}
