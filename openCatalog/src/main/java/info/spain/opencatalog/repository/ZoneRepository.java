package info.spain.opencatalog.repository;

import info.spain.opencatalog.domain.Zone;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

@RestResource(path = "zone", rel="zones")
public interface ZoneRepository extends MongoRepository<Zone, String> {
	
	// { "name" : { "$regex" : name}}
	@RestResource(path = "byName", rel="byName")
	public Page<Zone> findByNameLike(@Param("name") String name, Pageable pageable);
	

}
