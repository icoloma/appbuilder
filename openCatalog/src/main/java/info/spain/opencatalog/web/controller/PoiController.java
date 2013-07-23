package info.spain.opencatalog.web.controller;


import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.FlagGroup;
import info.spain.opencatalog.domain.poi.types.BasicPoiType;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;
import info.spain.opencatalog.domain.poi.types.PoiTypeRepository;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.image.ImageResource;
import info.spain.opencatalog.image.PoiImageUtils;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.web.form.PoiForm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.http.HttpEntity;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Handles requests for the application poi page.
 */
@Controller
@RequestMapping(value = "/admin/poi")
public class PoiController extends AbstractUIController {
	
	@Autowired
	private PoiRepository poiRepository;
	
	@Autowired
	private PoiImageUtils poiImageUtils;
	
	
	/**
	 * SEARCH
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String search(Model model, @PageableDefaults(sort="name.es") Pageable pageable, @RequestParam(value="q",required=false) String q) {
		String query = q == null ? "" : q; 
		Page<BasicPoi>page  = poiRepository.findByNameEsLikeIgnoreCase(query, pageable);
		model.addAttribute("page", page);
		model.addAttribute("q", query);
		return "admin/poi/poiList";
	}

	
	/**
	 * SHOW
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public String show( @PathVariable("id") String id, Model model) {
		BasicPoi poi  = poiRepository.findOne(id);
		if (poi == null){
			throw new NotFoundException("poi", id);
		}
		PoiForm poiForm = new PoiForm(poi.getType().getId());
		poiForm.copyData(poi);
	

		
		//poiForm.setHasImage(poiImageUtils.hasImage(id));
		model.addAttribute("flags", getMapFlags(poi.getType()));
		model.addAttribute("poi", poiForm);
		return "admin/poi/poi";
	}
	
	/**
	 * Flags permitidos para un tipo determinado organizado por grupos
	 * 
	 * Ej. para Hotel :   QUALITY -> [ ]
	 */
	private Map<String,List<String>> getMapFlags(BasicPoiType type){
		Map<String,List<String>> result = Maps.newLinkedHashMap();
		
		for (FlagGroup flagGroup : type.getFlagGroups()) {
			List<String> flags = Lists.newArrayList();
			for (Flag flag : type.getAllowedFlags()) {
				if (flagGroup.getFlags().contains(flag)){
					flags.add(flag.toString());
				}
			}
			if (flags.size() > 0){
				result.put(flagGroup.toString(), flags);
			}
		}
		return result;
	}

	
	/**
	 * CREATE 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("poi") PoiForm poiForm ,BindingResult errors,  Model model) {
		if (errors.hasErrors()){
			return "admin/poi/poi";
		}
		BasicPoi poi = poiRepository.save(poiForm.getPoi());
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
	@RequestMapping(value="/new/{type}")
	public String newPoi(Model model, @PathVariable("type") String type){
		PoiForm poiForm =  new PoiForm( PoiTypeID.valueOf(type));
		model.addAttribute("poi", poiForm );
		model.addAttribute("flags", getMapFlags(poiForm.getType()));
		return "admin/poi/poi";
	}

	
	/**
	 * UPDATE
	 * 
	 * FIXME: Usamos POST y no PUT dado que el MultiPart da problemas con el HiddenHttpMethodFilter
	 */
	@RequestMapping( value="/{id}", method=RequestMethod.POST)
	public String update(@ModelAttribute("poi") PoiForm poiForm, BindingResult errors,  Model model, @PathVariable("id") String id,  @RequestParam("flags") String[] strFlags) {
		
		poiForm.setFlags(convertFlags(strFlags));
		
		BasicPoi dbPoi = poiRepository.findOne(id);
		poiForm.setType(dbPoi.getType());  // Always override with db type
		poiForm.validate(); 
		
		
		if (errors.hasErrors() || ! processImage(poiForm, errors)){
			return "admin/poi/poi";
		}

		BasicPoi poi = poiForm.getPoi();
		poi.setId(id);
		
		dbPoi.copyData(poi);
		
		poiRepository.save(dbPoi);
		model.addAttribute(INFO_MESSAGE,  "message.item.updated") ;
		return "redirect:/admin/poi/" + id;
	}
	
	private Flag[] convertFlags(String[] strFlags){
		List<Flag> flags = Lists.newArrayList();
		for (String flag : strFlags) {
			flags.add(Flag.valueOf(flag));
		}
		return flags.toArray(new Flag[]{});
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
	@RequestMapping(value="/tags/{type}", produces="application/json; charset=utf-8")
	public @ResponseBody String getAllTags(@RequestParam String term, @PathVariable("type") String poiType, Locale locale){

		BasicPoiType type = PoiTypeRepository.getType(PoiTypeID.valueOf(poiType));
		
		List<Flag> values = getValidFlags(type, locale);
		
		StringBuffer result = new StringBuffer("[");
		
		boolean empty = true;
		int i=0;
		for (Flag flag : values) {
			String txt = messageSource.getMessage("flags." + flag, new Object[]{}, locale);
			if (txt.toLowerCase().contains(term.toLowerCase())){
				if (i++>0 && ! empty){
					result.append(",");
				}
				result.append("{\"id\":\"").append(flag).append("\", \"label\":\"").append(txt).append("\", \"value\":\"").append(txt).append("\"}");
				empty = false;
			}
		}
		result.append("]");
		return result.toString();
		
	}
	
	/** Flags validos para el tipo ordenados alfabéticamente según el locale */
	// TODO: Mejorar la ordenación basada en la traducción
	private List<Flag> getValidFlags(BasicPoiType type, final Locale locale){
		Set<Flag> allowedValues = type.getAllowedFlags();
		List<Flag> result = new ArrayList<Flag>();
		result.addAll(allowedValues);
		Collections.sort(result, new Comparator<Flag>() {
			@Override
			public int compare(Flag o1, Flag o2) {
				String txt1 = normalizeText(messageSource.getMessage("flags." + o1.toString(), new Object[]{}, locale));
				String txt2 = normalizeText(messageSource.getMessage("flags." + o2.toString(), new Object[]{}, locale));
				return txt1.compareTo(txt2);
			}
		});
		return result;
	}
	
	
	
	/**
	 * IMAGE
	 */
	 @RequestMapping(value = "/{id}/image", method = RequestMethod.GET)
	 public HttpEntity<byte[]> getById (@PathVariable (value="id") String id) throws IOException {
		 ImageResource img = poiImageUtils.getPoiImageResource(id);
		 return super.getInputStreamAsHttpEntity(img.getInputStream(), img.getContentType(), img.getContentLenght(), img.getFilename());
	  }
	 
	 
	 /**
	  * SAVE OR DELETE POI IMAGE
	  */
	 private boolean processImage(PoiForm form, BindingResult errors ){

		 if (form.isDeleteImage()){
			 poiImageUtils.deleteImage(form.getId());
			return true;
		 } 
		 try {
			 if (form.getFile() != null && ! form.getFile().isEmpty()) {
				 poiImageUtils.deleteImage(form.getId());
				 poiImageUtils.saveImage(form.getId(), form.getFile().getInputStream(), form.getFile().getContentType());
			 }
			return true;
		 } catch (IOException e) {
			errors.addError( new ObjectError("image", "poi.image.save.error"));
			return false;
		 }
	}

}
