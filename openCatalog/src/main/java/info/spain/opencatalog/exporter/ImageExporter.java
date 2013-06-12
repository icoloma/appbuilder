package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.Poi;

import java.io.File;
import java.util.List;

public interface ImageExporter {
	public List<String> exportImages(Poi poi, File outputDir);

}
