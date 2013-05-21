package info.spain.opencatalog.repository;

import info.spain.opencatalog.domain.Poi;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;


public interface PoiRepository extends MongoRepository<Poi, String> {
	
	public List<Poi> findByNameLike(@Param("name") String name);
	public List<Poi> findByName(@Param("name") String name, Pageable pageable);

}
