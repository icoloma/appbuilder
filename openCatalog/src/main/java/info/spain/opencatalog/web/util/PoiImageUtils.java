package info.spain.opencatalog.web.util;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PoiImageUtils {

	public boolean hasImage(String idPoi);
	public void deleteImage(String idPoi);
	public void saveImage(String idPoi, MultipartFile file) throws IOException;
	public HttpEntity<byte[]> getPoiImageAsHttpEntity(String idPoi);
		
		
}
