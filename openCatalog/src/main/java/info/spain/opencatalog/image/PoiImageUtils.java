package info.spain.opencatalog.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface PoiImageUtils {
	
	public String getImageFileName(String idPoi, String idImg );
	public List<String> getPoiImageFilenames(String idPoi);
	public boolean hasImages(String idPoi);
	public void deleteImage(String idPoi);
	public String saveImage(String idPoi,InputStream inputStream, String contentType) throws IOException;
	public ImageResource getPoiImageResource(String filename);
		
}
