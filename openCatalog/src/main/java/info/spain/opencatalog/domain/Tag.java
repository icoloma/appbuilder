package info.spain.opencatalog.domain;

/**
 * 
 * @author ehdez
 *
 */
public class Tag {
	
	public enum Tags { 
		
		ALOJAMIENTO("alojamiento"),
		ALOJAMIENTO_APARTAMENTO("alojamiento/apartamento"),
		ALOJAMIENTO_CAMPING("alojamiento/camping"),
		ALOJAMIENTO_HOTEL("alojamiento/hotel"),
		
		COMER("comer"),
		COMER_CAFETERIA("comer/restaurante"),
		COMER_RESTAURANTE("comer/restaurante"),
		
		OCIO("ocio"),
		OCIO_PLAYA("ocio/playa"),
		OCIO_PUB("ocio/pub")
		
		;
		
		private String label;
		
		private Tags(String label){
			this.label = label;
		}
		
		@Override
		public String toString(){
			return label;
		}
	};

}
