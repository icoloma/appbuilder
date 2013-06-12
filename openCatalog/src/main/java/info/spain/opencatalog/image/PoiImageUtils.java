package info.spain.opencatalog.image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpEntity;

public interface PoiImageUtils {

	public String getPoiImageFilename(String idPoi);
	public boolean hasImage(String idPoi);
	public void deleteImage(String idPoi);
	public void saveImage(String idPoi,InputStream inputStream, String contentType) throws IOException;
	public File getPoiImageAsFile(String idPoi);
	public HttpEntity<byte[]> getPoiImageAsHttpEntity(String idPoi);
		
		
}
