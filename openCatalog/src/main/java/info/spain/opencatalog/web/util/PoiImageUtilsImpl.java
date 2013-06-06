package info.spain.opencatalog.web.util;

import info.spain.opencatalog.repository.StorageService;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PoiImageUtilsImpl implements PoiImageUtils {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	StorageService storageService;

	private static final String NO_IMAGE = "img/no_image.png";

	private String getPoiImageFilename(String idPoi) {
		return idPoi;
	}
	
	public boolean hasImage(String idPoi){
		return storageService.existsFile(getPoiImageFilename(idPoi));
	}
	
	public HttpEntity<byte[]> getPoiImageAsHttpEntity(String idPoi) {
		HttpHeaders headers = new HttpHeaders();
		try {
			String contentType;
			int contentLength;
			byte data[];
			
			GridFsResource file = storageService.getByFilename(getPoiImageFilename(idPoi));
			if (file != null && file.exists()) {
				contentType = file.getContentType();
				contentLength = (int) file.contentLength();
				data = IOUtils.toByteArray(file.getInputStream());
			} else {
				Resource img = new ClassPathResource(NO_IMAGE);
				contentLength = (int) img.contentLength();
				contentType = MediaType.IMAGE_PNG_VALUE;
				data = IOUtils.toByteArray(img.getInputStream());
			}
	
			headers.setContentType(MediaType.valueOf(contentType));
			headers.setContentLength(contentLength);
			
			return new HttpEntity<byte[]>(data, headers);
		} catch( Exception e) {
			return new ResponseEntity<byte[]>(e.getMessage().getBytes(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    
	}
	
	
	@Override
	public void deleteImage(String idPoi) {	
		 String filename = getPoiImageFilename(idPoi);
		 storageService.deleteFile(filename);
	}

	@Override
	public void saveImage(String idPoi, MultipartFile file) throws IOException {
		String filename = getPoiImageFilename(idPoi);
		if (file != null && ! file.isEmpty()){
			storageService.saveFile(file.getInputStream(), filename, file.getContentType());
		}
	}
	 
	 
	 

}
