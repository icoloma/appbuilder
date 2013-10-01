package info.spain.opencatalog.api;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.ContactInfo;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.Price;
import info.spain.opencatalog.domain.poi.Score;
import info.spain.opencatalog.domain.poi.SyncInfo;
import info.spain.opencatalog.domain.poi.TimeTableEntry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.ResourceSupport;

@XmlRootElement
public class PoiResource extends ResourceSupport {

	protected String type;
	protected I18nText name;
	protected I18nText description;
	protected Address address;
	protected GeoLocation location;
	protected Set<Flag> flags;
	protected Score score;
	protected I18nText enviroment;
	protected Map<String, String> data;
	protected Set<TimeTableEntry> timetable;
	protected List<Price> prices;
	protected ContactInfo contactInfo;
	protected Set<String> languages;
	protected String defaultImageFilename;
	protected SyncInfo syncInfo = new SyncInfo();
	private String created;
	private String lastModified;

	public void copyFrom(BasicPoi poi) {
		BeanUtils.copyProperties(poi, this, new String[] { "type", "created",
				"lastModified" });
		this.prices = poi.getPrices();
		this.type = poi.getType().toString();
		this.created = poi.getCreated().toString();
		this.lastModified = poi.getLastModified().toString();
	}

	public I18nText getName() {
		return name;
	}

	public void setName(I18nText name) {
		this.name = name;
	}

	public I18nText getDescription() {
		return description;
	}

	public void setDescription(I18nText description) {
		this.description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public GeoLocation getLocation() {
		return location;
	}

	public void setLocation(GeoLocation location) {
		this.location = location;
	}

	public Set<Flag> getFlags() {
		return flags;
	}

	public void setFlags(Set<Flag> flags) {
		this.flags = flags;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public I18nText getEnviroment() {
		return enviroment;
	}

	public void setEnviroment(I18nText enviroment) {
		this.enviroment = enviroment;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public Set<TimeTableEntry> getTimetable() {
		return timetable;
	}

	public void setTimetable(Set<TimeTableEntry> timetable) {
		this.timetable = timetable;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public Set<String> getLanguages() {
		return languages;
	}

	public void setLanguages(Set<String> languages) {
		this.languages = languages;
	}

	public String getDefaultImageFilename() {
		return defaultImageFilename;
	}

	public void setDefaultImageFilename(String defaultImageFilename) {
		this.defaultImageFilename = defaultImageFilename;
	}

	public SyncInfo getSyncInfo() {
		return syncInfo;
	}

	public void setSyncInfo(SyncInfo syncInfo) {
		this.syncInfo = syncInfo;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	

}
