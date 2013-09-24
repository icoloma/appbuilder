package info.spain.opencatalog.image;

import info.spain.opencatalog.repository.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component
public class PoiImageUtilsImpl implements PoiImageUtils {
	
	//private Logger log = LoggerFactory.getLogger(getClass());
	
	private static final String SEPARATOR = "::";
	private static final String NO_IMAGE = "img/no_image.png";
	
	StorageService storageService;

	@Autowired
	public PoiImageUtilsImpl(StorageService storageService){
		this.storageService = storageService;
	}
	

	public boolean hasImages(String idPoi){
		return storageService.getFilenamesLike(getImageFileName(idPoi, null)).size() > 0;
	}
	
	@Override
	public void deletePoiImages(String idPoi) {	
		List<String> images = getPoiImageFilenames(idPoi);
		for (String filename : images) {
			deleteImage(filename);
		}
	}
	
	@Override
	public void deleteImage(String filename) {	
		storageService.deleteFile(filename);
	}

	@Override
	public String saveImage(String idPoi, InputStream  inputStream, String contentType) throws IOException {
		String filename = getImageFileName(idPoi, UUID.randomUUID().toString());
		storageService.saveFile( inputStream, filename, contentType);
		return filename;
	}
	 
	public ImageResource getPoiImageResource(String filename){
		try {
			GridFsResource file = storageService.getByFilename(filename);
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

	@Override
	public List<String> getPoiImageFilenames(String idPoi) {
		return storageService.getFilenamesLike(getImageFileName(idPoi, null));
	}
	
	
	@Override
	public void deleteAllImages() {
		storageService.deleteAll();
	}


	public String getImageFileName(String idPoi, String idImg ){
		StringBuilder result = new StringBuilder(idPoi);
		result.append(SEPARATOR);
		if (!Strings.isNullOrEmpty(idImg)){
			result.append(idImg);
		}
		return result.toString();
	}


   	
	
	
}
