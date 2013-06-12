package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.domain.Zone;

import java.io.File;
import java.util.List;

public interface CatalogExporter {
	
	
	public void init(File outputDir);
	public void export(List<Poi> pois, List<Zone> zones, Tag[] tags);
	public void close();

}
