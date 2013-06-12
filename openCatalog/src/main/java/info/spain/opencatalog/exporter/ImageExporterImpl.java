package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.image.PoiImageUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

public class ImageExporterImpl implements ImageExporter {
	
	Logger log = LoggerFactory.getLogger(getClass());

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
			File target = new File(outputDir, filename);
			Files.copy(file, target);
			log.debug("Exporting image:  " + target.getAbsolutePath());
			filenames.add(filename);
		} catch (IOException e) {
			throw new RuntimeException(e);
			
		}
		return filenames;
	}

}
