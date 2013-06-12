package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.domain.Zone;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import com.sun.xml.internal.ws.util.StringUtils;

/**
 * Permite exportar parte del catálogo a una base de datos SQLite 
 */
public class JSONExporter implements CatalogExporter {
	
	public static Logger log = LoggerFactory.getLogger(JSONExporter.class);
	
	public static final String DIR_NAME = "data";
	public static final String JSON_FILENAME ="openCatalog.json";
	
	private static final Locale LOCALE_ES = new Locale("ES");
	
	private MessageSource messageSource;
	private ImageExporter imageExporter;
	
	
	public JSONExporter(MessageSource messageSource, ImageExporter imageExporter) {
		this.messageSource = messageSource;
		this.imageExporter = imageExporter;	
	}
	
	

	@Override
	public void export(List<Poi> pois, List<Zone> zones, Tag[] tags, File outputDir) {
		Writer writer = init(outputDir);
		exportZones(zones, writer);
		writer.append(", ");
		exportPois(pois, outputDir, writer);
		writer.append(", ");
		exportTags(tags, writer);
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
	
	private void exportPois(List<Poi> pois, File outputDir, Writer writer){
		writer.append("\"pois\": [\n");
		for (Iterator<Poi> iterator = pois.iterator(); iterator.hasNext();) {
			Poi poi = iterator.next();
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
			.append("  \"created\": " + poi.getCreatedDate().getMillis() + ",\n")
			.append("  \"updated\": " + poi.getLastModifiedDate().getMillis() + ",\n")
			.append("  \"location\":{\n")
			.append("    \"lat\": " + poi.getLocation().getLat() + ",\n")
			.append("    \"lng\": " + poi.getLocation().getLng() + ",\n")
			.append("    \"normlng\": 0\n")
			.append("  },\n")
			.append("  \"starred\": 0,\n")
			.append("  \"tags\":" + asStringArray(poi.getTags()) + "\n")
			.append(" }");
			if (iterator.hasNext()){
				writer.append(",\n");
			}
		}
		writer.append("]\n");
	}
	
	private String asQuotedString(String str){
		if (org.apache.commons.lang.StringUtils.isBlank(str)){
			return "null";
		} else {
			return "\"" + str + "\"";
		}
	}
	/**
	 * Export Tags
	 * @param tags
	 * @return
	 */
	private void exportTags(Tag[] tags, Writer writer ) {
		writer.append("\"tags\": [\n");
		for (int i = 0; i < tags.length; i++) {
			Tag tag = tags[i];
			writer.append("{\n")
			.append(" \"tag\": " +  asQuotedString(tag.toString()) + ",\n")
			.append(" \"es\": " +  asQuotedString(translate("tags." + tag.toString(), LOCALE_ES )) + ",\n")
			.append(" \"en\": " +  asQuotedString(translate("tags." + tag.toString(), Locale.ENGLISH)) + ",\n")
			.append(" \"fr\": " +  asQuotedString(translate("tags." + tag.toString(), Locale.FRENCH )) + ",\n")
			.append(" \"de\": " +  asQuotedString(translate("tags." + tag.toString(), Locale.GERMAN)) + ",\n")
			.append(" \"it\": " +  asQuotedString(translate("tags." + tag.toString(), Locale.ITALIAN)) + "\n")
			.append("}\n");
			if (i < tags.length - 1){
				writer.append(",");
			}
		}
		writer.append("]\n");
		
	}
	
	private String translate(String text, Locale locale) {
		return messageSource.getMessage(text, null, text, locale);
	}
	
	
	@SuppressWarnings("rawtypes")
	private String asStringArray( List list){
		StringBuffer result = new StringBuffer("[");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			result.append(asQuotedString(object.toString()));
			if (iterator.hasNext()){
				result.append(", ");
			}
		}
		result.append("]");
		return result.toString();
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
			if (file.exists()){
				file.delete();
			}
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
	
	private String getPathAsJSON(List<GeoLocation> path){
		StringBuilder result = new StringBuilder("[");
		for (Iterator<GeoLocation> iterator = path.iterator(); iterator.hasNext();) {
			GeoLocation loc = iterator.next();
			result.append("{")
			.append("\"lat\":").append(loc.getLat())
			.append(",\"lng\":").append(loc.getLng())
			.append("}");
			if (iterator.hasNext()){
				result.append(", ");
			}
		}
		result.append("]");
		return result.toString();
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
