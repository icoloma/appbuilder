package info.spain.opencatalog.domain.poi;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.google.common.base.Objects;


public class SyncInfo {
	/** El Poi ha sido importado */
	private boolean imported;

	/** El Poi debe sincronizarse */
	private boolean sync;
	
	/** Si el poi fue importado, cual es su id original */
	private String originalId;
	
	/** Last update */
	@DateTimeFormat(pattern="dd/MM/yyy HH:mm:ss")
	private DateTime lastUpdate; 

	
	public boolean isImported() {
		return imported;
	}

	public SyncInfo setImported(boolean imported) {
		this.imported = imported;
		return this;
	}

	public boolean isSync() {
		return sync;
	}

	public SyncInfo setSync(boolean sync) {
		this.sync = sync;
		return this;
	}

	public String getOriginalId() {
		return originalId;
	}

	public SyncInfo setOriginalId(String originalId) {
		this.originalId = originalId;
		return this;
	}

	public DateTime getLastUpdate() {
		return lastUpdate;
	}

	public SyncInfo setLastUpdate(DateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
		return this;
	}
	

	@Override
	public String toString() {
		return toStringHelper().toString();
	}
	
	protected com.google.common.base.Objects.ToStringHelper toStringHelper(){
		return Objects.toStringHelper(getClass())
				.add("imported", imported)
				.add("sync",  sync)
				.add("originalId", originalId)
				.add("lastUpdate", lastUpdate)
				;
	}
	
}