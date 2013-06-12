package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.domain.Zone;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

/**
 * Permite exportar parte del catálogo a una base de datos SQLite 
 */
public class JSONExporter implements CatalogExporter {
	
	public static Logger log = LoggerFactory.getLogger(JSONExporter.class);
	
	public static final String JSON_FILENAME ="openCatalog.json";
	
	private static final Locale LOCALE_ES = new Locale("ES");
	
	// Fichero donde se almacenará la base de datos SQLite
	private FileWriter writer;
	private MessageSource messageSource;
	private ImageExporter imageExporter;

	public JSONExporter(MessageSource messageSource, ImageExporter imageExporter) {
		this.messageSource = messageSource;
		this.imageExporter = imageExporter;	
	}
	
	

	@Override
	public void export(List<Poi> pois, List<Zone> zones, Tag[] tags) {
		exportZones(zones);
		write(", ");
		exportPois(pois);
		write(", ");
		exportTags(tags);
	}



	/**
	 * Exporta un listado de Zonas; 
	 * Puede ser invocado múltiples veces de forma que no hace falta pasar 
	 * el listado completo de zonas de una sola vez y usar, por ejemplo 
	 * consultas paginadas
	 */
	private void exportZones(List<Zone> zones){
		write("\"zones\": [\n");
		for (Iterator<Zone> iterator = zones.iterator(); iterator.hasNext();) {
			Zone zone = iterator.next();
			write("{\n");
			write(" \"name\": \"" + zone.getName() + "\",\n");
			write(" \"description\" : \"" + zone.getDescription() + "\",\n"); 
			write(" \"path\": " + getPathAsJSON(zone.getPath())+ ",\n"); 
			write(" \"id\": " + zone.getId() + "\n");
			write("}");
			if (iterator.hasNext()){
				write(",\n");
			}
		}
		write("]\n");
			
	}

	
	
	
	/**
	 * Exporta un listado de Pois
	 */
	
	private void exportPois(List<Poi> pois){
		write("\"pois\": [\n");
		for (Iterator<Poi> iterator = pois.iterator(); iterator.hasNext();) {
			Poi poi = iterator.next();
			List<String> images = imageExporter.exportImages(poi);
			write("{\n");
			write("  \"id\":" + poi.getId() + ",\n");
			write("  \"name\":{\n");
			write("    \"es\": \"" + poi.getName().getEs() + "\",\n");
			write("    \"en\": \"" + poi.getName().getEn() + "\",\n");
			write("    \"fr\": \"" + poi.getName().getFr() + "\",\n");
			write("    \"de\": \"" + poi.getName().getDe() + "\",\n");
			write("    \"it\": \"" + poi.getName().getIt() + "\"\n");
			write("  },\n");
			write("  \"description\":{\n");
			write("    \"es\": \"" + poi.getDescription().getEs() + "\",\n");
			write("    \"en\": \"" + poi.getDescription().getEn() + "\",\n");
			write("    \"fr\": \"" + poi.getDescription().getFr() + "\",\n");
			write("    \"de\": \"" + poi.getDescription().getDe() + "\",\n");
			write("    \"it\": \"" + poi.getDescription().getIt() + "\"\n");
			write("  },\n");
			write("  \"thumb\": \"thumb.png\",\n");
			write("  \"imgs\": " +  asStringArray(images)+ ",\n");
			write("  \"created\": " + poi.getCreatedDate().getMillis() + ",\n");
			write("  \"updated\": " + poi.getLastModifiedDate().getMillis() + ",\n");
			write("  \"location\":{\n");
			write("    \"lat\": " + poi.getLocation().getLat() + ",\n");
			write("    \"lng\": " + poi.getLocation().getLng() + ",\n");
			write("    \"normlng\": 0\n");
			write("  },\n");
			write("  \"starred\": 0,\n");
			write("  \"tags\":" + asStringArray(poi.getTags()) + "\n");
			write(" }");
			if (iterator.hasNext()){
				write(",\n");
			}
		}
		write("]\n");
	}
	
	/**
	 * Export Tags
	 * @param tags
	 * @return
	 */
	private void exportTags(Tag[] tags ) {
		write("\"tags\": [\n");
		for (int i = 0; i < tags.length; i++) {
			Tag tag = tags[i];
			write("{\n");
			write(" \"tag\": \"" +  tag.toString() + "\",\n");
			write(" \"es\": \"" + translate("tags." + tag.toString(), LOCALE_ES ) + "\",\n");
			write(" \"en\": \"" + translate("tags." + tag.toString(), Locale.ENGLISH) + "\",\n");
			write(" \"fr\": \"" + translate("tags." + tag.toString(), Locale.FRENCH ) + "\",\n");
			write(" \"de\": \"" + translate("tags." + tag.toString(), Locale.GERMAN) + "\",\n");
			write(" \"it\": \"" + translate("tags." + tag.toString(), Locale.ITALIAN) + "\"\n");
			write("}\n");
			if (i < tags.length - 1){
				write(",");
			}
		}
		write("]\n");
		
	}
	
	private String translate(String text, Locale locale) {
		return messageSource.getMessage(text, null, text, locale);
	}
	
	
	@SuppressWarnings("rawtypes")
	private String asStringArray( List list){
		StringBuffer result = new StringBuffer("[");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			result.append("\"").append(object.toString()).append("\"");
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
	
	@Override
	public void init(File outputDir) {
		try {
			if (!outputDir.exists()){
				outputDir.mkdir();
			}
			File file = getJSONFile(outputDir);
			if (file.exists()){
				file.delete();
			}
			file.createNewFile();
			writer = new FileWriter(file);
			write("{\n");
			
    	} catch(Exception e) {
    		try {
    			writer.close();
    		} catch (Exception e2){
    			log.error(e2.getMessage());
    		}
    		log.error(e.getMessage());
			throw new RuntimeException(e);
    	}
	}

	@Override
	public void close() {
		try {
			write("\n}");
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
	
	private void write( String text ){
		try {
			writer.append(text);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
