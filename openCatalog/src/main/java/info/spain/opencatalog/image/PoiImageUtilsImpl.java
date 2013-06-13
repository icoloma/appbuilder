package info.spain.opencatalog.image;

import info.spain.opencatalog.repository.StorageService;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
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
	 
	public ImageResource getPoiImageResource(String idPoi){
		try {
			GridFsResource file = storageService.getByFilename(getPoiImageFilename(idPoi));
			if (file != null && file.exists()) {
				return new ImageResource(file.getInputStream(), file.getContentType(), file.contentLength(), file.getFilename());
			} else {
				Resource img = new ClassPathResource(NO_IMAGE);
				return new ImageResource(img.getInputStream(), MediaType.IMAGE_PNG_VALUE, img.contentLength(), "noImage.png");
			}
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
   	
	
	
}
