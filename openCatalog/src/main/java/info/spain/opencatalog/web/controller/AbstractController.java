package info.spain.opencatalog.web.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriTemplate;

public abstract class AbstractController {
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	protected HttpEntity<byte[]> getInputStreamAsHttpEntity(InputStream is, String contentType, long contentLength, String filename)  {
		HttpHeaders headers = new HttpHeaders();
		try {
			byte data[]= IOUtils.toByteArray(is);
			headers.setContentType(MediaType.valueOf(contentType));
			headers.setContentLength(contentLength);
			headers.setContentDispositionFormData("attachment", filename);
			return new HttpEntity<byte[]>(data, headers);
		} catch( Exception e) {
			try {
				return new ResponseEntity<byte[]>(e.getMessage().getBytes("UTF-8"), HttpStatus.INTERNAL_SERVER_ERROR);
			} catch(IOException ioEx){
				throw new RuntimeException(ioEx);
			}
		}
    
	}
	 
	 
	protected String getLocationForChildResource(HttpServletRequest request,  Object childIdentifier) {
		 StringBuffer url = request.getRequestURL();
		 UriTemplate template = new UriTemplate(url.append("/{childId}").toString());
		 return template.expand(childIdentifier).toASCIIString();
	 }
	
	
	


}
