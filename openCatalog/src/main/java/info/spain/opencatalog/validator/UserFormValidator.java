package info.spain.opencatalog.validator;

import info.spain.opencatalog.web.form.UserForm;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserFormValidator implements Validator {
	
	private static final int MIN_PASSWORD_LENGTH = 7;

	@Override
	public boolean supports(Class<?> clazz) {
		 return UserForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
	    UserForm userForm = (UserForm) target;
	    validatePasswordsMatch(userForm, errors);
	    validatePasswordLength(userForm, errors);
    }
	
	private void validatePasswordsMatch(UserForm userForm, Errors errors){
		boolean isValid;
		String repassword = userForm.getRepassword();
		String password = userForm.getPassword();
		if (repassword != null) {
			isValid = repassword.equals(password);
		} else {
			isValid = password == null;
		}
		
		if (!isValid){
			errors.rejectValue("password", "user.password.error.mismatch", "Password doesn't mismatch");
		}
	}
	
	private void validatePasswordLength(UserForm userForm, Errors errors){
		if (StringUtils.isNotBlank(userForm.getPassword()) &&  userForm.getPassword().length() < MIN_PASSWORD_LENGTH ){
			errors.rejectValue("password", "user.password.error.length", "Password lenght minmun is " + MIN_PASSWORD_LENGTH);
		}
		
	}
		
	

}
