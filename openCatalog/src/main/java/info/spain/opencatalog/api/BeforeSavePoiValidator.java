package info.spain.opencatalog.api;

import info.spain.opencatalog.domain.poi.BasicPoi;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BeforeSavePoiValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(BasicPoi.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		try {
			((BasicPoi) target).validate();
		} catch (Exception e){
			errors.reject(null, e.getMessage());
		}
		
	}

}
