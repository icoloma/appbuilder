package info.spain.opencatalog.repository;

import info.spain.opencatalog.api.controller.SearchQuery;
import info.spain.opencatalog.domain.poi.BasicPoi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PoiRepositoryCustom {
	public Page<BasicPoi> findCustom(SearchQuery query, Pageable pageable);
	public List<BasicPoi> findWithIn(double lat, double lng, double radius);
	public List<BasicPoi> findWithInZone(String zoneId);
	public List<String> findAdminArea1ByName(String name);
	public List<String> findAdminArea2ByName(String name);
}
