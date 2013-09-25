package info.spain.opencatalog.domain;

import java.util.ArrayList;
import java.util.List;

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
	
	public static List<User> generateUsers(int maxUsers){
		List<User> result = new ArrayList<User>();
		for (int i = 0; i < maxUsers; i++) {
			result.add(newUser("" + i));
		}
		return result;
	}
	
	
	public static final User ROOT = UserFactory.newUser("").setEmail("root@example.com").setPassword("root").setApiKey("1234");
	public static final ImmutableSet<User> WELL_KNOWN_USERS= ImmutableSet.of(ROOT);

	

}