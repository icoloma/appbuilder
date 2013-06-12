package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.image.PoiImageUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.Files;

public class ImageExporterImpl implements ImageExporter {

	private File outputDir;
	private PoiImageUtils poiImageUtils;
	
	public ImageExporterImpl(File outputDir, PoiImageUtils poiImageUtils) {
		super();
		this.outputDir = outputDir;
		this.poiImageUtils = poiImageUtils;
	}

	public  List<String> exportImages(Poi poi){
		List<String> filenames = new ArrayList<>();
		File file = poiImageUtils.getPoiImageAsFile(poi.getId());
		try {
			String filename = poiImageUtils.getPoiImageFilename(poi.getId()); 
			Files.copy(file, new File(outputDir, filename));
			filenames.add(filename);
		} catch (IOException e) {
			throw new RuntimeException(e);
			
		}
		return filenames;
	}

}
