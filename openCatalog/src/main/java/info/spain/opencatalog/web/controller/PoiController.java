package info.spain.opencatalog.web.controller;


import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.FlagGroup;
import info.spain.opencatalog.domain.poi.TimeTableEntry;
import info.spain.opencatalog.domain.poi.types.BasicPoiType;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;
import info.spain.opencatalog.domain.poi.types.PoiTypeRepository;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.image.PoiImageUtils;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.web.form.PoiForm;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public String search(Model model, @PageableDefaults(sort="lastModified", sortDir=Direction.DESC) Pageable pageable, @RequestParam(value="q",required=false) String q) {
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
		//PoiForm poiForm = new PoiForm(poi.getType().getId());
		PoiForm poiForm = new PoiForm(poi.getType());
		poiForm.copyData(poi);
			
		//poiForm.setHasImage(poiImageUtils.hasImage(id));
		model.addAttribute("flags", getMapFlags(poi.getType()));
		model.addAttribute("poi", poiForm);
		model.addAttribute("poiImages", poiImageUtils.getPoiImageFilenames(id));
		
		return "admin/poi/poi";
	}
	
	/**
	 * Flags permitidos para un tipo determinado organizado por grupos
	 * 
	 * Ej. para Hotel :   QUALITY -> [ QUALITY_ACCESIBILIDAD, QUALITY_MICHELIN, ...]
	 */
	private Map<String,List<String>> getMapFlags(PoiTypeID idType){
		BasicPoiType type = PoiTypeRepository.getType(idType);
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
	@RequestMapping(value="/new/{type}", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute("poi") PoiForm poiForm,
			@PathVariable("type") String type, BindingResult errors,  Model model,  
			@RequestParam(value="flags", required=false) String[] strFlags,
			@RequestParam(value="timetable", required=false) String[] timetable) {
	
		poiForm.getSyncInfo()
			.setLastUpdate(null)  // Always override
			.setImported(false)   // Always override 
			.setOriginalId(null)  // Always override 
			.setSync(false);  	  // Always override 
		
		if (errors.hasErrors()){
			return "admin/poi/poi";
		}
		if (timetable != null){
			poiForm.setTimetable(convertTimeTable(timetable, errors));
		}
		if (strFlags != null){
			poiForm.setFlags(convertFlags(strFlags, errors));
		}
		BasicPoi poi = poiRepository.save( new BasicPoi(PoiTypeID.valueOf(type)).copyData(poiForm));
		model.addAttribute(INFO_MESSAGE, "message.item.created" ) ;
		
		return "redirect:/admin/poi/" + poi.getId();
	}
	

	/**
	 * DELETE
	 */
	@RequestMapping( value="/{id}/delete")
	public String delete( @PathVariable("id") String idPoi, Model model) {
		poiImageUtils.deletePoiImages(idPoi);
		poiRepository.delete(idPoi);
		model.addAttribute(INFO_MESSAGE, "message.item.deleted" ) ;
		return "redirect:/admin/poi/";
	}
	
	/**
	 * EMPTY FORM 
	 */
	@RequestMapping(value="/new/{type}")
	public String newPoi(Model model, @PathVariable("type") String type){
		PoiForm poiForm =  new PoiForm(PoiTypeID.valueOf(type));
		model.addAttribute("poi", poiForm );
		model.addAttribute("flags", getMapFlags(poiForm.getType()));
		return "admin/poi/poi";
	}

	
	
	/**
	 * UPDATE
	 * Nota: Usamos POST y no PUT dado que el MultiPart da problemas con el HiddenHttpMethodFilter
	 * @throws IOException 
	 */
	@RequestMapping( value="/{id}", method=RequestMethod.POST) 
	public String update(@ModelAttribute("poi") PoiForm poiForm, BindingResult errors,
		Model model, 
		@PathVariable("id") String id,  
		@RequestParam(value="timetable", required=false) String[] timetable,
		@RequestParam(value="flags", required=false) String[] strFlags,
		@RequestParam(value="files", required=false) List<MultipartFile> addFiles,
		@RequestParam(value="deleteFile", required=false) String[] deleteFiles
		
		) throws IOException {

		BasicPoi dbPoi = poiRepository.findOne(id);
		if (dbPoi == null) {
			throw new NotFoundException("poi", id);
		}
		
		poiForm.setType(dbPoi.getType());  		       // Always override with db type
		poiForm.getSyncInfo()
			.setLastUpdate(dbPoi.getSyncInfo().getLastUpdate())  // Always override with db value   
			.setImported(dbPoi.getSyncInfo().isImported()) 		 // Always override with db value
			.setOriginalId(dbPoi.getSyncInfo().getOriginalId()); // Always override with db value
		
		if (timetable != null){
			poiForm.setTimetable(convertTimeTable(timetable,errors));
		}
		if (strFlags != null){
			poiForm.setFlags(convertFlags(strFlags,errors));
		}

		if (errors.hasErrors()){
			return "/admin/poi/poi";
		}
		
		poiForm.validate(); 
		
		BasicPoi poi = poiForm.getPoi();
		poi.setId(id);
		
		dbPoi.copyData(poi);
		
		updatePoiImages(dbPoi, addFiles, deleteFiles);

		poiRepository.save(dbPoi);
		model.addAttribute(INFO_MESSAGE,  "message.item.updated") ;

		
		return "redirect:/admin/poi/" + id;
	}
	
	private void updatePoiImages(BasicPoi poi, List<MultipartFile> addFiles,  String[] deleteFiles) throws IOException {
		
		// Save new files
		for (Iterator<MultipartFile> iterator = addFiles.iterator(); iterator.hasNext();) {
			MultipartFile file = iterator.next();
			poiImageUtils.saveImage(poi.getId(), file.getInputStream(), file.getContentType());
		}
		
		// Delete selected images
		if (deleteFiles != null) {
			for (int i = 0; i < deleteFiles.length; i++) {
				poiImageUtils.deleteImage(deleteFiles[i]);
				if (deleteFiles[i].equals(poi.getDefaultImageFilename())){
					poi.setDefaultImageFilename(null);
				}
			}
		}
	}
	
	private TimeTableEntry[] convertTimeTable(String[] timeTable, BindingResult errors){
		List<TimeTableEntry> result = Lists.newArrayList();
		for (int i = 0; i < timeTable.length; i++) {
			try {
				result.add(new TimeTableEntry(timeTable[i]));
			} catch (Exception e ){
				errors.reject("timetable.error.invalid", "invalid timeTable values");
			}
		}
		Collections.sort(result);  // Los guardamos en orden para visualizarlos mejor
		return result.toArray(new TimeTableEntry[] {});
	}
	
	private Flag[] convertFlags(String[] strFlags, BindingResult errors){
		List<Flag> flags = Lists.newArrayList();
		if (strFlags != null){
			for (String flag : strFlags) {
				try{
					flags.add(Flag.valueOf(flag));
				} catch(Exception e){
					errors.reject("flag.error.invalid", "Invalid flags values");
				}
			}
		}
		return flags.toArray(new Flag[]{});
	}
	
	
	/**
	 * Extrae el grupo de una key normalizada de poi.data
	 * Ej.: Si la key es grupo1_grupo_2_value devuelve grupo1_grupo2
	 * @param key : key normalizada
	 */
	public static String getDataGroup(String key){
		if (key == null) {
			return "";
		}
		int index = key.lastIndexOf("_");
		return index < 0 ? "": key.substring(0,index);
	}
}
