package info.spain.opencatalog.domain.poi;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Point of Interest
 * 
 * @author ehdez
 */
@Document
public class Poi extends AbstractPoi {
	
	private static final long serialVersionUID = 8115817089222497633L;

	public Poi(){
		super();
	}
	
	public Poi( Poi other){
		super(other);
	}

	
}