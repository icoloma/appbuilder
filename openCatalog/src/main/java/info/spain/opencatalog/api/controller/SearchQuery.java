package info.spain.opencatalog.api.controller;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;


/**
 * Parámetros para la búsqueda de pois a través del API
 * @author ehdez
 */
public class SearchQuery {
	
	private static Logger log = LoggerFactory.getLogger(SearchQuery.class.getName());
	
	private List<String> poiTypeIdList;
	private List<String> flagList;
	private String idZone;
	private String updatedAfter; // ISO8601
	
	public  List<String> getPoiTypeIdList() {
		return poiTypeIdList;
	}
	public SearchQuery setPoiTypeIdList( List<String> poiTypeIdList) {
		this.poiTypeIdList = poiTypeIdList;
		return this;
	}
	public List<String> getFlagList() {
		return flagList;
	}
	public SearchQuery setFlagList(List<String> flagList) {
		this.flagList = flagList;
		return this;
	}
	public String getUpdatedAfter() {
		return updatedAfter;
	}
	public SearchQuery setUpdatedAfter(String updatedAfter) {
		if ( updatedAfter==null || updatedAfter.length() == 0 || "null".equals(updatedAfter)){
			this.updatedAfter = null;
		} else {
			if (checkValidDate(updatedAfter)){
				this.updatedAfter = updatedAfter;
			} else {
				log.warn("Invalid date : " + updatedAfter + ", setting to null");
				this.updatedAfter = null;
			}
			
		}
		return this;
	}
	
	

	
	public String getIdZone() {
		return idZone;
	}
	public SearchQuery setIdZone(String idZone) {
		this.idZone = idZone;
		return this;
	}
	
	
	private boolean checkValidDate(String date){
		try {
			new DateTime(date);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(getClass())
			.add("idZone", idZone)
			.add("poiTypeIdList", poiTypeIdList)
			.add("flagList", flagList)
			.add("updatedAfter", updatedAfter)
			.toString();
	}
	
	
	
	
	
}
