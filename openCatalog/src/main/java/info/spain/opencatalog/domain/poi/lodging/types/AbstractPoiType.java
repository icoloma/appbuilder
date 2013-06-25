package info.spain.opencatalog.domain.poi.lodging.types;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 *
 */
public class AbstractPoiType {

    /** características que un PoiType puede tener o no */
    private Set<Flag> flags;

    /** certificados que pueden asignarse a este poi */
    private Set<QualityCertificate> qualityCertificates;

    public AbstractPoiType setFlags(String... flags) {
        return this.setFlags(Sets.newHashSet(flags));
    }

    public AbstractPoiType setFlags(Set <String> flags) {
        this.flags = flags;
        return this;
    }
}
