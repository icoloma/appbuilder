package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.poi.BasicPoi;

import java.io.File;
import java.util.List;

public interface ImageExporter {
	public List<String> exportImages(BasicPoi poi, File outputDir);

}
