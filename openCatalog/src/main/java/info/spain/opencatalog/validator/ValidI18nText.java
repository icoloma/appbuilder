package info.spain.opencatalog.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=ValidI18nTextConstraintValidator.class)
public @interface ValidI18nText {

	String message() default "validI18nText.invalid.message";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default {};

}