package info.spain.opencatalog.security;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
/**
 * Always grant permissions
 * @author ehdez
 *
 */
public class AlwaysAllowedPermissionEvaluator implements ClassTypePermissionEvaluation {

	@Override
	public boolean hasPermission(Authentication authentication,Object targetDomainObject, Object permission) {
		return true;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		return true;
	}

	@Override
	public boolean isAssignableByObject(Object target) {
		return true;
	}

	@Override
	public boolean isAssignableByType(String type) {
		return true;
	}
	
	
	

}
