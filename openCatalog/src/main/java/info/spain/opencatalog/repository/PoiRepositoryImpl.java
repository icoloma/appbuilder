package info.spain.opencatalog.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import info.spain.opencatalog.api.controller.SearchQuery;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.poi.BasicPoi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.geo.Circle;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.geo.Polygon;
import org.springframework.data.mongodb.core.geo.Shape;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;


public class PoiRepositoryImpl implements PoiRepositoryCustom {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	MongoOperations mongoTemplate;
	
	@Autowired
	PoiRepository repo;
	
	/**
	 *  Busca los POI dentro de un polígono 
	 */
	@Override
	public List<BasicPoi> findWithInZone(String idZone) {
		Criteria criteria = getCriteriaByZone(idZone);
		if (criteria == null ){
			return new ArrayList<BasicPoi>();
		}
		return mongoTemplate.find(query(criteria), BasicPoi.class);
	}
	
	/**
	 * Busca los poi en un radio dado
	 */
	@Override
	public List<BasicPoi> findWithIn(double lat, double lng, double radius) {
		Circle circle = new Circle(lng, lat, radius);
		List<BasicPoi> result = mongoTemplate.find(query(where("location").within(circle)), BasicPoi.class);
		return result;
	}
	
	/**
	 * Devuelve las diferenets address.adminArea1 existentes
	 */
	public List<String> findAdminArea1ByName(String name){
		return getUniqueFields("address.adminArea1", name);
	}

	/**
	 * Devuelve las diferenets address.adminArea2 existentes
	 */
	public List<String> findAdminArea2ByName(String name){
		return getUniqueFields("address.adminArea2", name);
	}
	
	/**
	 * Búsqueda Custom 
	 * @param SearchQuery ; Parámetros de búsqueda
	 * @return Pois que cumplen los diferentes criterios de búsquedas dentro de SearchQuery
	 * @see SearchQuery
	 * 
	 *  Si no se especifica ninguno, devuelve un listado vacío
	 *  Dentro de un mismo criterio de búsqueda se combinancon OR :  flag in [flag1,flag2,flag3] 
	 *  Si se especifica más de criterio, se combinarán con   AND :  flags && types
	 */
	@Override
	public Page<BasicPoi> findCustom( SearchQuery searchQuery, Pageable pageable) {
 		
 		Criteria criteria;
 		List<Criteria> andCriterias = new ArrayList<Criteria>();
 		addCriteriaIfNotNull( andCriterias, getCriteriaUpatedAfter(searchQuery));
 		addCriteriaIfNotNull( andCriterias, getCriteriaZone(searchQuery));
 		addCriteriaIfNotNull( andCriterias, getCriteriaValueInArrayCriteria("flags", searchQuery.getFlagList()));
 		addCriteriaIfNotNull( andCriterias, getCriteriaValueInArrayCriteria("type", searchQuery.getPoiTypeIdList()));
 		
 		if (andCriterias.size() == 0){
			// No criterias, no result
			return new PageImpl<BasicPoi>( new ArrayList<BasicPoi>());
 		} else if ( andCriterias.size() == 1){
 			// Only one criteria; do not use AND
 			criteria = andCriterias.get(0);
 		} else {
 			criteria = new Criteria().andOperator(andCriterias.toArray(new Criteria[]{}));
 		}
 		
		Query query = query(criteria)
				.with(pageable)
				.with(new Sort(Direction.ASC, "lastModified"));;
	
		log.trace(query.toString());
		List<BasicPoi> result = mongoTemplate.find(query, BasicPoi.class);
		return new PageImpl<BasicPoi>(result, pageable, result.size());
 	}

	
	/**
	 * @param idZone
	 * @return  { 'location' : { $geoWithin : { $polygon : [ [x1,y1],[x2,y2],[x3,y3],... ] } } }
	 */
	private Criteria getCriteriaByZone(String idZone) {
		Zone zone = mongoTemplate.findOne(query(where("_id").is(idZone)), Zone.class);
		return getCriteriaByZone(zone);
	}
	
	Criteria getCriteriaByZone(Zone zone) {
		if (zone != null){
			List<GeoLocation> path = zone.getPath();
			Point p1 = asPoint(path.get(0));
			Point p2 = asPoint(path.get(1));
			Point p3 = asPoint(path.get(2));
			List<Point> others = new ArrayList<Point>();
			for(int i=3; i<path.size(); i++){
				others.add(asPoint(path.get(i)));
			}
			Shape polygon = new Polygon(p1,p2,p3, others.toArray(new Point[]{}));
			return where("location").within(polygon);
		}
		return null;
	}
	
	
	
	
	/**
	 * Convierte un GeoLocation a un Point de mongo
	 * @param geo
	 * @return
	 */
	private Point asPoint(GeoLocation geo){
		return new Point(geo.getLng(), geo.getLat());
	}
	
	
	private void addCriteriaIfNotNull(List<Criteria> criterias, Criteria criteria){
		if (criteria != null){
			criterias.add(criteria);
		}
	}
	
	/** criteria a usar con SearchQuery.updateAfter */
	Criteria getCriteriaUpatedAfter(SearchQuery searchQuery){
		Criteria result = null;
		if (searchQuery != null) {
			String since = searchQuery.getUpdatedAfter();
			if (!StringUtils.isEmpty(since)){
				result = where("lastModified").gt( new DateTime(since).toDate()); 
			}
		}
		return result;
	}
	
	/** Criteria a usar con  SearchQuery.updateAfter */
	private Criteria getCriteriaZone(SearchQuery searchQuery){
		Criteria result = null;
		if (searchQuery != null) {
			String idZone = searchQuery.getIdZone();
			if (!StringUtils.isEmpty(idZone)) {
				result = getCriteriaByZone(idZone);
			} 
		} 
		return result;
	}
		
	/** Criteria a usar para un valor en un array */
	Criteria getCriteriaValueInArrayCriteria(String key,  List<String> values ){
		Criteria result = null;
		if (values != null && values.size() > 0 ){
			result = where(key).in( values);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	/**
	 * FIXME: Utilizar limit y sort en la consulta 
	 * https://jira.mongodb.org/browse/SERVER-2130
	 */
	private List<String> getUniqueFields(String fieldName, String fieldValue){
		DBCollection coll = mongoTemplate.getCollection("poi");
		BasicDBObject query =  new BasicDBObject().append(fieldName, new BasicDBObject().append("$regex", fieldValue).append("$options","i"));
		List<String> res = coll.distinct(fieldName, query);
		Collections.sort(res);
		return res;
	}
	
}
