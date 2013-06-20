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
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS  `Poi`  (" +
				" `name_es` TEXT, `desc_es` TEXT," +
				" `name_en` TEXT, `desc_en` TEXT," +
				" `name_de` TEXT, `desc_de` TEXT," +
				" `name_fr` TEXT, `desc_fr` TEXT," +
				" `name_it` TEXT, `desc_it` TEXT," +
				" `thumb` TEXT, `imgs` TEXT," +
	            " `created` numeric , `updated` numeric," +
	            " `lat` numeric, `lon` numeric, `normLon` numeric," +
	            " `starred` boolean," +
	            " `idPoi` TEXT," +
	            " `id` VARCHAR(32) PRIMARY KEY" +
	            ");");

		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS  `Zone` (" +
				" `name_es` TEXT, `desc_es` TEXT," +
				" `name_en` TEXT, `desc_en` TEXT," +
				" `name_de` TEXT, `desc_de` TEXT," +
				" `name_fr` TEXT, `desc_fr` TEXT," +
				" `name_it` TEXT, `desc_it` TEXT," +
				" `path` TEXT," +
				" `idZone` TEXT," +
				" `id` VARCHAR(32) PRIMARY KEY" +
				");");
		
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS  `Tag` (" +
				" `tag` TEXT," +
				" `es`  TEXT," +
				" `en`  TEXT," +
				" `fr`  TEXT," +
				" `de`  TEXT," +
				" `it`  TEXT," +
				" `id` VARCHAR(32) PRIMARY KEY" +
				");");
		
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `PoiTag` (" +
				" `idPoi` TEXT," +
				" `tag` TEXT" +
				");");
		
		jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS idx_poiTag ON PoiTag(tag)");
	}


	/**
	 * Exporta un listado de Zonas; 
	 * Puede ser invocado múltiples veces de forma que no hace falta pasar 
	 * el listado completo de zonas de una sola vez y usar, por ejemplo 
	 * consultas paginadas
	 */
	private void exportZones(List<Zone> zones, JdbcTemplate jdbcTemplate){
		int id=0;
		for (Zone zone: zones){
			jdbcTemplate.update("INSERT INTO `Zone` (`name_es`, `desc_es`, `path`, `idZone`, `id` ) VALUES (?,?,?,?,?);",
				zone.getName(),
				zone.getDescription(),
				getPathAsJSON(zone.getPath()),
				zone.getId(),
				id++
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
		int id=0;
		for (Poi poi : pois) {
			List<String> images = imageExporter.exportImages(poi, outputDir);
			jdbcTemplate.update("INSERT INTO `Poi` (" +
					" `name_es`, `desc_es`," +
					" `name_en`, `desc_en`," +
					" `name_de`, `desc_de`," +
					" `name_fr`, `desc_fr`," +
					" `name_it`, `desc_it`," +
					" `thumb`, `imgs`," +
					" `created`, `updated`," +
					" `lat`, `lon`, `normLon`," +
					" `starred`," +
					" `idPoi`," +
					" `id` ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);", 
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
				poi.getId(),
				id++
			);
			
			for (Tag tag : poi.getTags()) {
				jdbcTemplate.update("INSERT INTO `PoiTag` (idPoi, tag) VALUES (?,?)", poi.getId(), tag.toString());
			}
		}
	}
	

	
	
	/**
	 * Expor tags
	 */
	private void exportTags(Tag[] tags, JdbcTemplate jdbcTemplate ) {
		
		for (int i = 0; i < tags.length; i++) {
			Tag tag = tags[i];
			jdbcTemplate.update("INSERT INTO `Tag` (`tag`, `es`, `en`, `fr`, `de`, `it`, `id` ) VALUES (?,?,?,?,?,?,?);",
				tag.toString(),
				translate("tags." + tag.toString(), LOCALE_ES ),
				translate("tags." + tag.toString(), Locale.ENGLISH),
				translate("tags." + tag.toString(), Locale.FRENCH),
				translate("tags." + tag.toString(), Locale.GERMAN),
				translate("tags." + tag.toString(), Locale.ITALIAN),
				tag.getId()
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
