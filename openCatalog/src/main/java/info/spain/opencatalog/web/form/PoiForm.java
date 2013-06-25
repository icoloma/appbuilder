package info.spain.opencatalog.web.form;

import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.domain.poi.Poi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class PoiForm extends Poi {

	private static final long serialVersionUID = 3188263525111774200L;
	
	private boolean hasImage = false;
	private boolean deleteImage = false;
	private MultipartFile file; // for file uploads
	private Map<String,String> tag = new HashMap<String,String>(); // Used for tag
	private List<TagInfo> tagsInfo = new ArrayList<TagInfo>(); // Used for rendering
	
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

	public Map<String, String> getTag() {
		return tag;
	}
	
	public List<TagInfo> getTagsInfo() {
		return tagsInfo;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	/** 
	 * Obtenemos el Poi del PoiForm y le
	 * añadimos los Tags correspondientes
	 * @return
	 */
	public Poi getPoi(){
		Poi result = new Poi(this);
		for (String key: tag.keySet()) {
			int indx = key.indexOf("-a");
			if (indx > 0){
				Integer id = Integer.valueOf(key.substring(0, indx));
				Tag tag = Tag.valueOf(id);
				if (! result.getTags().contains(tag))
					result.getTags().add(tag);
			}
			
		}
		return result;
	}
	
	
	public PoiForm(){
		super();
	}
	
	/**
	 * Creamos PoiForm con la información del Poi y un genermamos un List< {id,key} > para los tags
	 * 
	 */
	public PoiForm(Poi poi){
		super(poi);
		
		for( Tag theTag : poi.getTags()){
			tagsInfo.add( new TagInfo( theTag.getId()+"-a", theTag.toString()));
		}
	}
	
	
	
	public class TagInfo{
		String id;
		String key;
		
		public TagInfo(String id, String key){
			this.id = id;
			this.key = key;
		}

		public String getId() {
			return id;
		}

		public String getKey() {
			return key;
		}
		
	}
}
