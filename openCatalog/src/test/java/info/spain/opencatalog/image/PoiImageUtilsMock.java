package info.spain.opencatalog.image;

import info.spain.opencatalog.domain.PoiFactory;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

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
	public ImageResource getPoiImageResource(String idPoi) {
		try {
			Resource img = PoiFactory.randomImage();
			int contentLength = (int) img.contentLength();
			return new ImageResource(img.getInputStream(), MediaType.IMAGE_JPEG.toString(), contentLength, getPoiImageFilename(idPoi));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getPoiImageFilename(String idPoi) {
		return idPoi;
	}

}
