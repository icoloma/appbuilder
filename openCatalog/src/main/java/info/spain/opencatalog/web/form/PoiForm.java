package info.spain.opencatalog.web.form;

import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.Tags.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoiForm extends Poi {


	
	private static final long serialVersionUID = 3188263525111774200L;
	
	/**
	 * Used form submits 
	 */
	private Map<String,String> tag = new HashMap<String,String>();
	public Map<String, String> getTag() {
		return tag;
	}
	
	/**
	 * Used for rendering
	 */
	private List<TagInfo> tagsInfo = new ArrayList<TagInfo>();
	public List<TagInfo> getTagsInfo() {
		return tagsInfo;
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
