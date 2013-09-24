package info.spain.opencatalog.api;

import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;
import info.spain.opencatalog.exception.NotFoundException;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

/*

   Se necesita añadir  a DataRestConfig
   @ComponentScan( basePackages="info.spain.opencatalog.rest" )
    
*/
@Controller
@ExposesResourceFor(BasicPoi.class)
@RequestMapping(value = "/poi")
public class PoiApiController extends AbstractController {

	@Autowired 
	PoiRepository poiRepository;
	
	@Autowired
	PoiImageUtils poiImageUtils;
	
	@Autowired
	ObjectMapper objectMapper;
	
	
	// TODO: HEAD que devuelva los valores validos/permitidos para un PoiType concreto
	// Ej.: BEACH --> data.width, data.longitude, ...
	

	/**
	 * POST 
	 * @throws IOException 
	 * @throws JSONException 
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public void saveBacic( HttpServletRequest req, HttpServletResponse res) throws IOException, JSONException {
		
		JSONObject json = getJSON(req.getInputStream());
		
		String jsonType= json.getString("type");

		Class<? extends BasicPoi> clazz = PoiTypeID.valueOf(jsonType).getPoiClass();
		
		BasicPoi poi = objectMapper.readValue(json.toString(), clazz);
		
		poi.setImported(false);   // Always override 
		poi.setOriginalId(null);  // Always override 
		poi.setSync(false);       // Always override 
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
	 
	 
	 @ResponseStatus(HttpStatus.NO_CONTENT)
	 @RequestMapping(value="/{id}", method = RequestMethod.PUT)
	 public void updateBasic( @PathVariable("id") String idPoi, HttpServletRequest req, HttpServletResponse res) throws IOException, JSONException {
			
		 	BasicPoi dbPoi = poiRepository.findOne(idPoi);
		 	if (dbPoi == null) {
		 		throw new NotFoundException("poi", idPoi);
		 	}
			
		 	JSONObject json = getJSON(req.getInputStream());
			
			Class<? extends BasicPoi> clazz = dbPoi.getType().getId().getPoiClass();
			
			BasicPoi poi = objectMapper.readValue(json.toString(), clazz);
			// Always override
			poi.setId(dbPoi.getId());
			poi.setImported(dbPoi.isImported());
			poi.setOriginalId(dbPoi.getOriginalId());  
			if (!dbPoi.isImported()){
				poi.setSync(false);
			}
			
			savePoi(poi, req,res);
		 }
	 
	 
	 /**
	  * Delete 
	  */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public void deletePoi(@PathVariable("id") String idPoi){
		BasicPoi poi = poiRepository.findOne(idPoi);
		if (poi != null){
			poiImageUtils.deletePoiImages(idPoi);
			poiRepository.delete(idPoi);
		}
		
	}

}
