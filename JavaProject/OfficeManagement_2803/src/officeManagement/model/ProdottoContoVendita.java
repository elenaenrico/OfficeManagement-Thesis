package officeManagement.model;

public class ProdottoContoVendita extends Prodotto{

	private String idMateriaPrima;
	private double quantitaMateriaPrima;
	
	public ProdottoContoVendita(String id, String nome, String descrizione, double prezzo, String idCliente, String idMateriaPrima,
			double quantitaMateriaPrima) {
		super(id, nome, descrizione, prezzo, idCliente);
		this.idMateriaPrima = idMateriaPrima;
		this.quantitaMateriaPrima = quantitaMateriaPrima;
	}

	public String getIdMateriaPrima() {
		return idMateriaPrima;
	}

	public void setIdMateriaPrima(String idMateriaPrima) {
		this.idMateriaPrima = idMateriaPrima;
	}

	public double getQuantitaMateriaPrima() {
		return quantitaMateriaPrima;
	}

	public void setQuantitaMateriaPrima(double quantitaMateriaPrima) {
		this.quantitaMateriaPrima = quantitaMateriaPrima;
	}
	
}
