package info.spain.opencatalog.web.controller;


import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.repository.StorageService;
import info.spain.opencatalog.web.form.PoiForm;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.net.MediaType;

/**
 * Handles requests for the application poi page.
 */
@Controller
@RequestMapping(value = "/admin/poi")
public class PoiController extends AbstractController {
	
	private static String NO_IMAGE = "img/no_image.png";
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PoiRepository poiRepository;
	
	@Autowired
	private StorageService storageService;
	
	
	/**
	 * SEARCH
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String search(Model model, @PageableDefaults(sort="name.es") Pageable pageable, @RequestParam(value="q",required=false) String q) {
		String query = q == null ? "" : q; 
		Page<Poi>page  = poiRepository.findByNameEsLikeIgnoreCase(query, pageable);
		model.addAttribute("page", page);
		model.addAttribute("q", query);
		return "admin/poi/poiList";
	}

	
	/**
	 * SHOW
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public String show( @PathVariable("id") String id, Model model) {
		Poi poi  = poiRepository.findOne(id);
		if (poi == null){
			throw new NotFoundException("poi", id);
		}
		PoiForm poiForm = new PoiForm(poi);
		poiForm.setHasImage(storageService.existsFile(getPoiImageFilename(id)));
		
		model.addAttribute("poi", poiForm);
		return "admin/poi/poi";
	}
	
	/**
	 * CREATE 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("poi") PoiForm poiForm ,BindingResult errors,  Model model) {
		if (errors.hasErrors()){
			return "admin/poi/poi";
		}
		Poi poi = poiRepository.save(poiForm.getPoi());
		model.addAttribute(INFO_MESSAGE, "message.item.created" ) ;
		
		if (poiForm.getFile() != null) {
			poiForm.setId(poi.getId());
			processImage(poiForm, errors);
		}
		
		return "redirect:/admin/poi/" + poi.getId();
	}
	
	/**
	 * EMPTY FORM 
	 */
	@RequestMapping(value="/new")
	public String newPoi(Model model){
		model.addAttribute("poi", new PoiForm());
		return "admin/poi/poi";
	}

	
	/**
	 * UPDATE
	 * 
	 * FIXME: Usamos POST y no PUT dado que el MultiPart da problemas con el HiddenHttpMethodFilter
	 */
	@RequestMapping( value="/{id}", method=RequestMethod.POST)
	public String update(@Valid @ModelAttribute("poi") PoiForm poiForm,BindingResult errors,  Model model, @PathVariable("id") String id) {
		
		if (errors.hasErrors() || ! processImage(poiForm, errors)){
			return "admin/poi/poi";
		}
		
		Poi poi = poiForm.getPoi();
		poi.setId(id);
		
		poiRepository.save(poi);
		model.addAttribute(INFO_MESSAGE,  "message.item.updated") ;
		return "redirect:/admin/poi/" + id;
	}
	
	
	
	/**
	 * DELETE
	 */
	@RequestMapping( value="/{id}", method=RequestMethod.DELETE)
	public String delete( @PathVariable("id") String id, Model model) {
		poiRepository.delete(id);
		model.addAttribute(INFO_MESSAGE, "message.item.deleted" ) ;
		return "redirect:/admin/poi/";
	}


	
	/**
	 * TAG LIST
	 * @param term
	 * Generates a list of tags [ {"id":"...", "label":"...", "value":"..." } ]
	 * Used by jquery.tagedit.js
	 */
	@RequestMapping(value="/tags", produces="application/json")
	public @ResponseBody String getAllTags(@RequestParam String term,Locale locale){
		StringBuffer result = new StringBuffer("[");
		Tag[] values = Tag.values();
		boolean empty = true;
		for (int i = 0; i < values.length; i++) {
			Tag tag = values[i];
			String txt = messageSource.getMessage("tags." + tag.toString(), new Object[]{}, locale);
			if (txt.toLowerCase().contains(term.toLowerCase())){
				if (i>0 && ! empty){
					result.append(",");
				}
				result.append("{\"id\":\"").append(tag.getId()).append("\", \"label\":\"").append(txt).append("\", \"value\":\"").append(txt).append("\"}");
				empty = false;
			}
		}
		result.append("]");
		return result.toString();
		
	}
	
	/**
	 * IMAGE
	 */
	 @RequestMapping(value = "/{id}/image", method = RequestMethod.GET)
	 public void getById (@PathVariable (value="id") String id, HttpServletResponse response) throws IOException {
		 
		 String contentType;
		 int contentLength;
		 byte data[];
		 
		 GridFsResource file = storageService.getByFilename(getPoiImageFilename(id));
		 if (file != null && file.exists()){
			  contentType = file.getContentType();
			 contentLength = (int)file.contentLength();
			 data= IOUtils.toByteArray(file.getInputStream());
		 } else {
			 Resource img = new  ClassPathResource(NO_IMAGE);
			 contentLength = (int) img.contentLength();
			 contentType= MediaType.PNG.toString();
			 data = IOUtils.toByteArray(img.getInputStream());
		 }
		
		 response.setContentType(contentType);
		 response.setContentLength(contentLength);
		 response.getOutputStream().write(data);
		 response.getOutputStream().flush();
	 
	  }
	 
	 private String getPoiImageFilename(String idPoi){
		 return idPoi;
	 }
	
	 private boolean processImage(PoiForm form, BindingResult errors ){
		 String filename = getPoiImageFilename(form.getId());
		 if (form.isDeleteImage()){
				storageService.deleteFile(filename);
		 } else {
			MultipartFile file = form.getFile();
			if (file != null && ! file.isEmpty()){
				try {
					// FIXME: save on storageService
					//storageService.saveFile(file, filename, contentType);
					storageService.saveFile(file.getInputStream(), filename, file.getContentType());
					
					//Files.write( file.getBytes(), new File("/tmp/" + filename));
				} catch (IOException e) {
					errors.addError( new ObjectError("image", "poi.image.save.error"));
					log.error(e.getMessage());
					return false;
				}
			}
		}
		return true;
	}
	

}
