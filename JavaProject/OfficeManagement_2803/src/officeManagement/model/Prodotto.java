package officeManagement.model;

public class Prodotto {
	
	private String id;
	private String nome;
	private String idCliente;
	private String descrizione;
	private double prezzo;
	private int vendite;
	
	public Prodotto(String id, String nome, String descrizione, double prezzo, String idCliente) {
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.idCliente = idCliente;
		vendite = 0;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public int getVendite() {
		return vendite;
	}

	public void setVendite(int vendite) {
		this.vendite += vendite;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	
	public String toString() {
		if (descrizione == null)
			return "<br><tr><td>" + id + "</td><td>" + nome + "</td><td>" + idCliente + "</td><td>    </td><td>" + prezzo + " €</td></tr>";
		return "<br><tr><td>" + id + "</td><td>" + nome + "</td><td>" + idCliente + "</td><td>" + descrizione + "</td><td>" + prezzo + " €</td></tr>";
	}
	
	public String toStringNoTable() {
		return id + "-" + nome + "-" + prezzo + "€";
	}
}
