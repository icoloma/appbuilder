package info.spain.opencatalog.web.form;

import info.spain.opencatalog.domain.User;

public class UserForm extends User {
	
	public UserForm(){};
	public UserForm(User user){
		super(user);
		this.repassword = user.getPassword();
	}
	
	public String repassword;

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	
	
	public User getUser(){
		return new User(this);	
	}
	

}
