package info.spain.opencatalog.rest;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.lodging.Lodging;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;
import info.spain.opencatalog.domain.poi.types.PoiTypeRepository;
import info.spain.opencatalog.image.ImageResource;
import info.spain.opencatalog.image.PoiImageUtils;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.web.controller.AbstractController;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/*

   Se necesita añadir  a DataRestConfig
   @ComponentScan( basePackages="info.spain.opencatalog.rest" )
    
*/
@Controller
@ExposesResourceFor(AbstractPoi.class)
public class PoiRestController extends AbstractController {

	@Autowired 
	PoiRepository poiRepository;
	
	@Autowired
	PoiImageUtils poiImageUtils;
	
	@Autowired
	ObjectMapper objectMapper;
	
	
	// TODO: HEAD que devuelva los valores validos/permitidos para un PoiType concreto
	// Ej.: BEACH --> data.width, data.longitude, ...
	
	/**
	 * GET Poi's Image
	 */
	 @RequestMapping(value = "/poi/{id}/image", method = RequestMethod.GET)
	 public HttpEntity<byte[]> getById (@PathVariable (value="id") String id)  {
		 ImageResource image = poiImageUtils.getPoiImageResource(id);
		 return super.getInputStreamAsHttpEntity(image.getInputStream(), image.getContentType(), image.getContentLenght(), image.getFilename());
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
			 poiImageUtils.saveImage(id, file.getInputStream(), file.getContentType());
			 return new ResponseEntity<byte[]>(HttpStatus.NO_CONTENT);
		 } catch (IOException e) {
			 return new ResponseEntity<byte[]>(e.getMessage().getBytes(), HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	 }

	/**
	 * POST without type param
	 * @param res
	 */
	 @RequestMapping(value = "/poi", method = RequestMethod.POST, params="!type")
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	 public void noType(HttpServletResponse res)  {
		res.addHeader("Error", "No POI type param specified: Valid types: [" + getValidPoiTypes()+ "]");
	 }	
	 
	 private String getValidPoiTypes(){
		 StringBuffer validTypes = new StringBuffer();
		 PoiTypeID[] values = PoiTypeID.values();
		 for (int i = 0; i < values.length; i++) {
			 validTypes.append(values[i]);
			 if (i<values.length-1){
				 validTypes.append(", ");
			 }
		 }
		 return "[" + validTypes.toString() + "]";
	 }
	 
	 
	/**
	 * POST 
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/poi", method = RequestMethod.POST, params="type")
	public void saveBacic(@RequestParam String type, HttpServletRequest req, HttpServletResponse res) throws IOException  {
		PoiTypeID idType = PoiTypeID.valueOf(type);
		InputStream body = req.getInputStream();
		 // Basic or Lodging
		Class<? extends AbstractPoi> clazz = PoiTypeRepository.BASIC_TYPES.contains(idType)? BasicPoi.class : Lodging.class;
		AbstractPoi poi = objectMapper.readValue(body, clazz);
		savePoi(poi,type,req,res);
	 }
	 
	 
	 private void savePoi(AbstractPoi poi, String type, HttpServletRequest req, HttpServletResponse res)  {
		 poi.setType(PoiTypeRepository.getType(type));
		 poi.validate();
		 poi = poiRepository.save(poi);
		 res.addHeader("Location", getLocationForChildResource(req, poi.getId()));
	 }	

	 private String getLocationForChildResource(HttpServletRequest request,  Object childIdentifier) {
		 StringBuffer url = request.getRequestURL();
		 UriTemplate template = new UriTemplate(url.append("/{childId}").toString());
		 return template.expand(childIdentifier).toASCIIString();
	 }

		
	

}
