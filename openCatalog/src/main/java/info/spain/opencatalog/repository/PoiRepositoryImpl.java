package info.spain.opencatalog.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.Zone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.geo.Circle;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.geo.Polygon;
import org.springframework.data.mongodb.core.geo.Shape;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class PoiRepositoryImpl implements PoiRepositoryCustom {
	
	@Autowired
	MongoOperations mongoTemplate;
	
	/**
	 *  Busca los POI dentro de un polígono 
	 *	query : { 'location' : { $geoWithin : { $polygon : [ [x1,y1],[x2,y2],[x3,y3],... ] } } }
	 */
	@Override
	public List<Poi> findWithInZone(String zoneId) {
		List<Poi> result = new ArrayList<>();
		Zone zone = mongoTemplate.findOne(query(where("_id").is(zoneId)), Zone.class);
		if (zone != null){
			List<GeoLocation> path = zone.getPath();
			Point p1 = asPoint(path.get(0));
			Point p2 = asPoint(path.get(1));
			Point p3 = asPoint(path.get(2));
			List<Point> others = new ArrayList<>();
			for(int i=3; i<path.size(); i++){
				others.add(asPoint(path.get(i)));
			}
			Shape polygon = new Polygon(p1,p2,p3, others.toArray(new Point[]{}));
			result = mongoTemplate.find(query(where("location").within(polygon)), Poi.class);
		}
		return result;
	}
	
	private Point asPoint(GeoLocation geo){
		return new Point(geo.getLng(), geo.getLat());
	}
	
	@Override
	public List<Poi> findWithIn(double lat, double lng, double radius) {
		Circle circle = new Circle(lng, lat, radius);
		List<Poi> result = mongoTemplate.find(query(where("location").within(circle)), Poi.class);
		return result;
	}
	
	public List<String> findAdminArea1ByName(String name){
		return getUniqueFields("address.adminArea1", name);
	}

	public List<String> findAdminArea2ByName(String name){
		return getUniqueFields("address.adminArea2", name);
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
