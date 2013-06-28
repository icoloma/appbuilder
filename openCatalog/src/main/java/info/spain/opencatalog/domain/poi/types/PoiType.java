package info.spain.opencatalog.domain.poi.types;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import info.spain.opencatalog.domain.poi.AccessibilityFlag;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.QualityCertificateFlag;
import info.spain.opencatalog.domain.poi.types.lodging.Score;

import java.util.Set;

/**
 *
 */
public class PoiType {

    private PoiTypeID id;
    private Set<Flag> allowedFlags;
    private Set<AccessibilityFlag> allowedAccessibilityFlags;
    private Set<Score> allowedScores;

    /** certificados que pueden asignarse a este poi */
    private Set<QualityCertificateFlag> allowedQualityCertificateFlags;

    public void validate(BasicPoi poi) {
        Preconditions.checkArgument(poi.getName() != null, "name is required");
        validateSet("flag", allowedFlags, poi.getFlags());
        validateSet("accessibility flag", allowedAccessibilityFlags, poi.getAccessibilityFlags());
        validateSet("quality certificates", allowedQualityCertificateFlags, poi.getQualityCertificateFlags());
        validateSet("score", allowedScores, ImmutableSet.of(poi.getScore()));
    }

    protected <T> void validateSet(String name, Set<T> allowedValues, Set<T> actualValues) {
        if (allowedValues == null) {
            Preconditions.checkArgument(actualValues == null || actualValues.isEmpty(), name + " is not allowed for this type");
        } else {
            Preconditions.checkArgument(allowedValues.containsAll(actualValues), "Invalid " + name + " found: " + actualValues + ". Allowed flags: " + allowedValues);
        }
    }

    public PoiTypeID getId() {
        return id;
    }

    public void setId(PoiTypeID id) {
        this.id = id;
    }

    public Set<Flag> getAllowedFlags() {
        return allowedFlags;
    }

    public void setAllowedFlags(Set<Flag> allowedFlags) {
        this.allowedFlags = allowedFlags;
    }

    public Set<AccessibilityFlag> getAllowedAccessibilityFlags() {
        return allowedAccessibilityFlags;
    }

    public void setAllowedAccessibilityFlags(Set<AccessibilityFlag> allowedAccessibilityFlags) {
        this.allowedAccessibilityFlags = allowedAccessibilityFlags;
    }

    public Set<Score> getAllowedScores() {
        return allowedScores;
    }

    public void setAllowedScores(Set<Score> allowedScores) {
        this.allowedScores = allowedScores;
    }

    public Set<QualityCertificateFlag> getAllowedQualityCertificateFlags() {
        return allowedQualityCertificateFlags;
    }

    public void setAllowedQualityCertificateFlags(Set<QualityCertificateFlag> allowedQualityCertificateFlags) {
        this.allowedQualityCertificateFlags = allowedQualityCertificateFlags;
    }
}
