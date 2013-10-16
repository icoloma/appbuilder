package info.spain.opencatalog.security;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import com.google.common.base.Preconditions;

public class CustomPermissionEvaluator implements PermissionEvaluator{
	
	private static Logger log = LoggerFactory.getLogger(CustomPermissionEvaluator.class);

	/** 
	 * List of configurable PermissionEvaluator
	 * The first assignable who responds not null wins 
	 */
	private List<ClassTypePermissionEvaluation> evaluators;
	
	public CustomPermissionEvaluator(List<ClassTypePermissionEvaluation> evaluators){
		Preconditions.checkNotNull(evaluators, "You must provide a list of PermissionEvaluators");
		this.evaluators = evaluators;
	}
	
	@Override
	public boolean hasPermission(Authentication authentication, Object target, Object permission) {
		for (ClassTypePermissionEvaluation evaluator : evaluators){ 
			if (evaluator.isAssignable(target)){
				log.debug("evaluating " + target + " with " + evaluator);
				Boolean result = evaluator.hasPermission(authentication, target, permission);
				if (result != null){
					return result;
				}
			} 
		} 
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		for (ClassTypePermissionEvaluation evaluator : evaluators){ 
			if (evaluator.isAssignable(targetType)){
				log.debug("evaluating " + targetId + ", " + targetType + " with " + evaluator);
				Boolean result = evaluator.hasPermission(authentication, targetId, targetType, permission);
				if (result != null){
					return result;
				}
			}
		}
		return false;
	}
	

}
