package info.spain.opencatalog.domain;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class User {
	
	
	
	@Id
	private String id;
	
	@Indexed(unique=true)
	private String email;
	private String name;
	private String password;
	private String apiKey;
	private List<String> idZones;
	private Set<UserRole> roles;
	
	@CreatedDate
	private DateTime created;
	
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
		if (source.idZones != null) {
			target.idZones = Lists.newArrayList(source.idZones);
		} 
		if (source.roles != null){
			target.roles = Sets.newHashSet(source.roles);
		}
	}
	
	public DateTime getCreated() {
		return created;
	}
	public DateTime getLastModified() {
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
	public List<String> getIdZones() {
		return idZones;
	}
	public User setIdZones(List<String> idZones) {
		this.idZones = idZones;
		return this;
	}
	
	
	public Set<UserRole> getRoles() {
		return roles;
	}
	public User setRoles(Set<UserRole> role) {
		this.roles = role;
		return this;
	}
	
	@Override
	public String toString(){
		return Objects.toStringHelper(getClass())
			.add("id", id)
			.add("name", name)
			.add("email", email)
			.add("roles", roles)
			.add("idZones", idZones)
			.add("password,", "****")
			.add("apiKey", "***" )
			.toString();
	}

}
