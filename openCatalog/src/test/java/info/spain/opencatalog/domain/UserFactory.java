package info.spain.opencatalog.domain;

import com.google.common.collect.ImmutableSet;

public class UserFactory extends AbstractFactory {
	
	public static User newUser(String key){
		key = key + "-" + getRandom().nextInt();
		User user= new User()
		.setEmail(key+"@example.com")
		.setPassword(key+"-password")
		.setName(key+"-name")
		.setApiKey(ApiKeyGenerator.newKey());
		return user;
	}
	
	
	public static User ROOT = UserFactory.newUser("").setEmail("root@example.com").setPassword("root").setApiKey("1234");
	public static ImmutableSet<User> WELL_KNOWN_USERS= ImmutableSet.of(ROOT);

	

}