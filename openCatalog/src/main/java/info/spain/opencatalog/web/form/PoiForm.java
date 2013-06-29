package info.spain.opencatalog.web.form;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.Flag;

import java.util.ArrayList;
import java.util.HashMap;
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
	public AbstractPoi getPoi(){
		for (String key: flag.keySet()) {
			int indx = key.indexOf("-a");
			if (indx > 0){
				String  id = key.substring(0, indx);
				Flag flag = Flag.valueOf(id);
 				if (! this.getFlags().contains(flag))
					this.setFlags(flag);
			}
			
		}
		return this;
	}
	
	
	public PoiForm(){
		super();
		initEmptySets();  
	}
	
	
	
	/**
	 * Creamos PoiForm con la información del Poi y un genermamos un List< {id,key} > para los flags
	 * 
	 */
		
	public PoiForm(AbstractPoi poi){
		super();
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
//		if (getFlags() == null){
//			setFlags(new HashSet<Flag>());
//		}
//		if (getAccessibilityFlags() == null){
//			setAccessibilityFlags(new HashSet<AccessibilityFlag>());
//		}
//		if (getQualityCertificateFlags() == null){
//			setQualityCertificateFlags(new HashSet<QualityCertificateFlag>());
//		}
//		if (getTimetable() == null) {
//			setTimetable(new HashSet<TimeTableEntry>());
//		}
		
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
