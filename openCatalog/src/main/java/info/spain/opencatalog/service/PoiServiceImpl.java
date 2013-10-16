package info.spain.opencatalog.service;

import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.repository.PoiRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class PoiServiceImpl implements PoiService {
	
	@Autowired
	PoiRepository repo;

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#poi, 'SAVE')")
	public <T extends BasicPoi> T save(T poi) {
		return repo.save(poi);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#id, 'BasicPoi', 'DELETE')")
	public void delete(String id) {
		repo.delete(id);
	}

	@Override
	public BasicPoi findOne(String id) {
		return repo.findOne(id);
	}

	@Override
	public Page<BasicPoi> findByNameEsLikeIgnoreCase(String name, Pageable pageable) {
		return repo.findByNameEsLikeIgnoreCase(name, pageable);
	}


	
}
