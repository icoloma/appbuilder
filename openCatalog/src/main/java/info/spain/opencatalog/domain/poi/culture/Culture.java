package info.spain.opencatalog.domain.poi.culture;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.AccessPrice;
import info.spain.opencatalog.domain.poi.ContactInfo;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.TimeTableEntry;
import info.spain.opencatalog.domain.poi.types.BasicPoiType;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Preconditions;

/**
 * Tipo de POI que representa lugares relacionados con Arte y Cultura:  Museos, Monumentos, Parques y Jardines  
 */
@Document(collection="poi")
public class Culture extends AbstractPoi {
	
   
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
    
	
    public Culture(BasicPoiType type) {
    	super(type);
    	Preconditions.checkArgument(PoiTypeID.CULTURE_TYPES.contains(type.getId()));
    }

	@Override
	public Culture setTimetable(TimeTableEntry... timetable) {
		return (Culture) super.setTimetable(timetable);
	}


	@Override
	public Culture setName(I18nText name) {
		return (Culture) super.setName(name);
	}

	@Override
	public Culture setPrices(AccessPrice... prices) {
		return (Culture) super.setPrices(prices);
	}

	@Override
	public Culture setDescription(I18nText description) {
		return (Culture) super.setDescription(description);
	}

	@Override
	public Culture setAddress(Address address) {
		return (Culture) super.setAddress(address);
	}

	@Override
	public Culture setLocation(GeoLocation location) {
		return (Culture) super.setLocation(location);
	}

	@Override
    public Culture setFlags(Flag... flags) {
        return (Culture) super.setFlags(flags);
    }
    
	@Override
 	public Culture setContactInfo(ContactInfo contactInfo) {
		return (Culture) super.setContactInfo(contactInfo);
	}

    public String getReservation() {
		return reservation;
	}

	public Culture setReservation(String reservation) {
		this.reservation = reservation;
		return this;
	}

	public ConstructionType getConstructionType() {
		return constructionType;
	}

	public Culture setConstructionType(ConstructionType constructionType) {
		this.constructionType = constructionType;
		return this;
	}
	
	public ArtisticPeriod getArtisticPeriod() {
		return artisticPeriod;
	}

	public Culture setArtisticPeriod(ArtisticPeriod artisticPeriod) {
		this.artisticPeriod = artisticPeriod;
		return this;
	}

	public HistoricalPeriod getHistoricalPeriod() {
		return historicalPeriod;
	}

	public Culture setHistoricalPeriod(HistoricalPeriod historicalPeriod) {
		this.historicalPeriod = historicalPeriod;
		return this;
	}
	
	public I18nText getEnviroment() {
		return enviroment;
	}

	public Culture setEnviroment(I18nText enviroment) {
		this.enviroment = enviroment;
		return this;
	}

	
	public Designation getDesignation() {
		return designation;
	}

	public Culture setDesignation(Designation designation) {
		this.designation = designation;
		return this;
	}
	
	@Override
	public Culture validate() {
		return (Culture) super.validate();
	}

	@Override
	protected ToStringHelper toStringHelper() {
		return super.toStringHelper()
			.add("constructionType", constructionType)
			.add("artisticPeriod", artisticPeriod)
			.add("historicalPeriod", historicalPeriod)
			.add("enviroment", enviroment)
			.add("designation", designation)
			.add("reservation", reservation);
	}
	

    
}
