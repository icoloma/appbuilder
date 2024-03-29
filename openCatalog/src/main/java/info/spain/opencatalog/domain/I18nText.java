package info.spain.opencatalog.domain;

import javax.validation.constraints.NotNull;

import com.google.common.base.Strings;

/**
 * Mantains a text in differents languages
 * 
 * @author ehdez
 * 
 * TODO: Cambiar a una implementación basada en Map
 */

public class I18nText {
	
	@NotNull
	private String es;
	private String en;
	private String fr;
	private String de;
	private String it;
	
	public String getEs() {
		return es;
	}
	public I18nText setEs(String es) {
		this.es = es;
		return this;
	}
	public String getEn() {
		return en;
	}
	public I18nText setEn(String en) {
		this.en = en;
		return this;
	}
	public String getFr() {
		return fr;
	}
	public I18nText setFr(String fr) {
		this.fr = fr;
		return this;
	}
	public String getDe() {
		return de;
	}
	public I18nText setDe(String de) {
		this.de = de;
		return this;
	}
	public String getIt() {
		return it;
	}
	public I18nText setIt(String it) {
		this.it = it;
		return this;
	}
	@Override
	public String toString() {
		return "[es=" + es + ", en=" + en + ", fr=" + fr + ", de="
				+ de + ", it=" + it + "]";
	}
	
	public I18nText copyNotEmpty(I18nText other){
		if (other != null){
			if (!Strings.isNullOrEmpty(other.es)){
				this.es = other.es;
			}
			if (!Strings.isNullOrEmpty(other.en)){
				this.en = other.en;
			}
			if (!Strings.isNullOrEmpty(other.fr)){
				this.fr = other.fr;
			}
			if (!Strings.isNullOrEmpty(other.de)){
				this.de = other.de;
			}
			if (!Strings.isNullOrEmpty(other.it)){
				this.it = other.it;
			}
		}	
		return this;
		
	}
	
	
	
	
	
}
