package info.spain.opencatalog.image;

import java.io.IOException;
import java.io.InputStream;

public interface PoiImageUtils {

	public String getPoiImageFilename(String idPoi);
	public boolean hasImage(String idPoi);
	public void deleteImage(String idPoi);
	public void saveImage(String idPoi,InputStream inputStream, String contentType) throws IOException;
	public ImageResource getPoiImageResource(String idPoi);
		
}
