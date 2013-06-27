package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.types.BasicPoi;

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
		
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS  `Flag` (" +
				" `flag` TEXT," +
				" `es`  TEXT," +
				" `en`  TEXT," +
				" `fr`  TEXT," +
				" `de`  TEXT," +
				" `it`  TEXT," +
				" `id` VARCHAR(32) PRIMARY KEY" +
				");");
		
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `PoiFlag` (" +
				" `idPoi` TEXT," +
				" `flag` TEXT" +
				");");
		
		jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS idx_poiFlag ON PoiFlag(flag)");
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
	
	private void exportPois(List<BasicPoi> pois, File outputDir, JdbcTemplate jdbcTemplate){
		int id=0;
		for (BasicPoi poi : pois) {
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
			for (Flag flag : poi.getFlags()) {
				jdbcTemplate.update("INSERT INTO `PoiFlag` (idPoi, flag) VALUES (?,?)", poi.getId(), flag);
			}
		}
	}
	

	
	
	/**
	 * Expor tags
	 */
	private void exportFlags(Flag[] flags, JdbcTemplate jdbcTemplate ) {
		
		for (int i = 0; i < flags.length; i++) {
			Flag flag = flags[i];
			jdbcTemplate.update("INSERT INTO `Flag` (`flag`, `es`, `en`, `fr`, `de`, `it`, `id` ) VALUES (?,?,?,?,?,?,?);",
				flag.toString(),
				translate("flags." + flag, LOCALE_ES ),
				translate("flags." + flag, Locale.ENGLISH),
				translate("flags." + flag, Locale.FRENCH),
				translate("flags." + flag, Locale.GERMAN),
				translate("flags." + flag, Locale.ITALIAN),
				i
			);
					
		}
		
	}
	
	
	@Override
	public void export(List<BasicPoi> pois, List<Zone> zones, Flag[] flags, File outputDir) {
		JdbcTemplate jdbcTemplate = init(outputDir);
		exportZones(zones, jdbcTemplate);
		exportPois(pois, outputDir, jdbcTemplate);
		exportFlags(flags, jdbcTemplate);
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
