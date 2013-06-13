package info.spain.opencatalog.web.controller;

import java.io.InputStream;

import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	protected HttpEntity<byte[]> getInputStreamAsHttpEntity(InputStream is, String contentType, long contentLength, String filename) {
		HttpHeaders headers = new HttpHeaders();
		try {
			byte data[]= IOUtils.toByteArray(is);
			headers.setContentType(MediaType.valueOf(contentType));
			headers.setContentLength(contentLength);
			headers.setContentDispositionFormData("attachment", filename);
			return new HttpEntity<byte[]>(data, headers);
		} catch( Exception e) {
			return new ResponseEntity<byte[]>(e.getMessage().getBytes(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    
	}
	 


}
