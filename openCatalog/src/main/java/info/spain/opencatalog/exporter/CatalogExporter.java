package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.types.BasicPoi;

import java.io.File;
import java.util.List;

public interface CatalogExporter {
	
	public void export(List<BasicPoi> pois, List<Zone> zones, Flag[] flags, File outputDir);

}
