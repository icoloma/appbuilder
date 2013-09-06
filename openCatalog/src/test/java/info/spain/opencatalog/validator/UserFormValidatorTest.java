package info.spain.opencatalog.validator;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import info.spain.opencatalog.web.form.UserForm;
import org.junit.Test;
import org.springframework.validation.BindException;

import com.google.common.base.Strings;

public class UserFormValidatorTest {
	
	private static final String CORRECT_PASSWORD = Strings.repeat("x", UserFormValidator.MIN_PASSWORD_LENGTH);
	
	@Test
	public void testPasswordsMatchs(){
		UserFormValidator validator = new UserFormValidator();
		UserForm userForm = new UserForm();
		userForm.setPassword(CORRECT_PASSWORD);
		userForm.setRepassword(CORRECT_PASSWORD);
		BindException errors = new BindException(userForm, "user");
		validator.validatePasswordsMatch(userForm, errors);
		assertFalse(errors.hasErrors());
	}
	
	@Test
	public void testPasswordsDoesntMatchs(){
		UserFormValidator validator = new UserFormValidator();
		UserForm userForm = new UserForm();
		userForm.setPassword(CORRECT_PASSWORD);
		userForm.setRepassword("---");
		BindException errors = new BindException(userForm, "user");
		validator.validatePasswordsMatch(userForm, errors);
		assertTrue(errors.hasErrors());
	}
	
	@Test
	public void testPasswordLength(){
		UserFormValidator validator = new UserFormValidator();
		UserForm userForm = new UserForm();
		userForm.setPassword(CORRECT_PASSWORD);
		userForm.setRepassword(CORRECT_PASSWORD);
		BindException errors = new BindException(userForm, "user");
		validator.validatePasswordLength(userForm, errors);
		assertFalse(errors.hasErrors());
	}
	
	@Test
	public void testPasswordLengthError(){
		UserFormValidator validator = new UserFormValidator();
		UserForm userForm = new UserForm();
		userForm.setPassword(Strings.repeat("x", UserFormValidator.MIN_PASSWORD_LENGTH - 1 ));
		BindException errors = new BindException(userForm, "user");
		validator.validatePasswordLength(userForm, errors);
		assertTrue(errors.hasErrors());
	}

}