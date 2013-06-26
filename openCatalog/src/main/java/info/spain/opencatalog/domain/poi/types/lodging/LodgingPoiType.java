package info.spain.opencatalog.domain.poi.types.lodging;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.DisabledAccessibility;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.PoiTypeRepository;
import info.spain.opencatalog.domain.poi.PoiTypeRepository.PoiType;
import info.spain.opencatalog.domain.poi.QualityCertificate;
import info.spain.opencatalog.domain.poi.types.AbstractPoiType;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

/**
 * 
 * Define los campos comunes que tendrán los Poi de tipo Lodging
 * 
 */
@Document(collection="poi")
public class LodgingPoiType extends AbstractPoiType {
	
	/** servicios que dispone el recinto del alojamiento: piscina, pub, terraza */
    private Set<LodgingFlag> lodgingFlags;
    
    /** tipos de alojamiento: habitación doble, individual, etc */
    private Set<LodgingType> lodgingTypes;

    /** servicios que se ofrece en el tipo de alojamiento: Jacuzzi, Caja fuerte, Wifi */
    private Set<LodgingTypeFlag> lodgingTypeFlags;
    
    /** valoración: 3 Estrellas, 2 tenedores, etc */
    private Set<Score> scores;

    /** precios del alojamiento: hab-doble en temporada alta con pensión completa */
    private Set<LodgingPrice> lodgingPrices;
    
    
    @Override
    public void validateTypeAllowedValues(){
    	super.validateTypeAllowedValues();
    	if (!PoiTypeRepository.LODGING_TYPES.contains(getPoiType())){
    		throw new IllegalArgumentException("Asignación de tipo incorrecto. Tipos permitidos : " + PoiTypeRepository.LODGING_TYPES + ", Tipo asignado :" + getPoiType());
    	}
    	LodgingPoiType type = (LodgingPoiType) PoiTypeRepository.getPoiType(getPoiType());
    	super.validateFlags(type.getLodgingFlags(), this.getLodgingFlags());
    	super.validateFlags(type.getLodgingTypes(), this.getLodgingTypes());
    	super.validateFlags(type.getLodgingTypeFlags(), this.getLodgingTypeFlags());
    	super.validateFlags(type.getScores(), this.getScores());
    	validateLodgingPrices();
    	
    }

    /** Comprueba que todos los precios estén asignados a los lodgingTypes soportados por la instancia en concreto*/
    private void validateLodgingPrices(){
    	for (LodgingPrice price : lodgingPrices) {
    		super.validateFlags(getLodgingTypes(), Sets.newHashSet(price.getLodgingType()));
		}
    	
    }
    
  

    @Override
	public LodgingPoiType setName(I18nText name) {
		return (LodgingPoiType) super.setName(name);
	}

	@Override
	public LodgingPoiType setDescription(I18nText description) {
		return (LodgingPoiType) super.setDescription(description);
	}

	@Override
	public LodgingPoiType setAddress(Address address) {
		return (LodgingPoiType) super.setAddress(address);
	}

	@Override
	public LodgingPoiType setLocation(GeoLocation location) {
		return (LodgingPoiType) super.setLocation(location);
	}

	@Override
	public LodgingPoiType setPoiType(PoiType poiType) {
		return (LodgingPoiType) super.setPoiType(poiType);
	}

	@Override
    public LodgingPoiType setFlags(Flag... flags) {
        return (LodgingPoiType) super.setFlags(flags);
    }
    
    @Override
	public LodgingPoiType setQualityCertificates( QualityCertificate... qualityCertificates) {
		return (LodgingPoiType) super.setQualityCertificates(qualityCertificates);
	}
    
    @Override
 	public LodgingPoiType setDisabledAccessibility(Set<DisabledAccessibility> disabledAccessibility) {
 		return (LodgingPoiType) super.setDisabledAccessibility(disabledAccessibility);
 	}
 	@Override
 	public LodgingPoiType setDisabledAccessibility(DisabledAccessibility... disabledAccessibility) {
 		return (LodgingPoiType) super.setDisabledAccessibility(disabledAccessibility);
 	}
 
    public Set<LodgingPrice> getLodgingPrices() {
    	return lodgingPrices;
    }
    
    public LodgingPoiType setLodgingPrices(Set<LodgingPrice> lodgingPrices) {
    	this.lodgingPrices = lodgingPrices;
    	return this;
    }
    
    public LodgingPoiType setLodgingPrices(LodgingPrice... lodgingPrices) {
    	return setLodgingPrices(Sets.newHashSet(lodgingPrices));
    }

    public LodgingPoiType setLodgingTypes(LodgingType... lodgingTypes) {
    	return setLodgingTypes(Sets.newHashSet(lodgingTypes));
    }
    
    public LodgingPoiType setLodgingTypes(Set<LodgingType> lodgingTypes) {
    	this.lodgingTypes = lodgingTypes;
    	return this;
    }

    public LodgingPoiType setScores(Score... scores){
    	return setScores(Sets.newHashSet(scores));
    }
    
    public LodgingPoiType setScores(Set<Score> scores){
    	this.scores = scores;
    	return this;
    }

    public LodgingPoiType setLodgingFlags(Set<LodgingFlag> lodgingFlags) {
		this.lodgingFlags = lodgingFlags;
		return this;
	}
    public LodgingPoiType setLodgingFlags(LodgingFlag... lodgingFlags) {
		return setLodgingFlags(Sets.newHashSet(lodgingFlags));
	}

	public LodgingPoiType setLodgingTypeFlags(Set<LodgingTypeFlag> lodgingTypeFlags) {
		this.lodgingTypeFlags = lodgingTypeFlags;
		return this;
	
	}
	public LodgingPoiType setLodgingTypeFlags(LodgingTypeFlag... lodgingTypeFlags) {
		return 	setLodgingTypeFlags(Sets.newHashSet(lodgingTypeFlags)); 

	}

	public Set<LodgingFlag> getLodgingFlags() {
		return lodgingFlags;
	}

	public Set<LodgingType> getLodgingTypes() {
		return lodgingTypes;
	}

	public Set<LodgingTypeFlag> getLodgingTypeFlags() {
		return lodgingTypeFlags;
	}

	public Set<Score> getScores() {
		return scores;
	}
	

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass())
			.add("poiType", getPoiType())
			.add("id", getId())
			.add("name", getName())
			.add("description", getDescription())
			.add("location", getLocation())
			.add("createdDate", getCreatedDate())
			.add("lastModifiedDate", getLastModifiedDate())
			.add("lodgingFlags",lodgingFlags)
			.add("lodgingTypes",lodgingTypes)
			.add("lodgingTypeFlags", lodgingTypeFlags)
			.add("scores", scores)
			.add("lodgingPrices",lodgingPrices)
			.toString();
	}
    
}
