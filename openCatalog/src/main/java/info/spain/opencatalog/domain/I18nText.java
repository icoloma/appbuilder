package info.spain.opencatalog.domain;

import javax.validation.constraints.NotNull;

/**
 * Mantains a text in differents languages
 * 
 * @author ehdez
 * 
 * FIXME: change for a HashMap implementation when fixed #8
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
	
	
	
	
}
