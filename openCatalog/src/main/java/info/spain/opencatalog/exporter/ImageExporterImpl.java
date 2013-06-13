package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.image.ImageResource;
import info.spain.opencatalog.image.PoiImageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteStreams;

public class ImageExporterImpl implements ImageExporter {
	
	public static final String DIR_NAME ="assets";
	
	Logger log = LoggerFactory.getLogger(getClass());

	private PoiImageUtils poiImageUtils;
	
	public ImageExporterImpl(PoiImageUtils poiImageUtils) {
		super();
		this.poiImageUtils = poiImageUtils;
	}

	public  List<String> exportImages(Poi poi, File dir){
		File outputDir = checkOutputDir(dir);
		List<String> filenames = new ArrayList<>();
		ImageResource image = poiImageUtils.getPoiImageResource(poi.getId());
		try {
			String filename = image.getFilename();
			File target = new File(outputDir, filename);
			ByteStreams.copy(image.getInputStream(), new FileOutputStream(target) );
			log.debug("Exporting image:  " + target.getAbsolutePath());
			filenames.add(filename);
		} catch (IOException e) {
			throw new RuntimeException(e);
			
		}
		return filenames;
	}
	
	private File checkOutputDir(File outputDir){
		File dir = new File(outputDir, DIR_NAME);
		if (!dir.exists()){
			dir.mkdir();
		}
		return dir;
		
	}

}
