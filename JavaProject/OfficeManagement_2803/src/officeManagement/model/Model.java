package officeManagement.model;

import java.sql.SQLException;
import java.sql.Date;
import java.util.*;
import officeManagement.DAO.DAO;

public class Model {
		
	private List<Ordine> ordini;
	private List<Prodotto> prodotti;
	private List<Cliente> clienti;
	private List<MateriaPrima> materiePrime;
	private List<Fornitore> fornitori;
	
	public Model() {
		ordini = new ArrayList<Ordine>();
		prodotti = new ArrayList<Prodotto>();
	}
	
	//METODI PER RICAVARE LISTE DI DATI DAL DATABASE:
	
	public List<Ordine> getAllOrdini() throws SQLException {
		this.ordini = (List<Ordine>) DAO.allOrdini();
		return ordini;
	}
	
	public List<Ordine> getOrdiniDaCompletare() throws SQLException {
		this.ordini = (List<Ordine>) DAO.allOrdini();
		List<Ordine> ordiniCompletare = new ArrayList<Ordine>();
		for (Ordine o: ordini) {
			if (o.getData_completamento() == null) {
				ordiniCompletare.add(o);
			}
		}
		return ordiniCompletare;
	}
	
	public List<Cliente> getAllClienti() throws SQLException {
		this.clienti = DAO.allClienti();
		return clienti;
	}
	
	public List<Prodotto> getAllProdotti() throws SQLException {
		this.prodotti = DAO.allProdotti();
		return prodotti;
	}
	
	public List<Fornitore> getAllFornitori() throws SQLException{
		this.fornitori = DAO.allFornitori();
		return fornitori;
	}
	
	public List<MateriaPrima> getAllMateriePrime() throws SQLException{
		this.materiePrime = DAO.allMateriePrime();
		return materiePrime;
	}
	
	//METODI PER AGGIUNGERE DATI AL DATABASE:
	
	public boolean aggiungiOrdine(String id, String idCliente, String idProdotto, int quantita, Date dataOrdine, Date dataScadenza) throws SQLException {
		Ordine o = new Ordine(id, idCliente, idProdotto, quantita, dataOrdine, dataScadenza, null);
		boolean c = DAO.inserisciOrdine(o);
		return c;
	}
	
	public boolean aggiungiProdotto(String id, String nome, String descrizione, double prezzo, String tipoProd, String idMateriaPrima, double d, String idCliente) throws SQLException {
		if (tipoProd.equals("Prodotto per conto Lavoro")) {
			ProdottoContoLavoro p = new ProdottoContoLavoro(id, nome, descrizione, prezzo, idCliente);
			return (DAO.inserisciProdotto(p));
		}
		else {
			ProdottoContoVendita p = new ProdottoContoVendita(id, nome, descrizione, prezzo, idCliente, idMateriaPrima, d);
			return (DAO.inserisciProdotto(p));
		}
	}

	public boolean aggiungiCliente(String idC, String nomeC, String emailC, String telC, String indirizzoC) {
		return DAO.inserisciCliente(idC, nomeC, emailC, telC, indirizzoC);
	}
	
	public boolean aggiungiMateriaPrima(String id, String idF, String nome, double prezzo) {
		return DAO.inserisciMateriaPrima(id, idF, nome, prezzo);
	}
	
	public boolean aggiungiFornitore(String idF, String nomeF, String emailF, String telF) {
		return DAO.inserisciFornitore(idF, nomeF, emailF, telF);
	}
	
	// METODI PER AGGIORNARE DATI DEL DATABASE:
	
	public boolean aggiornaOrdine(String idOrdine, String idCliente, String idProdotto, Date dataOSQL, Date dataSSQL, Date dataC,
			int quantita) {
		return (DAO.aggiornaOrdine(idOrdine, idCliente, idProdotto, dataOSQL, dataSSQL, dataC, quantita));
	}
	
	public boolean modificaProdottoCL(String idProdotto, String nomeProdotto, String descrizioneProdotto, double prezzoProdotto, String idCliente) {
		return DAO.modificaProdottoCL(idProdotto, nomeProdotto, descrizioneProdotto, prezzoProdotto, idCliente);
	}

	public boolean modificaProdottoCV(String idProdotto, String nomeProdotto, String descrizioneProdotto,
			double prezzoProdotto, String materiaPrima, double quantitaMP, String idCliente) {
		return DAO.modificaProdottoCV(idProdotto, nomeProdotto, descrizioneProdotto, prezzoProdotto, materiaPrima, quantitaMP, idCliente);
	}
	
	public boolean eliminaProdotto(String risposta, String tipo) {
		return DAO.eliminaProdotto(risposta, tipo);
	}
	
	// METODI PER RICERCA NEL DATABASE
	public List<Prodotto> ricercaProdotti(String inputStringa) {
		return DAO.ricercaProdotti(inputStringa);
	}
	
	
}

