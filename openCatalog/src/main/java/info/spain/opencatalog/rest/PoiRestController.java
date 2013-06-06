package info.spain.opencatalog.rest;

import java.io.IOException;

import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.web.util.PoiImageUtilsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/*

   Se necesita añadir  a DataRestConfig
   @ComponentScan( basePackages="info.spain.opencatalog.rest" )
    
*/
@Controller
@ExposesResourceFor(Poi.class)
public class PoiRestController {

	@Autowired 
	PoiRepository poiRepository;
	
	@Autowired
	PoiImageUtilsImpl poiImageUtils;
	

	/**
	 * GET Poi's Image
	 */
	 @RequestMapping(value = "/poi/{id}/image", method = RequestMethod.GET)
	 public HttpEntity<byte[]> getById (@PathVariable (value="id") String id)  {
		 return poiImageUtils.getPoiImageAsHttpEntity(id);
	 }
	 
	 /**
	  * DELETE Poi's Image
	  */
	 @RequestMapping(value = "/poi/{id}/image", method = RequestMethod.DELETE)
	 public HttpEntity<byte[]> deleteById (@PathVariable (value="id") String id)  {
		 poiImageUtils.deleteImage(id);
		 return new ResponseEntity<byte[]>(HttpStatus.NO_CONTENT);
	 }
	
	 /**
	  * Save Poi's Image
	  */
	 @RequestMapping(value = "/poi/{id}/image", method = RequestMethod.POST)
	 public HttpEntity<byte[]> save (@PathVariable (value="id") String id, @RequestParam("file") MultipartFile file)  {
		 try {
			 poiImageUtils.deleteImage(id);
			 poiImageUtils.saveImage(id, file);
			 return new ResponseEntity<byte[]>(HttpStatus.NO_CONTENT);
		 } catch (IOException e) {
			 return new ResponseEntity<byte[]>(e.getMessage().getBytes(), HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	 }
	
	

}
