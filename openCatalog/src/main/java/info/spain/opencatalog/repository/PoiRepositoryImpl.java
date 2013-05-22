package info.spain.opencatalog.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import info.spain.opencatalog.domain.Poi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.geo.Circle;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

public class PoiRepositoryImpl implements PoiRepositoryCustom {
	
	@Autowired
	MongoOperations mongoTemplate;
	
	@Override
	@RestResource(path = "within", rel="within")
	public List<Poi> findWithIn(@Param("lat") double lat, @Param("lng") double lng, @Param("radius") double radius) {
		Circle circle = new Circle(lng, lat, radius);
		List<Poi> result = mongoTemplate.find(query(where("location").within(circle)), Poi.class);
		return result;
	}
	
}
