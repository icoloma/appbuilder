package info.spain.opencatalog.domain.poi.types.culture;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.DisabledAccessibility;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.PoiTypeRepository.PoiType;
import info.spain.opencatalog.domain.poi.QualityCertificate;
import info.spain.opencatalog.domain.poi.types.AbstractPoiType;
import info.spain.opencatalog.domain.poi.types.ContactInfo;
import info.spain.opencatalog.domain.poi.types.TimeTableEntry;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

/**
 * Tipo de POI que representa lugares relacionados con Arte y Cultura:  Museos, Monumentos, Parques y Jardines  
 */
@Document(collection="poi")
public class CulturePoiType extends AbstractPoiType {
	
    /** precios: */
    private Set<CulturePrice> culturePrices;
    
    /** URL, mail o teléfono para reservas */
    private String reservation; 
    
    /** Tipo de construcción: Muralla, Monumento, etc  */
    private ConstructionType constructionType;
   
    /** Periodo artístico: árabe, neorrománico, ... */
    private ArtisticPeriod artisticPeriod;
   
    /** Periodo histórico: Siglo II, Prehistoira. */
    private HistoricalPeriod historicalPeriod;
    
    /** Entorno: El generalife, ... */
    private I18nText enviroment;
    
    /** Denominación */
    private Designation designation;
    
	
    /** */ 
    //private Set<CultureServices> services;
		

	@Override
	public CulturePoiType setTimetable(Set<TimeTableEntry> timetable) {
		return (CulturePoiType) super.setTimetable(timetable);
	}

	@Override
	public CulturePoiType setTimetable(TimeTableEntry... timetable) {
		return (CulturePoiType) super.setTimetable(timetable);
	}

	@Override
	public CulturePoiType setName(I18nText name) {
		return (CulturePoiType) super.setName(name);
	}


	@Override
	public CulturePoiType setDescription(I18nText description) {
		return (CulturePoiType) super.setDescription(description);
	}

	@Override
	public CulturePoiType setAddress(Address address) {
		return (CulturePoiType) super.setAddress(address);
	}

	@Override
	public CulturePoiType setLocation(GeoLocation location) {
		return (CulturePoiType) super.setLocation(location);
	}

	@Override
	public CulturePoiType setPoiType(PoiType poiType) {
		return (CulturePoiType) super.setPoiType(poiType);
	}

	@Override
    public CulturePoiType setFlags(Flag... flags) {
        return (CulturePoiType) super.setFlags(flags);
    }
    
    @Override
	public CulturePoiType setQualityCertificates( QualityCertificate... qualityCertificates) {
		return (CulturePoiType) super.setQualityCertificates(qualityCertificates);
	}

    @Override
	public CulturePoiType setDisabledAccessibility(Set<DisabledAccessibility> disabledAccessibility) {
		return (CulturePoiType) super.setDisabledAccessibility(disabledAccessibility);
	}
	@Override
	public CulturePoiType setDisabledAccessibility(DisabledAccessibility... disabledAccessibility) {
		return (CulturePoiType) super.setDisabledAccessibility(disabledAccessibility);
	}
	
	@Override
 	public CulturePoiType setContactInfo(ContactInfo contactInfo) {
		return (CulturePoiType) super.setContactInfo(contactInfo);
	}

    public String getReservation() {
		return reservation;
	}

	public CulturePoiType setReservation(String reservation) {
		this.reservation = reservation;
		return this;
	}

	public Set<CulturePrice> getPrices() {
		return culturePrices;
	}

	public CulturePoiType setPrices(Set<CulturePrice> culturePrices) {
		this.culturePrices = culturePrices;
		return this;
	}
	
	public CulturePoiType setPrices(CulturePrice...prices) {
		return setPrices(Sets.newHashSet(prices));
	}
	
	public ConstructionType getConstructionType() {
		return constructionType;
	}

	public CulturePoiType setConstructionType(ConstructionType constructionType) {
		this.constructionType = constructionType;
		return this;
	}
	
	public ArtisticPeriod getArtisticPeriod() {
		return artisticPeriod;
	}

	public CulturePoiType setArtisticPeriod(ArtisticPeriod artisticPeriod) {
		this.artisticPeriod = artisticPeriod;
		return this;
	}

	public HistoricalPeriod getHistoricalPeriod() {
		return historicalPeriod;
	}

	public CulturePoiType setHistoricalPeriod(HistoricalPeriod historicalPeriod) {
		this.historicalPeriod = historicalPeriod;
		return this;
	}
	
	public I18nText getEnviroment() {
		return enviroment;
	}

	public CulturePoiType setEnviroment(I18nText enviroment) {
		this.enviroment = enviroment;
		return this;
	}

	
	public Designation getDesignation() {
		return designation;
	}

	public CulturePoiType setDesignation(Designation designation) {
		this.designation = designation;
		return this;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(getClass())
			.add("poiType", getPoiType())
			.add("id", getId())
			.add("name", getName())
			.add("description", getDescription())
			.add("location", getLocation())
			.add("flags", getFlags())
			.add("createdDate", getCreatedDate())
			.add("lastModifiedDate", getLastModifiedDate())
			.add("timeTable", getTimetable())
			.add("culturePrices", culturePrices)
			.add("reservation", reservation)
			.toString();
	}
    
}
