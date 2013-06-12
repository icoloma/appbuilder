package info.spain.opencatalog.image;

import info.spain.opencatalog.domain.PoiFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class PoiImageUtilsMock implements PoiImageUtils {

	@Override
	public boolean hasImage(String idPoi) {
		return true;
	}

	@Override
	public void deleteImage(String idPoi) {}

	@Override
	public void saveImage(String idPoi, InputStream inputStream, String contentType) throws IOException {}

	@Override
	public File getPoiImageAsFile(String idPoi) {
		try {
			return PoiFactory.randomImage().getFile();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public HttpEntity<byte[]> getPoiImageAsHttpEntity(String idPoi) {
		HttpHeaders headers = new HttpHeaders();
		try {
			Resource img = PoiFactory.randomImage();
			String contentType = MediaType.IMAGE_JPEG_VALUE;
			int contentLength = (int) img.contentLength();
			byte data[] = IOUtils.toByteArray(img.getInputStream());
	
			headers.setContentType(MediaType.valueOf(contentType));
			headers.setContentLength(contentLength);
			
			return new HttpEntity<byte[]>(data, headers);
		} catch( Exception e) {
			return new ResponseEntity<byte[]>(e.getMessage().getBytes(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    
	}

	@Override
	public String getPoiImageFilename(String idPoi) {
		return idPoi;
	}

}
