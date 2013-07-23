package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.domain.poi.Poi;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.Flag;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * Permite exportar parte del catálogo a una base de datos SQLite 
 */
public class JSONExporter extends AbstractExporter implements CatalogExporter {
	
	
	public static final String DIR_NAME = "data";
	public static final String JSON_FILENAME ="openCatalog.json";
	
	public JSONExporter(MessageSource messageSource, ImageExporter imageExporter) {
		this.messageSource = messageSource;
		this.imageExporter = imageExporter;	
	}
	
	@Override
	public void export(List<BasicPoi> pois, List<Zone> zones, Flag[] flags, File outputDir) {
		Writer writer = init(outputDir);
		exportZones(zones, writer);
		writer.append(", ");
		exportPois(pois, outputDir, writer);
		writer.append(", ");
		exportFlags(flags, writer);
		close(writer);
	}



	/**
	 * Exporta un listado de Zonas; 
	 * Puede ser invocado múltiples veces de forma que no hace falta pasar 
	 * el listado completo de zonas de una sola vez y usar, por ejemplo 
	 * consultas paginadas
	 */
	private void exportZones(List<Zone> zones, Writer writer){
		writer.append("\"zones\": [\n");
		for (Iterator<Zone> iterator = zones.iterator(); iterator.hasNext();) {
			Zone zone = iterator.next();
			writer.append("{\n")
			.append(" \"name\": \"" + zone.getName() + "\",\n")
			.append(" \"description\" : \"" + zone.getDescription() + "\",\n")
			.append(" \"path\": " + getPathAsJSON(zone.getPath())+ ",\n")
			.append(" \"id\": " + zone.getId() + "\n")
			.append("}");
			
			if (iterator.hasNext()){
				writer.append(",\n");
			}
		}
		writer.append("]\n");
			
	}

	
	
	
	/**
	 * Exporta un listado de Pois
	 */
	
	private void exportPois(List<BasicPoi> pois, File outputDir, Writer writer){
		writer.append("\"pois\": [\n");
		for (Iterator<BasicPoi> iterator = pois.iterator(); iterator.hasNext();) {
			BasicPoi poi = iterator.next();
			List<String> images = imageExporter.exportImages(poi, outputDir);
			writer.append("{\n")
			 .append("  \"id\":" + asQuotedString(poi.getId()) + ",\n")
			.append("  \"name\":{\n")
			.append("    \"es\": " +  asQuotedString(poi.getName().getEs()) +",\n")
			.append("    \"en\": " +  asQuotedString(poi.getName().getEn()) + ",\n")
			.append("    \"fr\": " +  asQuotedString(poi.getName().getFr()) + ",\n")
			.append("    \"de\": " +  asQuotedString(poi.getName().getDe()) + ",\n")
			.append("    \"it\": " +  asQuotedString(poi.getName().getIt()) + "\n")
			.append("  },\n")
			.append("  \"description\":{\n")
			.append("    \"es\": " +  asQuotedString(poi.getDescription().getEs()) + ",\n")
			.append("    \"en\": " +  asQuotedString(poi.getDescription().getEn()) + ",\n")
			.append("    \"fr\": " +  asQuotedString(poi.getDescription().getFr()) + ",\n")
			.append("    \"de\": " +  asQuotedString(poi.getDescription().getDe()) + ",\n")
			.append("    \"it\": " +  asQuotedString(poi.getDescription().getIt()) + "\n")
			.append("  },\n")
			.append("  \"thumb\": \"thumb.png\",\n")
			.append("  \"imgs\": " +  asStringArray(images)+ ",\n")
			.append("  \"created\": " + poi.getCreated().getMillis() + ",\n")
			.append("  \"updated\": " + poi.getLastModified().getMillis() + ",\n")
			.append("  \"lat\": " + poi.getLocation().getLat() + ",\n")
			.append("  \"lng\": " + poi.getLocation().getLng() + ",\n")
			.append("  \"normlng\": " + getNormLong(poi.getLocation()) + ",\n")
//			.append("  \"tags\":" + asStringArray(poi.getTags()) + ",\n")
			.append("  \"starred\": 0\n")
			.append(" }");
			if (iterator.hasNext()){
				writer.append(",\n");
			}
		}
		writer.append("]\n");
	}
	
	/**
	 * Export Tags
	 * @param tags
	 * @return
	 */
	private void exportFlags(Flag[] flags, Writer writer ) {
		writer.append("\"tags\": [\n");
		
//		for (int i = 0; i < flags.length; i++) {
//			Flag flag = flags[i];
//			writer.append("{\n")
//			.append(" \"tag\": " +  asQuotedString(flag.toString()) + ",\n")
//			.append(" \"es\": " +  asQuotedString(translate("tags." + tag.toString(), LOCALE_ES )) + ",\n")
//			.append(" \"en\": " +  asQuotedString(translate("tags." + tag.toString(), Locale.ENGLISH)) + ",\n")
//			.append(" \"fr\": " +  asQuotedString(translate("tags." + tag.toString(), Locale.FRENCH )) + ",\n")
//			.append(" \"de\": " +  asQuotedString(translate("tags." + tag.toString(), Locale.GERMAN)) + ",\n")
//			.append(" \"it\": " +  asQuotedString(translate("tags." + tag.toString(), Locale.ITALIAN)) + "\n")
//			.append("}\n");
//			if (i < flag.length - 1){
//				writer.append(",");
//			}
//		}
		writer.append("]\n");
		
	}
	
	
	public static File getJSONFile(File outputDir){
		return new File(outputDir, JSON_FILENAME);
	}
	
	private Writer init(File dir) {
		Writer writer = null;
		try {
			File outputDir = new File(dir, DIR_NAME);
			if (!outputDir.exists()){
				outputDir.mkdir();
			}
			
			File file = getJSONFile(outputDir);
			file.createNewFile();
			log.debug("Created JSON File {}", file.getAbsolutePath());
			writer = new Writer(file);
			writer.append("{\n");
			
    	} catch(Exception e) {
    		try {
    			writer.close();
    		} catch (Exception e2){
    			log.error(e2.getMessage());
    		}
    		log.error(e.getMessage());
			throw new RuntimeException(e);
    	}
		return writer;
	}

	private void close(Writer writer) {
		try {
			writer.write("\n}");
			writer.close();
		} catch( Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	
	
	/**
	 * Custom class
	 * @author ehdez
	 *
	 */
	class Writer extends FileWriter {

		public Writer(File file) throws IOException {
			super(file);
		}
		@Override
		 public Writer append(CharSequence csq) {
			try {
				super.append(csq);
				return this;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
	}

}
