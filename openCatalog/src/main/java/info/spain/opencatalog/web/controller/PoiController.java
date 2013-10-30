package info.spain.opencatalog.web.controller;


import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.FlagGroup;
import info.spain.opencatalog.domain.poi.types.BasicPoiType;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;
import info.spain.opencatalog.domain.poi.types.PoiTypeRepository;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.image.PoiImageUtils;
import info.spain.opencatalog.service.PoiService;
import info.spain.opencatalog.web.form.PoiForm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Handles requests for the application poi page.
 */
@Controller
@RequestMapping(value = "/poi")
public class PoiController extends AbstractUIController {
	
	@Autowired
	protected PermissionEvaluator permissionEvaluator;
		
	@Autowired
	protected PoiService poiService;
	
	@Autowired
	protected PoiImageUtils poiImageUtils;

	
	/**
	 * SEARCH
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String search(Model model, @PageableDefaults(sort="lastModified", sortDir=Direction.DESC) Pageable pageable, @RequestParam(value="q",required=false) String q, @ModelAttribute("currentUser") User user) {
		String query = q == null ? "" : q; 
		Page<BasicPoi>page  = poiService.findByNameEsLikeIgnoreCase(query, pageable);
		model.addAttribute("page", page);
		model.addAttribute("q", query);
		return "poi/poiList";
	}

	/**
	 * SHOW by UUID
	 * No muestra formulario
	 */
	@RequestMapping(value="/{uuid}", method = RequestMethod.GET)
	public String showByUUID( @PathVariable("uuid") String uuid, Model model) {
		
		List<BasicPoi> pois  = poiService.findByUuid(uuid);
		if (pois == null || pois.size() == 0){
			throw new NotFoundException("poi", uuid);
		}
		
		BasicPoi poi = pois.get(0);
		//PoiForm poiForm = new PoiForm(poi.getType().getId());
		PoiForm poiForm = new PoiForm(poi.getType());
		poiForm.copyData(poi);
			
		//poiForm.setHasImage(poiImageUtils.hasImage(id));
		model.addAttribute("flags", getMapFlags(poi.getType()));
		model.addAttribute("poi", poiForm);
		model.addAttribute("poiImages", poiImageUtils.getPoiImageFilenames(poi.getId()));
		model.addAttribute("hasEditPermission", permissionEvaluator.hasPermission(SecurityContextHolder.getContext().getAuthentication(), poi, "EDIT"));
		model.addAttribute("versions", pois);
		return "poi/poi";
	}
	
	
	
	/**
	 * Flags permitidos para un tipo determinado organizado por grupos
	 * 
	 * Ej. para Hotel :   QUALITY -> [ QUALITY_ACCESIBILIDAD, QUALITY_MICHELIN, ...]
	 */
	protected  Map<String,List<String>> getMapFlags(PoiTypeID idType){
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
