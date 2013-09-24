package info.spain.opencatalog.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.image.ImageResource;
import info.spain.opencatalog.image.PoiImageUtils;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.web.controller.AbstractController;

import java.io.IOException;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@ExposesResourceFor(BasicPoi.class)
@RequestMapping(value = "/poi/{idPoi}/image")
public class PoiImageApiController extends AbstractController {

	@Autowired 
	PoiRepository poiRepository;
	
	@Autowired
	PoiImageUtils poiImageUtils;
	
	
	 /**
	 * GET Poi's Image List
	 */
	 @RequestMapping(method = RequestMethod.GET)
	 public HttpEntity<ResourceSupport> getPoiImages(@PathVariable (value="idPoi") String idPoi)  {
		 checkIfPoiExists(idPoi);
		 ResourceSupport resource = new ResourceSupport();
		 List<String> images = poiImageUtils.getPoiImageFilenames(idPoi);
		 for (int i = 0; i < images.size(); i++) {
			String name = images.get(i);
			// image Link
			Link l = linkTo(methodOn(PoiImageApiController.class).getById(idPoi, name)).withRel("image[" + i + "]");
			resource.add(l);
		 }
		 return new HttpEntity<ResourceSupport>(resource);
	 }
	 
	 /**
	  * GET Poi's Image 
	  */
	 @RequestMapping(value = "/{idImage}", method = RequestMethod.GET)
	 public HttpEntity<byte[]> getById (@PathVariable (value="idPoi") String idPoi, @PathVariable (value="idImage") String idImage)  {
		 checkIfPoiExists(idPoi);
		 System.out.println("*********** ------------------------------------------------");
		 if (!idImage.startsWith(idPoi)){
			 log.warn("Access to an image of a different poi");
			 System.out.println("------------------------------------------------");
			 return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		 }
		 ImageResource image = poiImageUtils.getPoiImageResource(idImage);
		 return super.getInputStreamAsHttpEntity(image.getInputStream(), image.getContentType(), image.getContentLenght(), image.getFilename());
	 }

	 /**
	  * DELETE Poi's Image
	  */
	 @RequestMapping(value = "/{idImage}", method = RequestMethod.DELETE)
	 public HttpEntity<byte[]> deleteById ( @PathVariable (value="idPoi") String idPoi, @PathVariable (value="idImage") String idImage)  {
		 poiImageUtils.deleteImage( poiImageUtils.getImageFileName(idPoi, idImage));
		 return new ResponseEntity<byte[]>(HttpStatus.NO_CONTENT);
	 }
	
	 /**
	  * POST Poi's Image
	  */
	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<byte[]> processUpload(@PathVariable (value="idPoi") String idPoi, @RequestParam MultipartFile file, HttpServletRequest request) throws IOException  {
		checkIfPoiExists(idPoi);
		 try {
			 String fileName = poiImageUtils.saveImage(idPoi, file.getInputStream(), file.getContentType());
			 String location = getLocationForChildResource(request, fileName);
			 HttpHeaders headers = new HttpHeaders();
			 headers.add("Location", location);
			 HttpEntity<byte[]> result = new ResponseEntity<byte[]>(headers, HttpStatus.CREATED);
			 return result;
		 } catch (Exception e) {
			 return new ResponseEntity<byte[]>(e.getMessage().getBytes(), HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	 }
	
	private void checkIfPoiExists(String idPoi) {
		if (poiRepository.findOne(idPoi) == null){
			throw new NotFoundException("", idPoi);
		}
	}
	

}
