package info.spain.opencatalog.web.form;

import info.spain.opencatalog.domain.poi.DisabledAccessibility;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.QualityCertificate;
import info.spain.opencatalog.domain.poi.types.BasicPoi;
import info.spain.opencatalog.domain.poi.types.TimeTableEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class PoiForm extends BasicPoi {

	private boolean hasImage = false;
	private boolean deleteImage = false;
	private MultipartFile file; // for file uploads
	private Map<String,String> flag = new HashMap<String,String>(); // Used for tag
	private List<FlagInfo> flagsInfo = new ArrayList<FlagInfo>(); // Used for rendering
	
	
	
	public boolean isDeleteImage() {
		return deleteImage;
	}

	public void setDeleteImage(boolean deleteImage) {
		this.deleteImage = deleteImage;
	}

	public boolean isHasImage() {
		return hasImage;
	}

	public void setHasImage(boolean hasImage) {
		this.hasImage = hasImage;
	}

	public Map<String, String> getFlag() {
		return flag;
	}
	
	public List<FlagInfo> getFlagsInfo() {
		return flagsInfo;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	/** 
	 * Obtenemos el Poi del PoiForm y le
	 * añadimos los Flags correspondientes
	 * @return
	 */
	public BasicPoi getPoi(){
		BasicPoi result = new BasicPoi(this);
		for (String key: flag.keySet()) {
			int indx = key.indexOf("-a");
			if (indx > 0){
				String  id = key.substring(0, indx);
				Flag flag = Flag.valueOf(id);
 				if (! result.getFlags().contains(flag))
					result.getFlags().add(flag);
			}
			
		}
		return result;
	}
	
	
	public PoiForm(){
		super();
		initEmptySets();  
	}
	
	
	
	/**
	 * Creamos PoiForm con la información del Poi y un genermamos un List< {id,key} > para los flags
	 * 
	 */
	public PoiForm(BasicPoi poi){
		super(poi);
		initEmptySets();
		
		for( Flag theFlag : poi.getFlags()){
			flagsInfo.add( new FlagInfo( theFlag+"-a", theFlag));
		}
	}
	
	/**
	 * Inicializa los sets que estén a null para evitar NullPointerExceptions al aplicar 
	 * valores desde la Request
	 */
	private void initEmptySets(){
		if (getFlags() == null){
			setFlags(new HashSet<Flag>());
		}
		if (getDisabledAccessibility() == null){
			setDisabledAccessibility(new HashSet<DisabledAccessibility>());
		}
		if (getQualityCertificates() == null){
			setQualityCertificates(new HashSet<QualityCertificate>());
		}
		if (getTimetable() == null) {
			setTimetable(new HashSet<TimeTableEntry>());
		}
		
	}
	
	
	
	public class FlagInfo{
		String id;
		Flag flag;
		
		public FlagInfo(String id, Flag flag){
			this.id = id;
			this.flag = flag;
		}

		public String getId() {
			return id;
		}

		public Flag getFlag() {
			return flag;
		}
		
	}
}
