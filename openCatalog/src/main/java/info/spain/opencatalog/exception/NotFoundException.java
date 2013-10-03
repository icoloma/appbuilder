package info.spain.opencatalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3057114766244340608L;
	
	private String itemKey;
	private String id;
	
	public NotFoundException(String itemKey, String id){
		this.itemKey = itemKey;
		this.id = id;
	}

	public String getItemKey() {
		return itemKey;
	}

	public String getId() {
		return id;
	}

	@Override
	public String getMessage() {
		String m = super.getMessage();
		if (StringUtils.isEmpty(m)){
			m = "Not found [ itemKey: " + itemKey + ", id: " + id + "]";
		}
		return m;
	}
	
	
	
	
}
