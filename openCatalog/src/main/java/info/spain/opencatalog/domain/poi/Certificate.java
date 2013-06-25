package info.spain.opencatalog.domain.poi;



public class Certificate {
	
	private CertificateType type;
	private int year;
	
	public Certificate(CertificateType type, int year) {
		super();
		this.type = type;
		this.year = year;
	}

	public CertificateType getType() {
		return type;
	}

	public int getYear() {
		return year;
	}

	public static enum CertificateType {
		Q_CALIDAD,
		PATRIMONIO_HUMANIDAD,
		BANDERA_AZUL,
		RESERVA_BIOSFERA,
		BALNEARIOS,
		ACCESIBILIDAD,
		NATURISTA,
		CAMPSA,
		ECOTURISMO,
		EDEN;
	}

}
