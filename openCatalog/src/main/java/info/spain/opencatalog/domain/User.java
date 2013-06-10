package info.spain.opencatalog.domain;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

public class User {
	@Id
	private String id;
	
	@NotNull
	@Indexed(unique=true)
	private String email;
	private String name;
	private String password;
	
	@NotNull
	private String apiKey;
	
	@CreatedDate
	private DateTime createdDate;
	
	@LastModifiedDate
	private DateTime lastModifiedDate;
	
	
	public User(){};
	public User(User other){
		copyData(this, other);
	}
	
	public static void copyData(User target, User source){
		target.id = source.id;
		target.email = source.email;
		target.name = source.name;
		target.apiKey = source.apiKey;
		target.password = source.password;
	}
	
	public DateTime getCreatedDate() {
		return createdDate;
	}
	public DateTime getLastModifiedDate() {
		return lastModifiedDate;
	}
	public String getName() {
		return name;
	}
	public User setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getEmail() {
		return email;
	}
	public User setEmail(String email) {
		this.email = email;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public User setPassword(String password) {
		this.password = password;
		return this;
	}
	public String getApiKey() {
		return apiKey;
	}
	public User setApiKey(String apiKey) {
		this.apiKey = apiKey;
		return this;
	}
	public String getId() {
		return id;
	}
	public User setId(String id) {
		this.id = id;
		return this;
	}

}
