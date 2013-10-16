package info.spain.opencatalog.security;

import org.springframework.security.access.PermissionEvaluator;

public interface ClassTypePermissionEvaluation extends PermissionEvaluator {
	
	public boolean isAssignable(Object target);
	public boolean isAssignable(String targetType);

}
