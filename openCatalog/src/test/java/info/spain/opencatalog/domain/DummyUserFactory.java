package info.spain.opencatalog.domain;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class DummyUserFactory extends AbstractFactory {
	
	public static User newUser(String key){
		key = key + "-" + getRandom().nextInt();
		User user= new User()
		.setEmail(key+"@example.com")
		.setPassword(key+"-password")
		.setName(key+"-name")
		.setRoles(Sets.newHashSet(UserRole.ROLE_USER))
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
	
	
	public static final User ROOT = new User()
			.setName("root")
			.setEmail("root@example.com")
			.setPassword("1234567890")
			.setRoles( Sets.newHashSet(UserRole.ROLE_ADMIN, UserRole.ROLE_USER))
			.setApiKey("1234");
	
	

	public static final User USER1 = new User()
			.setName("user1")
			.setEmail("user1@example.com")
			.setPassword("1234567890")
			.setRoles( Sets.newHashSet(UserRole.ROLE_USER))
			.setApiKey("00001");
	
	public static final ImmutableSet<User> WELL_KNOWN_USERS= ImmutableSet.of(ROOT, USER1);

	

}