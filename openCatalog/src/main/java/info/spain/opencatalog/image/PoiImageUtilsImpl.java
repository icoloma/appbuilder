package info.spain.opencatalog.image;

import info.spain.opencatalog.repository.StorageService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
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

@Component
public class PoiImageUtilsImpl implements PoiImageUtils {
	
	//private Logger log = LoggerFactory.getLogger(getClass());
	
	StorageService storageService;

	@Autowired
	public PoiImageUtilsImpl(StorageService storageService){
		this.storageService = storageService;
	}
	
	
	private static final String NO_IMAGE = "img/no_image.png";

	public String getPoiImageFilename(String idPoi) {
		return idPoi;
	}
	
	public boolean hasImage(String idPoi){
		return storageService.existsFile(getPoiImageFilename(idPoi));
	}
	
	public File getPoiImageAsFile(String idPoi){
		try {
			GridFsResource file = storageService.getByFilename(getPoiImageFilename(idPoi));
			if (file != null && file.exists()) {
				return file.getFile();
			} else {
				Resource img = new ClassPathResource(NO_IMAGE);
				return img.getFile();
			}
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	
	
	@Override
	public void deleteImage(String idPoi) {	
		 String filename = getPoiImageFilename(idPoi);
		 storageService.deleteFile(filename);
	}

	@Override
	public void saveImage(String idPoi, InputStream  inputStream, String contentType) throws IOException {
		String filename = getPoiImageFilename(idPoi);
		storageService.saveFile( inputStream, filename, contentType);
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
	 

}
