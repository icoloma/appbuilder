package info.spain.opencatalog.image;

import java.io.InputStream;

public class ImageResource {
	InputStream inputStream;
	String contentType;
	long contentLenght;
	String filename;
	
	
	public ImageResource(InputStream inputStream, String contentType, long contentLenght, String filename) {
		super();
		this.inputStream = inputStream;
		this.contentType = contentType;
		this.contentLenght = contentLenght;
		this.filename = filename;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public String getContentType() {
		return contentType;
	}
	public long getContentLenght() {
		return contentLenght;
	}
	public String getFilename() {
		return filename;
	}
	
	
}
