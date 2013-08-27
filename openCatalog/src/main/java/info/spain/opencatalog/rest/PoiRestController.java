package info.spain.opencatalog.rest;

import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;
import info.spain.opencatalog.image.ImageResource;
import info.spain.opencatalog.image.PoiImageUtils;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.web.controller.AbstractController;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
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
@ExposesResourceFor(BasicPoi.class)
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
	 * POST 
	 * @throws IOException 
	 * @throws JSONException 
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/poi", method = RequestMethod.POST)
	public void saveBacic( HttpServletRequest req, HttpServletResponse res) throws IOException, JSONException {
		
		JSONObject json = getJSON(req.getInputStream());
		
		String jsonType= json.getString("type");

		Class<? extends BasicPoi> clazz = PoiTypeID.valueOf(jsonType).getPoiClass();
		
		BasicPoi poi = objectMapper.readValue(json.toString(), clazz);
		savePoi(poi, req,res);
	 }
	
	private JSONObject getJSON(InputStream inputStream) throws  IOException, JSONException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer);
		return new JSONObject(writer.toString());
	}
	 
	
	 private void savePoi(BasicPoi poi, HttpServletRequest req, HttpServletResponse res)  {
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
