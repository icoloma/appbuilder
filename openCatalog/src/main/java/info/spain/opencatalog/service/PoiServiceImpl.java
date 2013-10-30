package info.spain.opencatalog.service;

import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.types.PoiFactory;
import info.spain.opencatalog.exception.NotFoundException;
import info.spain.opencatalog.repository.PoiRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PoiServiceImpl implements PoiService {
	
	@Autowired
	PoiRepository repo;

	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#poi, 'EDIT')")
	public <T extends BasicPoi> T save(T poi) {
		if (StringUtils.isEmpty(poi.getUuid())){
			poi.setUuid(UUID.randomUUID().toString());
		}
		return repo.save(poi);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#id, 'BasicPoi', 'EDIT')")
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

	@Override
	public List<BasicPoi> findByUuid(String uuid) {
		List<BasicPoi>  pois = repo.findByUuid(uuid);
		Collections.sort(pois, new Comparator<BasicPoi>() {
			@Override
			public int compare(BasicPoi o1, BasicPoi o2) {
					if (o1.isPublished()) 
						return -1;
					else if (o2.isPublished())
						return +1;
					else 
						return o2.getLastModified().compareTo(o1.getLastModified());
			}
		});
		return pois;
	}

	/**
	 * Crea una revisión (copia) del poi 
	 */
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#poi, 'EDIT')")
	public BasicPoi createRevision(String id) {
		BasicPoi dbPoi = findOne(id);
		if (dbPoi == null) {
			throw new NotFoundException("poi", id);
		}
		BasicPoi poi = PoiFactory.newInstance(dbPoi.getType())
		.copyData(dbPoi)
		.setId(null)
		.setPublished(false);
		
		return save(poi);
	}

	/**
	 * Publica un Poi, estableciendo el resto como no publicados
	 */
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void publish(String id) {
		BasicPoi dbPoi = findOne(id);
		if (dbPoi == null) {
			throw new NotFoundException("poi", id);
		}
		List<BasicPoi> pois = findByUuid(dbPoi.getUuid());
		for (BasicPoi poi : pois) {
			poi.setPublished( poi.getId().equals(id));
			save(poi);
		}
	}
	
	
	
	
	
	
}
