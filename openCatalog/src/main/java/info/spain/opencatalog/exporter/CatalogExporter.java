package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.poi.Poi;

import java.io.File;
import java.util.List;

public interface CatalogExporter {
	
	public void export(List<Poi> pois, List<Zone> zones, Tag[] tags, File outputDir);

}
