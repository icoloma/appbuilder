package info.spain.opencatalog.domain.poi.types;

import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.FlagGroup;
import info.spain.opencatalog.domain.poi.Score;

import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Clase base para definir los tipos de POI Se utiliza para validar los
 * diferentes POI
 */
public class BasicPoiType {

	private PoiTypeID id;

	private Set<Flag> allowedFlags;
	private Set<Score> allowedScores;
	
	/**
	 * Datos específicos del poi: longitud(playa), nº pistas verdes(estación
	 * esquí) ... Si se desean categorías de datos, hay que especificarlo en el
	 * key separando por ":" Ej.: Para estación de esquí, categoría alpino, dato
	 * número de pistas verdes "alpino:numero-pistas-verdes"
	 * */
	private Map<String, DataValidator> allowedDataValidators;

	transient Set<FlagGroup> flagGroups;
	
	public BasicPoiType(PoiTypeID id) {
		this.id = id;
	}
	public BasicPoiType(String id) {
		this.id = PoiTypeID.valueOf(id);
	}

	public Set<FlagGroup> getFlagGroups() {
		if (flagGroups == null) {
			Set<FlagGroup> s = Sets.newTreeSet();
			for (Flag flag : allowedFlags) {
				s.add(flag.getGroup());
			}
			flagGroups = s;
		}
		return flagGroups;
	}

	public void validate(BasicPoi poi) {
		Preconditions.checkArgument(poi.getName() != null, "name is required");
		validateSet("flag", allowedFlags, poi.getFlags());
		if (poi.getScore() != null) {
			validateSet("score", allowedScores, ImmutableSet.of(poi.getScore()));
		}
		validateData(poi.getData());
	}

	/**
	 * Comprueba que los datos tienen key y valores válidos
	 */
	private void validateData(Map<String, String> data) {
		if (allowedDataValidators == null || data == null) {
			return;
		}
		Set<String> validKeys = allowedDataValidators.keySet();
		for (String key : data.keySet()) {
			Preconditions.checkArgument(validKeys.contains(key), key
					+ " is not a valid data for this type. Allowed data :"
					+ validKeys);
			DataValidator validator = allowedDataValidators.get(key);
			if (validator != null) {
				validator.validate(data.get(key));
			}
		}
	}

	protected <T> void validateSet(String name, Set<T> allowedValues,
			Set<T> actualValues) {
		if (allowedValues == null) {
			Preconditions.checkArgument(
					actualValues == null || actualValues.isEmpty(), name
							+ " is not allowed for this type");
		} else {
			Preconditions.checkArgument(
					actualValues == null 
						|| actualValues.size() == 0
						|| allowedValues.containsAll(actualValues),
					"Invalid " + name + " found: " + actualValues + 
					".\n Allowed flags: " + allowedValues);
		}
	}

	public PoiTypeID getId() {
		return id;
	}

	public Set<Flag> getAllowedFlags() {
		return allowedFlags;
	}

	public BasicPoiType setAllowedFlags(Flag... flags) {
		this.allowedFlags = Sets.immutableEnumSet(Sets.newHashSet(flags));
		return this;
	}

	public Set<Score> getAllowedScores() {
		return allowedScores;
	}

	public BasicPoiType setAllowedScores(Score... scores) {
		this.allowedScores = Sets.immutableEnumSet(Sets.newHashSet(scores));
		return this;
	}

	public Map<String, DataValidator> getAllowedDataValidators() {
		return allowedDataValidators;
	}

	public BasicPoiType setAllowedDataValidator(String key,
			DataValidator dataValidator) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(key),
				"key can't be empty");
		Preconditions.checkArgument(dataValidator != null,
				"Validator can't be empty");
		if (allowedDataValidators == null) {
			allowedDataValidators = Maps.newLinkedHashMap();
		}
		this.allowedDataValidators.put(key, dataValidator);
		return this;
	}

}
