package info.spain.opencatalog.domain.poi.types;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.AccessibilityFlag;
import info.spain.opencatalog.domain.poi.FamilyServiceFlag;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.QualityCertificateFlag;
import info.spain.opencatalog.domain.poi.business.Business;
import info.spain.opencatalog.domain.poi.business.BusinessActiviyFlag;
import info.spain.opencatalog.domain.poi.business.BusinessServiceFlag;
import info.spain.opencatalog.domain.poi.business.BusinessTypeFlag;
import info.spain.opencatalog.domain.poi.lodging.Score;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * Tipos de negocios
 */
public class BusinessType extends BasicPoiType {

	/** Servicios que ofrece el negocio: piscina, clases, taller, ...  */
	private Set<BusinessServiceFlag> allowedServiceFlags;
	
	/** Tipo de negocio: Si es un campo de golf: privado/socios/mixto, .... */
	private Set<BusinessActiviyFlag> allowedActivityFlags;
	
	/** Actividades que ofrece: clases, taller*/
	private Set<BusinessTypeFlag> allowedTypeFlags;
   
	public BusinessType(PoiTypeID id) {
		super(id);
	}
	
	@Override
    public void validate(AbstractPoi poi) {
        super.validate(poi);
        Business business = (Business) poi;
        validateSet("business service flag", allowedServiceFlags, business.getBusinessServices());
        validateSet("business activities flag", allowedActivityFlags, business.getActivities());
        if (business.getBusinessType() != null){
        	validateSet("business type flag", allowedTypeFlags, Sets.newHashSet(business.getBusinessType()));
        }
    }

	public Set<BusinessServiceFlag> getAllowedServiceFlags() {
		return allowedServiceFlags;
	}

	public BusinessType setAllowedBusinessServiceFlags(BusinessServiceFlag... flags) {
		this.allowedServiceFlags = Sets.immutableEnumSet(Sets.newHashSet(flags));
		return this;
	}

	public Set<BusinessTypeFlag> getAllowedTypeFlags() {
		return allowedTypeFlags;
	}

	public BusinessType setAllowedBusinessTypeFlags(BusinessTypeFlag... flags) {
		this.allowedTypeFlags = Sets.immutableEnumSet(Sets.newHashSet(flags));
		return this;
	}
	
	public Set<BusinessActiviyFlag> getAllowedActivityFlags() {
		return allowedActivityFlags;
	}

	public BusinessType setAllowedActivityFlags( BusinessActiviyFlag... flags) {
		this.allowedActivityFlags = Sets.immutableEnumSet(Sets.newHashSet(flags));
		return this;
	}

	@Override
	public BusinessType setAllowedFamilyServiceFlag(FamilyServiceFlag... flags) {
		return (BusinessType) super.setAllowedFamilyServiceFlag(flags);
	}

	@Override
	public BusinessType setAllowedFlags(Flag... flags) {
		return (BusinessType) super.setAllowedFlags(flags);
	}

	@Override
	public BusinessType setAllowedAccessibilityFlags( AccessibilityFlag... flags) {
		return (BusinessType) super.setAllowedAccessibilityFlags(flags);
	}

	@Override
	public BusinessType setAllowedScores(Score... scores) {
		return (BusinessType) super.setAllowedScores(scores);
	}

	@Override
	public BusinessType setAllowedQualityCertificateFlags( QualityCertificateFlag... flags) {
		return (BusinessType) super.setAllowedQualityCertificateFlags(flags);
	}
	
	
	
	
    
    
}
