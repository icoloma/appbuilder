package info.spain.opencatalog.domain;

import java.util.UUID;

public class ApiKeyGenerator {

	public static String newKey(){
		return UUID.randomUUID().toString();
	}
}
