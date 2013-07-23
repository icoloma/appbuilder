package info.spain.opencatalog.web.form;

import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.types.BasicPoiType;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;
import info.spain.opencatalog.domain.poi.types.PoiTypeRepository;

import org.springframework.web.multipart.MultipartFile;

public class PoiForm extends BasicPoi {
	
	private boolean hasImage = false;
	private boolean deleteImage = false;
	
	private MultipartFile file; // for file uploads
	
	
 
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

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public void setType(BasicPoiType type){
		this.type = type;
	}

	/** 
	 * Obtenemos el Poi del PoiForm y le
	 * añadimos los Flags correspondientes
	 * @return
	 */
	public BasicPoi getPoi(){
	
		return this;
	}
	
	
	public PoiForm(PoiTypeID typeId){
		super(PoiTypeRepository.getType(typeId));
		super.initCollections();
	}
	
	public PoiForm(){
		super(PoiTypeRepository.getType(PoiTypeID.BASIC));
		super.initCollections();
	}
	
	

	
}
