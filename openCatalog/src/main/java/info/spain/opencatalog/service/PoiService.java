package info.spain.opencatalog.service;

import info.spain.opencatalog.domain.poi.BasicPoi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PoiService  {
	
	public BasicPoi findOne(String id);
	public Page<BasicPoi> findByNameEsLikeIgnoreCase(String name, Pageable pageable);

	public <T extends BasicPoi> T save(T poi);
	public void delete(String id);
	
	
}
