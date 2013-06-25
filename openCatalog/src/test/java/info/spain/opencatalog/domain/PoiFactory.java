package info.spain.opencatalog.domain;

import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.domain.poi.Accessibility;
import info.spain.opencatalog.domain.poi.Certificate;
import info.spain.opencatalog.domain.poi.Certificate.CertificateType;
import info.spain.opencatalog.domain.poi.Poi;
import info.spain.opencatalog.domain.poi.beach.Beach;
import info.spain.opencatalog.domain.poi.lodging.Regime;
import info.spain.opencatalog.domain.poi.lodging.Season;
import info.spain.opencatalog.domain.poi.lodging.camping.Camping;
import info.spain.opencatalog.domain.poi.lodging.camping.CampingCategory;
import info.spain.opencatalog.domain.poi.lodging.camping.CampingPrice;
import info.spain.opencatalog.domain.poi.lodging.camping.CampingPrice.CampingPriceType;
import info.spain.opencatalog.domain.poi.lodging.camping.CampingService;
import info.spain.opencatalog.domain.poi.lodging.hotel.Hotel;
import info.spain.opencatalog.domain.poi.lodging.hotel.HotelCategory;
import info.spain.opencatalog.domain.poi.lodging.hotel.HotelPrice;
import info.spain.opencatalog.domain.poi.lodging.hotel.HotelPrice.HotelPriceType;
import info.spain.opencatalog.domain.poi.lodging.hotel.HotelService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.google.common.collect.ImmutableSet;

public class PoiFactory extends AbstractFactory {
	
	public static PoiBuilder newPoi(String key){
		key = key + "-" + getRandom().nextInt();
		return  new PoiBuilder()
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
				.setAdminArea1( "adminArea1-" + getRandom().nextInt(10))
				.setAdminArea2( "adminArea2-" + getRandom().nextInt(10))
				.setZipCode(key+"-zipCode"))
			.setLocation(randomLocation())
			.setTags(randomTags(getRandom().nextInt(4)));
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
			result.add(newPoi("" + i).build());
		}
		return result;
	}
	
	public static Resource randomImage(){
		String imageFilename = "/img/" + getRandom().nextInt(9) + ".jpg";
		Resource image = new ClassPathResource(imageFilename);
		Assert.isTrue(image.exists());
		return image;
	}

	
	public static Poi POI_CASA_CAMPO =  newPoi("Casa de Campo").setLocation(GEO_CASA_CAMPO).setAddress(new Address().setAdminArea1("Comunidad de Madrid")).build();
	public static Poi POI_RETIRO = newPoi("Retiro").setLocation(GEO_RETIRO).setAddress(new Address().setAdminArea1("Comunidad de Madrid")).build();
	public static Poi POI_SOL = newPoi("Sol").setLocation(GEO_SOL).setAddress(new Address().setAdminArea1("Comunidad de Madrid")).build();
	public static Poi POI_TEIDE = newPoi("Teide").setLocation(GEO_TEIDE).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Tenerife")).build();
	public static Poi POI_PLAYA_TERESITAS= newPoi("Playa de las Teresitas").setLocation(GEO_PLAYA_TERESITAS).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Tenerife")).build();
	public static Poi POI_ROQUE_NUBLO= newPoi("Roque Nublo").setLocation(GEO_ROQUE_NUBLO).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Gran Canaria")).build();
	public static Poi POI_ALASKA = newPoi("Alaska").setLocation(GEO_ALASKA).build();
	
	public static ImmutableSet<Poi> WELL_KNOWN_POIS = ImmutableSet.of(POI_CASA_CAMPO, POI_RETIRO, POI_SOL, POI_TEIDE, POI_ALASKA, POI_PLAYA_TERESITAS, POI_ROQUE_NUBLO);
	
	public static Hotel HOTEL;
	public static Camping CAMPING;
	public static Beach BEACH;
	
	static {
		// Hotel
		List<HotelPrice> hotelPrices = new ArrayList<HotelPrice>();
		hotelPrices.add(new HotelPrice(HotelPriceType.HAB2, Season.HIGH_SEASON, Regime.AD, Double.valueOf(55)));
		hotelPrices.add(new HotelPrice(HotelPriceType.HAB2, Season.LOW_SEASON, Regime.AD, Double.valueOf(45)));
		hotelPrices.add(new HotelPrice(HotelPriceType.HAB2, Season.CHIRSTMAS, Regime.AD, Double.valueOf(35)));
		
		List<HotelService> hotelServices = new ArrayList<HotelService>();
		hotelServices.add(HotelService.BAR);
		hotelServices.add(HotelService.CASINO);
		hotelServices.add(HotelService.CREDIT_CARD);
		
		HOTEL = new Hotel();
		
		Poi.copyData(HOTEL, PoiFactory.newPoi("Hotel California").build());
		HOTEL.setPrices(hotelPrices);
		HOTEL.setCategory(HotelCategory.STAR_2);
		HOTEL.setServices(hotelServices);
		HOTEL.getAccessibility().add(Accessibility.ADAPTED_ROOMS);
		HOTEL.getAccessibility().add(Accessibility.DISABLED_ACCESS);
		HOTEL.getCertificate().add( new Certificate(CertificateType.ACCESIBILIDAD, 2013));
		HOTEL.getCertificate().add( new Certificate(CertificateType.ACCESIBILIDAD, 2012));
		
		// Camping
		List<CampingPrice> campingPrices = new ArrayList<CampingPrice>();
		campingPrices.add(new CampingPrice(CampingPriceType.BUNGALOW, Season.HIGH_SEASON, Regime.AD, Double.valueOf(55)));
		campingPrices.add(new CampingPrice(CampingPriceType.BUS, Season.LOW_SEASON, Regime.AD, Double.valueOf(45)));
		campingPrices.add(new CampingPrice(CampingPriceType.MOTORBIKE, Season.CHIRSTMAS, Regime.AD, Double.valueOf(35)));
		
		List<CampingService> campingServices = new ArrayList<CampingService>();
		campingServices.add(CampingService.BEACH);
		campingServices.add(CampingService.BIKE_RENT);
		campingServices.add(CampingService.CREDIT_CARD);
		
		CAMPING = new Camping();
		
		Poi.copyData(CAMPING, PoiFactory.newPoi("Montaña rajada").build());
		CAMPING .setPrices(campingPrices);
		CAMPING .setCategory(CampingCategory.CAT_3);
		CAMPING .setServices(campingServices);
		CAMPING.getAccessibility().add(Accessibility.GUIDE_DOG_ALLOWED);
		CAMPING.getAccessibility().add(Accessibility.PARKING_ACCESSIBLE);
		CAMPING.getCertificate().add( new Certificate(CertificateType.Q_CALIDAD, 2012));
		
		
		// Beach
		BEACH = new Beach();
		Poi.copyData(BEACH, PoiFactory.newPoi("Beach One").build());
		
		CAMPING.getAccessibility().add(Accessibility.GUIDE_DOG_ALLOWED);
		CAMPING.getCertificate().add( new Certificate(CertificateType.BANDERA_AZUL, 2012));
		
		
		
	}
	
	
}