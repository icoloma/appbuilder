package info.spain.opencatalog.domain;


/**
 * 
 * @author ehdez
 *
 */
public class Tags {
	
	
	public enum Tag { 
		
		LODGING("lodging"),
		LODGING_APARTAMENT("lodging.apartment"),
		LODGING_CAMPING("lodging.camping"),
		LODGING_HOTEL("lodging.hotel"),
		
		EATING("eating"),
		EATING_CAFE("eating.cafe"),
		EATING_RESTAURANT("eating.restaurant"),
		
		LEISURE("leisure"),
		LEISURE_BEACH("leisure.beach"),
		LEISURE_PUB("leisure.pub")
		
		;
		
		private String label;
		
		private Tag(String label){
			this.label = label;
		}
		
		@Override
		public String toString(){
			return label;
		}
	};
	
	

}
