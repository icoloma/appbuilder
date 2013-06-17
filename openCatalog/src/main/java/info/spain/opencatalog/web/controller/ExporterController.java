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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.io.Files;


@Controller
public class ExporterController extends AbstractUIController {
	
	@Autowired
	private PoiImageUtils poiImageUtils;
	
	@Autowired
	private PoiRepository poiRepository;
	
	@Autowired
	private ZoneRepository zoneRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CatalogExporter jsonExporter;
	
	@Autowired
	private CatalogExporter sqliteExporter;
	
	
	@RequestMapping(value="/admin/exporter")
	public String show(){
		return "admin/exporter/export";
	}
	
	@RequestMapping(value="/admin/exporter/zoneTypeahead", produces="application/json")
	public @ResponseBody String zonesTypeahead(@RequestParam String query){
		PageRequest pageable = new PageRequest(0, 10);
		Page<Zone> zones = zoneRepository.findByNameIgnoreCaseLike(query, pageable);
		StringBuffer result = new StringBuffer("{\"options\": [");
		for (Iterator<Zone> iterator = zones.iterator(); iterator.hasNext();) {
			Zone zone=  iterator.next();
			result.append("\"" + zone.getName() + "\"");
			if (iterator.hasNext()){
				result.append(",");
			}
		}
		result.append("]}");
		return result.toString();
	}
	
	/**
	 * @param q : Name of the zone
	 * @return List of pois inside the zone as JSON 
	 */
	@RequestMapping(value="/admin/exporter/zonePois", produces="application/json")
	public @ResponseBody String zonePois(@RequestParam String q){
		Page<Zone> zones = zoneRepository.findByName(q, new PageRequest(0, 1));
		if (zones.getNumberOfElements() == 0){
			return "{ \"data\" : []}";
		}
		List<Poi> pois = poiRepository.findWithInZone(zones.getContent().get(0).getId());
		return pois2JSON(pois);
	}
	
	@RequestMapping(value="/admin/exporter/area1Typeahead", produces="application/json")
	public @ResponseBody String areas1Typeahead(@RequestParam String query){
		List<String> areas = poiRepository.findAdminArea1ByName(query);
		return asOptions(areas);
	}

	@RequestMapping(value="/admin/exporter/area2Typeahead", produces="application/json")
	public @ResponseBody String areas2Typeahead(@RequestParam String query){
		List<String> areas = poiRepository.findAdminArea2ByName(query);
		return asOptions(areas);
	}
	
	private String asOptions(List<String> values){
		StringBuffer result = new StringBuffer("{\"options\": [");
		for (Iterator<String> iterator = values.iterator(); iterator.hasNext();) {
			String value = iterator.next();
			result.append("\"" + value + "\"");
			if (iterator.hasNext()){
				result.append(",");
			}
		}
		result.append("]}");
		return result.toString();
	}


	@RequestMapping(value="/admin/exporter/poiTypeahead", produces="application/json")
	public @ResponseBody String poiTypeahead(@RequestParam String query){
		Page<Poi> pois= poiRepository.findByNameEsLikeIgnoreCase(query, new PageRequest(0, 10));
		StringBuffer result = new StringBuffer("{\"options\": [");
		for (Iterator<Poi> iterator = pois.iterator(); iterator.hasNext();) {
			Poi poi=  iterator.next();
			result.append("\"" + poi.getName().getEs() + "\"");
			if (iterator.hasNext()){
				result.append(",");
			}
		}
		result.append("]}");
		return result.toString();
	}
	
	/**
	 * @param q : Name of the poi
	 * @return List of pois inside with that name 
	 */
	@RequestMapping(value="/admin/exporter/poi", produces="application/json")
	public @ResponseBody String poi(@RequestParam String q){
		Page<Poi> pois= poiRepository.findByNameEs(q, new PageRequest(0, 10));
		if (pois.getNumberOfElements() == 0){
			return "{ \"data\" : []}";
		}
		return pois2JSON(pois.getContent());
	}
	
	/**
	 * @param q : Name of the zone
	 * @return List of pois that belongs to an adminArea1 as JSON 
	 */
	@RequestMapping(value="/admin/exporter/areaPois1", produces="application/json")
	public @ResponseBody String areaPois1(@RequestParam String q){
		List<Poi> pois = poiRepository.findByAddressArea1(q);
		return pois2JSON(pois);
	}
	
	/**
	 * @param q : Name of the zone
	 * @return List of pois that belongs to an adminArea2 as JSON 
	 */
	@RequestMapping(value="/admin/exporter/areaPois2", produces="application/json")
	public @ResponseBody String areaPois2(@RequestParam String q){
		List<Poi> pois = poiRepository.findByAddressArea2(q);
		return pois2JSON(pois);
	}
	
	private String pois2JSON(List<Poi> pois){
		
		if (pois.size() == 0){
			return "{ \"data\" : []}";
		}

		StringBuffer result = new StringBuffer("{ \"data\" : [");
		for (Iterator<Poi> iterator = pois.iterator(); iterator.hasNext();) {
			Poi poi = iterator.next();
			result.append("{")
			.append("\"id\": \"" + poi.getId() + "\",")
			.append("\"name\": \"" + poi.getName().getEs() + "\",")
			.append("\"description\": \"" + poi.getDescription().getEs() + "\",")
			.append("\"area1\": \"" + poi.getAddress().getAdminArea1() + "\",")
			.append("\"area2\": \"" + poi.getAddress().getAdminArea2() + "\",")
			.append("\"description\": \"" + poi.getDescription().getEs() + "\"")
			.append("}");
			if (iterator.hasNext()){
				result.append(",");
			}
		}
		result.append("]}");
		return result.toString();
	}
	

	@RequestMapping(value="/admin/exporter", method=RequestMethod.POST)
	public HttpEntity<byte[]> export(@RequestParam String type, @RequestParam(value="idPoi") HashSet<String> idPoi) throws IOException {
		
		 CatalogExporter exporter = ("JSON".equals(type)) ? jsonExporter : sqliteExporter;
		 File tmpDir = Files.createTempDir();
		 File outputDir = new File(tmpDir,"openCatalog");
		 outputDir.mkdir();
		 
		 List<Poi> pois = poiRepository.findByIds(idPoi.toArray(new String[]{}));
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
