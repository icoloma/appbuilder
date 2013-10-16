package info.spain.opencatalog.security;

import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.repository.UserRepository;
import info.spain.opencatalog.repository.ZoneRepository;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
/**
 * Evaluate if current user can Modify
 * @author ehdez
 *
 */
public class PoiPermissionEvaluator implements ClassTypePermissionEvaluation {
	
	@Autowired
	ZoneRepository zoneRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PoiRepository poiRepository;

	@Override
	public boolean hasPermission(Authentication authentication, Object target, Object permission) {
		return evaluatePoiInUserZones(authentication,  target,  permission);
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		BasicPoi poi = poiRepository.findOne(targetId.toString());
		return evaluatePoiInUserZones(authentication, poi, permission);
	}

	@Override
	public boolean isAssignable(Object target) {
		return (target instanceof BasicPoi);
	}

	@Override
	public boolean isAssignable(String targetType) {
		return BasicPoi.class.getSimpleName().equals(targetType);
	}

	// TODO: Check permission type
	private Boolean evaluatePoiInUserZones(Authentication authentication, Object target, Object permission){
		User user = getCurrentUser(authentication);
		if (user != null ) {
			BasicPoi poi = (BasicPoi) target;
			List<String> idZones = user.getIdZones();
			return isPoiInUserZones(poi,idZones);
		} 
		return false;
	}
	
	private Boolean isPoiInUserZones(BasicPoi poi, List<String> idZones){
		if (idZones != null) {
			for (String idZone : idZones) {
				Zone zone = zoneRepository.findOne(idZone);
				if (zone != null){
					if (zone.contains(poi.getLocation())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	
	// TODO: Extraer método a clase común aparte
	private User getCurrentUser(Authentication authentication){
		Object principal =  authentication.getPrincipal();
		
		if ("anonymousUser".equals(principal)){
			return null;
		}
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		return userRepository.findByEmail(user.getUsername());
	}

	


}
