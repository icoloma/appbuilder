package info.spain.opencatalog.validator;

import info.spain.opencatalog.web.form.UserForm;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserFormValidator implements Validator {
	
	protected static final int MIN_PASSWORD_LENGTH = 7;

	@Override
	public boolean supports(Class<?> clazz) {
		 return UserForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
	    UserForm userForm = (UserForm) target;
	    validatePasswordsMatch(userForm, errors);
	    validatePasswordLength(userForm, errors);
	    validatePasswordContent(userForm, errors);
    }
	
	protected void validatePasswordsMatch(UserForm userForm, Errors errors){
		boolean isValid = false;
		String repassword = userForm.getRepassword();
		String password = userForm.getPassword();
		if (repassword != null) {
			isValid = repassword.equals(password);
		} 
		
		if (!isValid){
			errors.rejectValue("password", "user.password.error.mismatch", "Password doesn't mismatch");
		}
	}
	
	protected void validatePasswordLength(UserForm userForm, Errors errors){
		String password = userForm.getPassword();
		if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
			errors.rejectValue("password", "user.password.error.length", "Password lenght minmun is " + MIN_PASSWORD_LENGTH);
		}
	}
		
	/**
	 * El password no puede ser parte del nombre ni del email
	 */
	protected void validatePasswordContent(UserForm userForm, Errors errors){
		String password = userForm.getPassword();
		String email = userForm.getEmail();
		String name = userForm.getName();
		if (email.contains(password) || name.contains(password)){
			errors.rejectValue("password", "user.password.error.content");
		}
		
	}
	

}
