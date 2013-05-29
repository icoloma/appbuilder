package info.spain.opencatalog.exception;

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
	
	
}
