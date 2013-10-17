package info.spain.opencatalog.web.form;

import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.Price;
import info.spain.opencatalog.domain.poi.lodging.Lodging;
import info.spain.opencatalog.domain.poi.lodging.RoomPrice;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;

import java.util.List;

import com.google.common.collect.Lists;

/*
 * FIXME: Actualmente extendemos de Lodging para poderlo usar tanto como BasicPoi como Lodging
 * Si chiciera falta habría que mirar si se necesita crear una jerarquía de Forms o utilizar uno único genérico
 * 
 */
public class PoiForm extends Lodging {
	
	/**
	 * Como al hacer el POST no se puede saber a priori si se debe crear una instancia de Lista de RoomPrice o Price 
	 * y dado que Spring siempre la crea de Price, añadimos este campo en el Form para los casos de Lodging al cual haremos 
	 * referencia des de el formulario (HTML)
	 * @see adjustPricesOnSave()
	 * @see copyData()
	 */
	List<RoomPrice> roomPrices = Lists.newArrayList();
	
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
	
	public List<RoomPrice> getRoomPrices() {
		return roomPrices;
	}
	public void setRoomPrices(List<RoomPrice> roomPrices) {
		this.roomPrices = roomPrices;
	}
	
	/** 
	 * Si nos han pasado roomPrices, entonces los guardamos como prices
	 */
	public PoiForm adjustPricesOnSave(){
		if (roomPrices.size() >0){
			prices.clear();
			for (RoomPrice roomPrice : roomPrices) {
				prices.add(roomPrice);
			}
		}
		return this;
	}
	@Override
	public BasicPoi copyData(BasicPoi source) {

		super.copyData(source);
		
		// Ajustamos los precios si copiamos desde un Lodging
		
		if (source instanceof Lodging){
			roomPrices.clear();
			for (Price price : source.getPrices()) {
				roomPrices.add((RoomPrice) price);
			}
		}
		return this;
	}
	
	
}
