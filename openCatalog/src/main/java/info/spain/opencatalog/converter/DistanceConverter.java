package info.spain.opencatalog.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.Metrics;
/**
 * Deserialize {"distance":d} to Distance(d,Metrics.KILOMETERS)
 *   
 * @author ehdez
 *
 */
public class DistanceConverter implements  Converter<String[],Distance>{

	@Override
	public Distance convert(String[] source) {
		double value = Double.valueOf(source[0]);
		return new Distance(value,Metrics.KILOMETERS);
	}

}
