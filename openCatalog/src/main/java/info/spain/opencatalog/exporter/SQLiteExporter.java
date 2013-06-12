package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.domain.Zone;

import java.io.File;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Permite exportar parte del catálogo a una base de datos SQLite 
 */
public class SQLiteExporter extends AbstractExporter implements CatalogExporter {
	
	public static final String DIR_NAME = "data";
	public static final String DB_FILENAME = "openCatalog.db"; 
	
	public SQLiteExporter(MessageSource messageSource,  ImageExporter imageExporter) {
		this.messageSource = messageSource;
		this.imageExporter  = imageExporter;
	}

	private void createSchemas(JdbcTemplate jdbcTemplate, File file){
		jdbcTemplate.execute("create table Poi  (" +
				" name_es text, desc_es text," +
				" name_en text, desc_en text," +
				" name_de text, desc_de text," +
				" name_fr text, desc_fr text," +
				" name_it text, desc_it text," +
				" thumb text, imgs text," +
	            " created numeric , updated numeric, lat numeric, lon numeric, normLon numeric," +
	            " starred boolean , tag text," +
	            " id text PRIMARY KEY);");

		jdbcTemplate.execute("create table Zone (" +
				" name_es text, desc_es text," +
				" name_en text, desc_en text," +
				" name_de text, desc_de text," +
				" name_fr text, desc_fr text," +
				" name_it text, desc_it text," +
				" path text," +
				" id text PRIMARY KEY);");
		
		jdbcTemplate.execute("create table Tag  (tag text, es text, en text, fr text, de text, it text );");
	}


	/**
	 * Exporta un listado de Zonas; 
	 * Puede ser invocado múltiples veces de forma que no hace falta pasar 
	 * el listado completo de zonas de una sola vez y usar, por ejemplo 
	 * consultas paginadas
	 */
	private void exportZones(List<Zone> zones, JdbcTemplate jdbcTemplate){
		for (Zone zone: zones){
			jdbcTemplate.update("insert into Zone (name_es, desc_es, path, id ) values(?,?,?,?);",
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
	
	private void exportPois(List<Poi> pois, File outputDir, JdbcTemplate jdbcTemplate){
		for (Poi poi : pois) {
			List<String> images = imageExporter.exportImages(poi, outputDir);
			jdbcTemplate.update("insert into Poi (" +
					" name_es, desc_es," +
					" name_en, desc_en," +
					" name_de, desc_de," +
					" name_fr, desc_fr," +
					" name_it, desc_it," +
					" thumb, imgs," +
					" created, updated," +
					" lat, lon, normLon," +
					" starred," +
					" tag," +
					" id ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);", 
				poi.getName().getEs(),  
				poi.getDescription().getEs(), 
				poi.getName().getEn(),  
				poi.getDescription().getEn(), 
				poi.getName().getDe(),  
				poi.getDescription().getDe(),
				poi.getName().getFr(),  
				poi.getDescription().getFr(), 
				poi.getName().getIt(),  
				poi.getDescription().getIt(), 
				"thumb.png", // thumb
				asStringArray(images), // imgs
				poi.getCreatedDate().getMillis(),
				poi.getLastModifiedDate().getMillis(),
				poi.getLocation().getLat(),
				poi.getLocation().getLng(),
				getNormLong(poi.getLocation()), // normLon
				false, // starred
				asStringArray(poi.getTags()),
				poi.getId()
			);
			
		}
	}
	

	
	
	/**
	 * Expor tags
	 */
	private void exportTags(Tag[] tags, JdbcTemplate jdbcTemplate ) {
		
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
	
	
	@Override
	public void export(List<Poi> pois, List<Zone> zones, Tag[] tags, File outputDir) {
		JdbcTemplate jdbcTemplate = init(outputDir);
		exportZones(zones, jdbcTemplate);
		exportPois(pois, outputDir, jdbcTemplate);
		exportTags(tags, jdbcTemplate);
	}

	private JdbcTemplate init(File outputDir) {
		try {
			if (!outputDir.exists()){
				outputDir.mkdir();
			}
			File outputFile = SQLiteExporter.getDBFile(outputDir);
			if (outputFile.exists()){
				outputFile.delete();
			}
			log.debug("Created SQLite file {}", outputFile.getAbsolutePath());
			JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource(outputDir));
			createSchemas(jdbcTemplate, outputFile);
			return jdbcTemplate;
		} catch(Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

		
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
			throw new RuntimeException(e);
		}
	}
	
}
