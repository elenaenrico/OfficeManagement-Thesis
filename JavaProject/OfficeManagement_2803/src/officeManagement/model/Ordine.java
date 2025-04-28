package officeManagement.model;

import java.sql.Date;

public class Ordine {

	private String id;
	private String id_cliente;
	private String id_prodotto;
	private int quantita;
	private Date data_ordine;
	private Date data_scadenza;
	private Date data_completamento;
	
	public Ordine(String id, String id_cliente, String id_prodotto, int quantita, java.sql.Date data_ordine2, java.sql.Date data_scadenza2,
			java.sql.Date data_completamento2) {
		this.id = id;
		this.id_cliente = id_cliente;
		this.id_prodotto = id_prodotto;
		this.quantita = quantita;
		this.data_ordine = data_ordine2;
		this.data_scadenza = data_scadenza2;
		this.data_completamento = data_completamento2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getId_prodotto() {
		return id_prodotto;
	}

	public void setId_prodotto(String id_prodotto) {
		this.id_prodotto = id_prodotto;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public Date getData_ordine() {
		return data_ordine;
	}

	public void setData_ordine(Date data_ordine) {
		this.data_ordine = data_ordine;
	}

	public Date getData_scadenza() {
		return data_scadenza;
	}

	public void setData_scadenza(Date data_scadenza) {
		this.data_scadenza = data_scadenza;
	}

	public Date getData_completamento() {
		return data_completamento;
	}

	public void setData_completamento(Date data_completamento) {
		this.data_completamento = data_completamento;
	}
	
	public String toString() {
		return "<tr><td>" + this.data_ordine + "</td><td>" + this.data_scadenza + "</td><td>" + this.id + "</td><td>" + this.id_cliente + "</td><td>" + this.id_prodotto + "</td><td>" + this.quantita + "</td>"; 
	}
}
