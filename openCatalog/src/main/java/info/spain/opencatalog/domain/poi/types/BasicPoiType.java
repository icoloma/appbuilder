package info.spain.opencatalog.domain.poi.types;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.AccessibilityFlag;
import info.spain.opencatalog.domain.poi.FamilyServiceFlag;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.QualityCertificateFlag;
import info.spain.opencatalog.domain.poi.lodging.Score;

import java.util.Set;

/**
 * Clase base para definir los tipos de POI
 * Se utiliza para validar los diferentes POI
 */
public class BasicPoiType {

	private PoiTypeID id;
    
	private Set<Flag> allowedFlags;
    private Set<Score> allowedScores;
    private Set<FamilyServiceFlag> allowedFamilyServiceFlags;
    private Set<AccessibilityFlag> allowedAccessibilityFlags;
    private Set<QualityCertificateFlag> allowedQualityCertificateFlags;

    public BasicPoiType(PoiTypeID id) {
		this.id = id;
	}

	public void validate(AbstractPoi poi) {
        Preconditions.checkArgument(poi.getName() != null, "name is required");
        validateSet("flag", allowedFlags, poi.getFlags());
        validateSet("accessibility flag", allowedAccessibilityFlags, poi.getAccessibilityFlags());
        validateSet("quality certificates", allowedQualityCertificateFlags, poi.getQualityCertificateFlags());
        validateSet("family services", allowedFamilyServiceFlags, poi.getFamilyServiceFlags());
        if (poi.getScore() != null){
        	validateSet("score", allowedScores, ImmutableSet.of(poi.getScore()));
        }
    }

    protected <T> void validateSet(String name, Set<T> allowedValues, Set<T> actualValues) {
        if (allowedValues == null) {
            Preconditions.checkArgument(actualValues == null || actualValues.isEmpty(), name + " is not allowed for this type");
        } else {
            Preconditions.checkArgument(actualValues == null || allowedValues.containsAll(actualValues), "Invalid " + name + " found: " + actualValues + ". Allowed flags: " + allowedValues);
        }
    }

    public PoiTypeID getId() {
        return id;
    }

    public Set<Flag> getAllowedFlags() {
        return allowedFlags;
    }

    public BasicPoiType setAllowedFamilyServiceFlag(FamilyServiceFlag... flags) {
        this.allowedFamilyServiceFlags = Sets.immutableEnumSet(Sets.newHashSet(flags));
        return this;
    }
    
    public Set<FamilyServiceFlag> getAllowedFamilyServiceFlags() {
        return allowedFamilyServiceFlags;
    }

    public BasicPoiType setAllowedFlags(Flag... flags) {
        this.allowedFlags = Sets.immutableEnumSet(Sets.newHashSet(flags));
        return this;
    }

    public Set<AccessibilityFlag> getAllowedAccessibilityFlags() {
        return allowedAccessibilityFlags;
    }

    public BasicPoiType setAllowedAccessibilityFlags(AccessibilityFlag... flags) {
        this.allowedAccessibilityFlags = Sets.immutableEnumSet(Sets.newHashSet(flags));
        return this;
    }
  
    public Set<Score> getAllowedScores() {
        return allowedScores;
    }

    public BasicPoiType setAllowedScores(Score... scores) {
        this.allowedScores = Sets.immutableEnumSet(Sets.newHashSet(scores));
        return this;
    }

    public Set<QualityCertificateFlag> getAllowedQualityCertificateFlags() {
        return allowedQualityCertificateFlags;
    }

    public BasicPoiType setAllowedQualityCertificateFlags(QualityCertificateFlag... flags) {
        this.allowedQualityCertificateFlags = Sets.immutableEnumSet(Sets.newHashSet(flags));
        return this;
    }
}
