package info.spain.opencatalog.rest;

import info.spain.opencatalog.domain.poi.AbstractPoi;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BeforeSavePoiValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(AbstractPoi.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		try {
			((AbstractPoi) target).validate();
		} catch (Exception e){
			errors.reject(null, e.getMessage());
		}
		
	}

}
