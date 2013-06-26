package info.spain.opencatalog.domain.poi;


/**
 * 
 * @author ehdez
 *
 */
public class Tags {
	
	
	public enum Tag { 
		
		LODGING("lodging", 0),
		LODGING_APARTAMENT("lodging.apartment", 1),
		LODGING_CAMPING("lodging.camping", 2),
		LODGING_HOTEL("lodging.hotel",3 ),
		
		EATING("eating", 10),
		EATING_CAFE("eating.cafe", 11),
		EATING_RESTAURANT("eating.restaurant", 12),
		
		LEISURE("leisure",20),
		LEISURE_BEACH("leisure.beach",21),
		LEISURE_PUB("leisure.pub",22)
		
		;
		
		private String label;
		private Integer id;
		
		private Tag(String label, Integer id){
			this.label = label;
			this.id = id;
		}
		
		@Override
		public String toString(){
			return label;
		}
		
		public Integer getId(){
			return id;
		}
		
		public static Tag valueOf(int id){
			for (Tag tag : Tag.values()) {
				if ( id == tag.getId()) {
					return tag;
				}
			}
			throw new IllegalArgumentException("unkowed id Tag : " + id);
		}
	};
	
	

}
