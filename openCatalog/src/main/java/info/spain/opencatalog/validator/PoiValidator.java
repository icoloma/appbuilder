package info.spain.opencatalog.validator;

import info.spain.opencatalog.domain.poi.BasicPoi;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PoiValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		 return BasicPoi.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		try {
			((BasicPoi) target).validate();
		} catch (Exception e){
			errors.reject("",e.getMessage());
		}
	    
    }
	
		

}
