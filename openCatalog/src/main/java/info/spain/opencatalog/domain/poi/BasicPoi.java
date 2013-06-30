package info.spain.opencatalog.domain.poi;

import info.spain.opencatalog.domain.poi.types.BasicPoiType;

/** TODO: Eliminar esta clase 
 * De momento se mantiene hasta que se terminie 
 * la migración de los tipos de POI
 * */
public class BasicPoi extends AbstractPoi {


	public BasicPoi(BasicPoiType type){
		super(type);
    }

}
