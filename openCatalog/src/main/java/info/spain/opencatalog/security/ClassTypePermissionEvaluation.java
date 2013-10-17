package info.spain.opencatalog.security;

import org.springframework.security.access.PermissionEvaluator;

public interface ClassTypePermissionEvaluation extends PermissionEvaluator {
	public boolean isAssignableByObject(Object target);
	public boolean isAssignableByType(String type);
}
