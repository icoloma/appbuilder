package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.Flag;

import java.io.File;
import java.util.List;

public interface CatalogExporter {
	
	public void export(List<AbstractPoi> pois, List<Zone> zones, Flag[] flags, File outputDir);

}
