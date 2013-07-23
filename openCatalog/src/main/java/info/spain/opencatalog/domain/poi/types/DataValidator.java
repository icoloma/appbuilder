package info.spain.opencatalog.domain.poi.types;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Sets;

@SuppressWarnings("rawtypes")
public class DataValidator {
	
	public static enum ValidatorType {
		TEXT, BIGTEXT, NUMBER, BOOLEAN, KEY_VALUE
	}

	private static final String INTEGER ="\\d+";
	private static final String DOUBLE="\\d+(\\.\\d+)?";
	
	public static final DataValidator TEXT_VALIDATOR = new DataValidator();
	public static final DataValidator KEY_VALUE_VALIDATOR = keyValueValidator();
	public static final DataValidator BIGTEXT_VALIDATOR = bigTextValidator();
	public static final DataValidator BOOLEAN_VALIDATOR = booleanValidator();
	public static final DataValidator INTEGER_VALIDATOR = integerValidator();
	public static final DataValidator DOUBLE_VALIDATOR = doubleValidator();
	
	
	
	private ValidatorType type; // Used form html forms
	private String regex;
	private Pattern pattern;
	private Set<String> validValues;
	
	public DataValidator(){
		this.type=ValidatorType.TEXT; // Default
	}
	
	public DataValidator(ValidatorType type){
		this.type=type;
	}
	
	
	public void validate(String data){
		validateWithValidValues(data);
		validateWithPattern(data);
	}
	
	private void validateWithPattern(String data){
		if (pattern != null){
			Matcher matcher = pattern.matcher(data);
			if (!matcher.matches()){
				throw new IllegalArgumentException( data + " doesn't match " + regex);
			}
		}
	}
	
	private void validateWithValidValues(String data){
		if (validValues != null && !validValues.contains(data)){
			throw new IllegalArgumentException(data + " is not a valid data :" + validValues);
		}
	}
	
	public DataValidator setValidValues(Set validValues) {
		if (validValues != null){
			this.validValues = new HashSet<String>();
			for (Object object : validValues) {
				this.validValues.add(object.toString());
			}
		}
		return this;
	}

	public Set getValidValues() {
		return validValues;
	}

	public String getRegex() {
		return regex;
	}

	public DataValidator setRegex(String regex) {
		this.regex = regex;
		this.pattern = Pattern.compile(regex);
		return this;
	}
	
	
	public ValidatorType getType() {
		return type;
	}

	private static DataValidator booleanValidator(){
		return new DataValidator(ValidatorType.BOOLEAN).setValidValues(Sets.newHashSet(Boolean.TRUE, Boolean.FALSE));
	}
	private static DataValidator integerValidator(){
		return new DataValidator(ValidatorType.NUMBER).setRegex(INTEGER);
	}
	private static DataValidator doubleValidator(){
		return  new DataValidator(ValidatorType.NUMBER).setRegex(DOUBLE);
	}
	
	private static DataValidator bigTextValidator(){
		return  new DataValidator(ValidatorType.BIGTEXT);
	}
	
	private static DataValidator keyValueValidator(){
		return  new DataValidator(ValidatorType.KEY_VALUE);
	}

}
