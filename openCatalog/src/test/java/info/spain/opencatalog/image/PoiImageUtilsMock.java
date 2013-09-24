package info.spain.opencatalog.image;

import info.spain.opencatalog.domain.DummyPoiFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

public class PoiImageUtilsMock implements PoiImageUtils {

	@Override
	public boolean hasImages(String idPoi) {
		return true;
	}

	@Override
	public void deletePoiImages(String idPoi) {}

	@Override
	public void deleteImage(String filename) {}

	@Override
	public String saveImage(String idPoi, InputStream inputStream, String contentType) throws IOException { return "";}


	@Override
	public ImageResource getPoiImageResource(String idPoi) {
		try {
			Resource img = DummyPoiFactory.randomImage();
			int contentLength = (int) img.contentLength();
			return new ImageResource(img.getInputStream(), MediaType.IMAGE_JPEG.toString(), contentLength, getImageFileName(idPoi, null));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	

	@Override
	public String getImageFileName(String idPoi, String idImg) {
		return idPoi + "::" + idImg;
	}

	@Override
	public List<String> getPoiImageFilenames(String idPoi) {
		return null;
	}

	@Override
	public void deleteAllImages() {
		// do nothing
	}

	
	

}
