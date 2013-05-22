package info.spain.opencatalog.validator;

import info.spain.opencatalog.domain.I18nText;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidI18nTextConstraintValidator implements
		ConstraintValidator<ValidI18nText, I18nText> {

	@Override
	public void initialize(ValidI18nText constraintAnnotation) {}

	@Override
	public boolean isValid(I18nText i18nText, ConstraintValidatorContext context) {
//		return i18nText != null && i18nText.get(I18nText.DEFAULT) != null;   //FIXME: Uncomment when fixed #8
		return i18nText != null && i18nText.getEs() != null;                 //FIXME: delete when fixed #8  
	}

}
