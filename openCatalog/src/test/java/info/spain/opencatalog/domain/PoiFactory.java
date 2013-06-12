package info.spain.opencatalog.domain;

import info.spain.opencatalog.domain.Tags.Tag;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.google.common.collect.ImmutableSet;

public class PoiFactory extends AbstractFactory {
	
	public static Poi newPoi(String key){
		key = key + "-" + getRandom().nextInt();
		Poi poi = new Poi()
		.setName( new I18nText()
			.setEs("es-"+key+"-name")
			.setEn("en-"+key+"-name")
			.setDe("en-"+key+"-name"))
		.setDescription( new I18nText()
			.setEs("es-"+key+"-description")
			.setEn("en-"+key+"-description")
			.setDe("en-"+key+"-description"))
		.setAddress(new Address()
			.setRoute( key + "-address")
			.setAdminArea1( key + "-area1")
			.setAdminArea2( key + "-area2")
			.setZipCode(key+"-zipCode"))
		.setLocation(randomLocation())
		.setTags(randomTags(getRandom().nextInt(4)));
		return poi;
	}
	
	private static List<Tag> randomTags(int numTags){
		List<Tag> result = new ArrayList<Tag>();
		for(int i=0; i<numTags; i++){
			Tag tag = Tag.values()[(getRandom().nextInt(Tag.values().length))];
			result.add(tag);
		}
		return result;
	}
	
	public static List<Poi> generatePois(int numPois){
		// random
		List<Poi> result = new ArrayList<Poi>();
		for (int i = 0; i < numPois; i++) {
			result.add(newPoi("" + i));
		}
		return result;
	}
	
	public static Resource randomImage(){
		String imageFilename = "/img/" + getRandom().nextInt(9) + ".jpg";
		Resource image = new ClassPathResource(imageFilename);
		Assert.isTrue(image.exists());
		return image;
	}

	public static Poi POI_CASA_CAMPO = PoiFactory.newPoi("Casa de Campo").setLocation(GEO_CASA_CAMPO);
	public static Poi POI_RETIRO = PoiFactory.newPoi("Retiro").setLocation(GEO_RETIRO);
	public static Poi POI_SOL = PoiFactory.newPoi("Sol").setLocation(GEO_SOL);
	public static Poi POI_TEIDE = PoiFactory.newPoi("Teide").setLocation(GEO_TEIDE);
	public static Poi POI_ALASKA = PoiFactory.newPoi("Alaska").setLocation(GEO_ALASKA);
	
	public static ImmutableSet<Poi> WELL_KNOWN_POIS = ImmutableSet.of(POI_CASA_CAMPO, POI_RETIRO, POI_SOL, POI_TEIDE, POI_ALASKA);
	
	
	

}