package travel.spain.opencatalog.repository;

import org.springframework.data.repository.CrudRepository;

import travel.spain.opencatalog.domain.Poi;

public interface PoiRepository extends CrudRepository<Poi, String> {

}
