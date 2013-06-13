package info.spain.opencatalog.web.controller;

import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.Tags;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.exporter.CatalogExporter;
import info.spain.opencatalog.image.PoiImageUtils;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.repository.ZoneRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.io.Files;


@Controller
public class ExporterController extends AbstractUIController {
	
	@Autowired
	private PoiImageUtils poiImageUtils;
	
	@Autowired
	private PoiRepository poiRepository;
	
	@Autowired
	private ZoneRepository zoneRepository;
	
		
	@RequestMapping(value="/admin/exporter")
	public String show(){
		return "admin/exporter/export";
	}
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	@Qualifier("jsonExporter")
	private CatalogExporter jsonExporter;
	
	@Autowired
	@Qualifier("sqliteExporter")
	private CatalogExporter sqliteExporter;
	
	@RequestMapping(value="/admin/exporter", method=RequestMethod.POST)
	public HttpEntity<byte[]> export(@RequestParam String type) throws IOException {
		
		 CatalogExporter exporter = ("JSON".equals(type)) ? jsonExporter : sqliteExporter;
		 File tmpDir = Files.createTempDir();
		 File outputDir = new File(tmpDir,"openCatalog");
		 outputDir.mkdir();
		 
		 List<Poi> pois = poiRepository.findAll();
		 List<Zone> zones = zoneRepository.findAll();
		 
		 exporter.export(pois, zones, Tags.Tag.values(), outputDir);
		
		 File file = createTarGZ(outputDir);
		 
		 HttpEntity<byte[]> result = getInputStreamAsHttpEntity(new FileInputStream(file), "application/x-gzip", file.length(), "openCatalog.tgz");
		 
		 file.delete();
		 
		 return result;
	}
	
	// TODO: Extract method to utility class
	private File createTarGZ(File dirPath) throws FileNotFoundException, IOException {
		TarArchiveOutputStream out = null;
		File file = File.createTempFile("openCatalog", ".tar.gz");
		log.debug("Creating file {}", file.getAbsolutePath());
		try {
		     out = new TarArchiveOutputStream(new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(file))));
		     addFileToTarGz(out, dirPath, "");
		     out.flush();
		} finally {
		     if(out != null){
		    	 out.close();
		     }
		}
		return file;
    }
	
	private void addFileToTarGz(TarArchiveOutputStream tOut, File file, String base) throws IOException {
        String entryName = base + file.getName();
        TarArchiveEntry tarEntry = new TarArchiveEntry(file, entryName);
        tOut.putArchiveEntry(tarEntry);

        if (file.isFile()) {
            IOUtils.copy(new FileInputStream(file), tOut);
            tOut.closeArchiveEntry();
        } else {
            tOut.closeArchiveEntry();
            File[] children = file.listFiles();
            if (children != null){
                for (File child : children) {
                    log.debug("adding {}", child.getAbsolutePath());
                    addFileToTarGz(tOut, child, entryName + "/");
                }
            }
        }
    }
	

	 


}
