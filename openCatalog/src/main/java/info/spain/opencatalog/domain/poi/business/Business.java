package info.spain.opencatalog.domain.poi.business;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.AccessPrice;
import info.spain.opencatalog.domain.poi.AccessibilityFlag;
import info.spain.opencatalog.domain.poi.ContactInfo;
import info.spain.opencatalog.domain.poi.FamilyServiceFlag;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.LanguageFlag;
import info.spain.opencatalog.domain.poi.QualityCertificateFlag;
import info.spain.opencatalog.domain.poi.TimeTableEntry;
import info.spain.opencatalog.domain.poi.types.BasicPoiType;

import java.util.Map;
import java.util.Set;

import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.Sets;

/**
 * Empresas de ecoturismo
 */
public class Business extends AbstractPoi {

	/** Idiomas soportados */
	private Set<LanguageFlag> languages;
	
	/** Actividades de ecoturismo*/
	private Set<BusinessActiviyFlag> activities;
	
	/** servicios del negocio:  */
    private Set<BusinessServiceFlag> businessServices;
    
    /** tipo de negocio */
    private BusinessTypeFlag businessType;
    
    /** 
     * 	Datos específicos del negocio 
     *  Golf: Año de inauguración: , Diseñador, Número de hoyos, Par del campo, ...
     *  Estación de equí: nº pistas, nº kilómetros esquiables, ... 
     */
    private Map<String,String> data;
    
    public Business(BasicPoiType type) {
		super(type);
	}

	@Override
	public Business setPrices(AccessPrice... prices) {
		return (Business) super.setPrices(prices);
	}
  
    @Override
	public Business setName(I18nText name) {
		return (Business) super.setName(name);
	}

	@Override
	public Business setDescription(I18nText description) {
		return (Business) super.setDescription(description);
	}

	@Override
	public Business setAddress(Address address) {
		return (Business) super.setAddress(address);
	}

	@Override
	public Business setLocation(GeoLocation location) {
		return (Business) super.setLocation(location);
	}

	@Override
    public Business setFlags(Flag... flags) {
        return (Business) super.setFlags(flags);
    }
    
    @Override
	public Business setQualityCertificates( QualityCertificateFlag... flags) {
		return (Business) super.setQualityCertificates(flags);
	}

	@Override
	public Business setAccessibilityFlags(AccessibilityFlag... flags) {
		return (Business) super.setAccessibilityFlags(flags);
	}
	
	@Override
	public Business setTimetable(TimeTableEntry... timetable) {
		return (Business) super.setTimetable(timetable);
	}
	
	@Override
 	public Business setContactInfo(ContactInfo contactInfo) {
		return (Business) super.setContactInfo(contactInfo);
	}

 	@Override
	public Business setFamilyServiceFlags( FamilyServiceFlag... flags) {
		return (Business) super.setFamilyServiceFlags(flags);
	}

	public Set<BusinessActiviyFlag> getActivities() {
		return activities;
	}

	public Business setActivities(BusinessActiviyFlag... activities) {
		this.activities = Sets.newHashSet(activities);
		return this;
	}
	
	public Set<LanguageFlag> getLanguages() {
		return languages;
	}

	public Business setLanguages(LanguageFlag... languages) {
		this.languages = Sets.newHashSet(languages);
		return this;
	}
	
	public Set<BusinessServiceFlag> getBusinessServices() {
		return businessServices;
	}

	public Business setBusinessServices(BusinessServiceFlag... flags ) {
		this.businessServices = Sets.newHashSet(flags);
		return this;
	}
	
	public BusinessTypeFlag getBusinessType() {
		return businessType;
	}

	public Business setBusinessType(BusinessTypeFlag businessType) {
		this.businessType = businessType;
		return this;
	}

	@Override
	protected ToStringHelper toStringHelper() {
		return super.toStringHelper()
			.add("activities", activities)
			.add("languages",languages)
			.add("businessServices", businessServices)
			.add("businessType",businessType);
	}


}
