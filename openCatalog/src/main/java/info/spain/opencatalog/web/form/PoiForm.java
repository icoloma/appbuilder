package info.spain.opencatalog.web.form;

import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.lodging.Lodging;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;

import org.springframework.web.multipart.MultipartFile;

/*
 * FIXME: 
 * Actualmente extendemos de Lodging para poderlo usar tanto como BasicPoi como Lodging
 * Si chiciera falta habría que mirar si se necesita crear una jerarquía de Forms o utilizar uno único genérico
 * 
 */
public class PoiForm extends Lodging {
	
	private boolean hasImage = false;
	private boolean deleteImage = false;
	
	private MultipartFile file; // for file uploads
	
	/** 
	 * Obtenemos el Poi del PoiForm y le
	 * añadimos los Flags correspondientes
	 * @return
	 */
	public BasicPoi getPoi(){
		if (PoiTypeID.HOTEL.equals(this.getType()) ||
			PoiTypeID.CAMPING.equals(this.getType()) ||
			PoiTypeID.APARTMENT.equals(this.getType())) {
			return getLodging();
		} else {
			return getBasicPoi();
		}
	}
	private BasicPoi getBasicPoi(){
		return new BasicPoi().copyData(this);
	}
	
	private Lodging getLodging(){
		return (Lodging) new Lodging().copyData(this);
	}
	
 
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
	
	
	public PoiForm(PoiTypeID typeId){
		super(typeId);
		super.initCollections();
	}
	
	public PoiForm(){
		super(PoiTypeID.BASIC);
		super.initCollections();
	}
	
	public void setType(PoiTypeID type){
		this.type = type;
	}
	
	

	
}
