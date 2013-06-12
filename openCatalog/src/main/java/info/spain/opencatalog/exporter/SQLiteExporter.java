package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.domain.Zone;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Permite exportar parte del catálogo a una base de datos SQLite 
 */
public class SQLiteExporter implements CatalogExporter {
	
	public static Logger log = LoggerFactory.getLogger(SQLiteExporter.class);
	
	public static final String DIR_NAME = "data";
	public static final String DB_FILENAME = "openCatalog.db"; 
	
	private static final Locale LOCALE_ES = new Locale("ES");
	
	// Fichero donde se almacenará la base de datos SQLite
	private File outputFile;
	private JdbcTemplate jdbcTemplate;
	private MessageSource messageSource;
	private ImageExporter imageExporter;
	
	public SQLiteExporter(MessageSource messageSource,  ImageExporter imageExporter) {
		this.messageSource = messageSource;
		this.imageExporter  = imageExporter;
	}

	private void createSchemas(JdbcTemplate jdbcTemplate, File file){
		jdbcTemplate.execute("create table Poi  (name, description, thumb, imgs, created, updated, lat, lon, normLon, starred, tag, id );");
		jdbcTemplate.execute("create table Zone (name, description, path, id );");
		jdbcTemplate.execute("create table Tag  (tag, es, en, fr, de, it );");
	}


	/**
	 * Exporta un listado de Zonas; 
	 * Puede ser invocado múltiples veces de forma que no hace falta pasar 
	 * el listado completo de zonas de una sola vez y usar, por ejemplo 
	 * consultas paginadas
	 */
	private void exportZones(List<Zone> zones){
		for (Zone zone: zones){
			jdbcTemplate.update("insert into Zone (name, description, path, id ) values(?,?,?,?);",
				zone.getName(),
				zone.getDescription(),
				getPathAsJSON(zone.getPath()),
				zone.getId()
			);
		}
			
	}

	/**
	 * Exporta un listado de Pois
 	 * Puede ser invocado múltiples veces de forma que no hace falta pasar 
	 * el listado completo de Pois de una sola vez y usar, por ejemplo 
	 * consultas paginadas
	 */
	
	private void exportPois(List<Poi> pois){
		for (Poi poi : pois) {
			List<String> images = imageExporter.exportImages(poi);
			jdbcTemplate.update("insert into Poi  (name, description, thumb, imgs, created, updated, lat, lon, normLon, starred, tag, id ) values (?,?,?,?,?,?,?,?,?,?,?,?);", 
				poi.getName().getEs(),  
				poi.getDescription().getEs(), 
				"thumb.png", // thumb
				asStringArray(images), // imgs
				poi.getCreatedDate().getMillis(),
				poi.getLastModifiedDate().getMillis(),
				poi.getLocation().getLat(),
				poi.getLocation().getLng(),
				"", // normLon
				"0", // starred
				asStringArray(poi.getTags()),
				poi.getId()
			);
			
		}
	}
	

	
	
	/**
	 * Expor tags
	 */
	private void exportTags(Tag[] tags ) {
		
		for (int i = 0; i < tags.length; i++) {
			Tag tag = tags[i];
			jdbcTemplate.update("insert into Tag (tag, es, en, fr, de, it ) values (?,?,?,?,?,?);",
				tag.toString(),
				translate("tags." + tag.toString(), LOCALE_ES ),
				translate("tags." + tag.toString(), Locale.ENGLISH),
				translate("tags." + tag.toString(), Locale.FRENCH),
				translate("tags." + tag.toString(), Locale.GERMAN),
				translate("tags." + tag.toString(), Locale.ITALIAN)
			);
					
		}
		
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
	
	
	@Override
	public void export(List<Poi> pois, List<Zone> zones, Tag[] tags) {
		exportZones(zones);
		exportPois(pois);
		exportTags(tags);
	}

	@Override
	public void init(File outputDir) {
		try {
			if (!outputDir.exists()){
				outputDir.mkdir();
			}
			this.outputFile = SQLiteExporter.getDBFile(outputDir);
			if (outputFile.exists()){
				outputFile.delete();
			}
			log.debug("Created SQLite file {}", outputFile.getAbsolutePath());
			this.jdbcTemplate = new JdbcTemplate(getDataSource(outputDir));
			createSchemas(jdbcTemplate, this.outputFile);
		} catch(Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() {}
	
	private static File getDBFile(File outputDir){
		File dir = new File(outputDir, DIR_NAME);
		if (!dir.exists()){
			dir.mkdir();
		}
		return new File(dir, DB_FILENAME);
	}
	
	public static DataSource getDataSource(File outputDir){
		try {
			File file = getDBFile(outputDir);
			String path = file.getAbsolutePath();
			BasicDataSource ds = new BasicDataSource();
			ds.setDriverClassName("org.sqlite.JDBC");
			ds.setUrl("jdbc:sqlite:" + path);
		return ds;
		} catch(Exception e){
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	private String getPathAsJSON(List<GeoLocation> path){
		StringBuilder result = new StringBuilder("[");
		for (Iterator<GeoLocation> iterator = path.iterator(); iterator.hasNext();) {
			GeoLocation loc = iterator.next();
			result.append("{")
			.append(" \"lat\":").append(loc.getLat())
			.append(",\"lng\":").append(loc.getLng())
			.append("}");
			if (iterator.hasNext()){
				result.append(",");
			}
		}
		result.append("]");
		return result.toString();
	}

}
