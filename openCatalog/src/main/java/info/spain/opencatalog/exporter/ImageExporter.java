package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.poi.AbstractPoi;

import java.io.File;
import java.util.List;

public interface ImageExporter {
	public List<String> exportImages(AbstractPoi poi, File outputDir);

}
