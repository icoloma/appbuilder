package info.spain.opencatalog.web.controller;


import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.image.ImageResource;
import info.spain.opencatalog.image.PoiImageUtils;
import info.spain.opencatalog.repository.PoiRepository;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application poi images.
 */
@Controller
@RequestMapping(value = "/admin/poi/{idPoi}/image")
public class PoiImageController extends AbstractUIController {
	
	@Autowired
	private PoiRepository poiRepository;
	
	@Autowired
	private PoiImageUtils poiImageUtils;
	
	/**
	 * IMAGE
	 */
	 @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	 public HttpEntity<byte[]> getById (@PathVariable (value="id") String id, @PathVariable(value="idPoi") String idPoi) throws IOException {
		 
		 String filename = id;
		 if ("default".equals(id)){
			 BasicPoi poi = poiRepository.findOne(idPoi);
			 String defaultImage  = poi.getDefaultImageFilename();
			 if (defaultImage != null){
				 filename = defaultImage;
			 } else {
				 // No se ha especificado default image, pero existen imágenes asociadas
				 // En ese caso, coger la primera
				 List<String> images = poiImageUtils.getPoiImageFilenames(idPoi);
				 if (images.size() > 0){
					 filename = images.get(0);
				 }
			 }
		 }
		 
		 ImageResource img = poiImageUtils.getPoiImageResource(filename);
		 return super.getInputStreamAsHttpEntity(img.getInputStream(), img.getContentType(), img.getContentLenght(), img.getFilename());
	  }

}
