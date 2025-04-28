package officeManagement.MVC;

import officeManagement.model.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

// DA FINIRE "ORDINE FORNITURA"

public class Controller {

	private Timer timer;
	private final long UPDATE_PERIOD = 10000;
	private Model model;
	private View view;
	private OrdiniView ordiniView;
	private ProdottoView prodottoView;
	private CompletaOrdineView completaOrdineView;
	private AggiungiOrdineView aggiungiOrdineView;
	private AggiungiProdottoView aggiungiProdottoView;
	private AggiungiClienteView aggiungiClienteView;
	private AggiungiMateriaPrimaView aggiungiMateriaPrimaView;
	private AggiungiFornitoreView aggiungiFornitoreView;
	private ModificaProdottoView modificaProdottoView;
	private OrdineFornituraView ordineFornituraView;
	private List<Ordine> ordiniDaCompl = new ArrayList<Ordine>();
	private List<Prodotto> prodotti = new ArrayList<Prodotto>();
	
	public Controller(Model model, View view, OrdiniView ordiniView, CompletaOrdineView completaOrdineView, 
			AggiungiOrdineView aggiungiOrdineView, AggiungiProdottoView aggiungiProdottoView, ProdottoView prodottoView,
			AggiungiClienteView aggiungiClienteView, AggiungiMateriaPrimaView aggiungiMateriaPrimaView, 
			AggiungiFornitoreView aggiungiFornitoreView, ModificaProdottoView modificaProdottoView, OrdineFornituraView ordineFornituraView) {
		this.model = model;
		this.view = view;
		this.ordiniView = ordiniView;
		this.completaOrdineView = completaOrdineView;
		this.aggiungiOrdineView = aggiungiOrdineView;
		this.aggiungiProdottoView = aggiungiProdottoView;
		this.prodottoView = prodottoView;
		this.aggiungiClienteView = aggiungiClienteView;
		this.aggiungiMateriaPrimaView = aggiungiMateriaPrimaView;
		this.aggiungiFornitoreView = aggiungiFornitoreView;
		this.modificaProdottoView = modificaProdottoView;
		this.ordineFornituraView = ordineFornituraView;
	}

	public Model getModel() {
		return model;
	}

	public View getView() {
		return view;
	}
	
	//METODI PER AGGIORNAMENTO AUTOMATICO
	public void startPeriodicUpdates() {
	    if (timer == null) {
	        timer = new Timer();
	        timer.scheduleAtFixedRate(new TimerTask() {
	            @Override
	            public void run() {
	                try {
						aggiornaDati();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }

	        }, 0, UPDATE_PERIOD); // AGGIORNA OGNI 10 S
	    }
	}
	
	public void stopPeriodicUpdates() {
	    if (timer != null) {
	        timer.cancel();
	        timer = null;
	    }
	}
	
	private void aggiornaDati() throws SQLException {
		ordiniDaCompl = model.getOrdiniDaCompletare();
	    prodotti = model.getAllProdotti();
	    
	    // ESEGUI AGGIORNAMENTI
	    ordiniView.updateOrdini();  
	    prodottoView.updateProdotti();  
	}
	
	//METODI PER SETTARE RIEPILOGHI PARTENDO DAL DATABASE
	
	public String setTxtRiepilogoOrdini() throws SQLException {
		String s = "<html><p>&nbsp;&nbsp;&nbsp;ORDINI DA COMPLETARE: ";
		ordiniDaCompl = model.getOrdiniDaCompletare();
		s = s + ordiniDaCompl.size() + "</p>"; 
		s = s + "<table cellpadding='8' cellspacing='25'>" + "<tr color = 'red'><th>Data Ordine</th><th>Data Scadenza</th><th>Ordine n.</th><th>Id cliente</th><th>Prodotto</th><th>Quantit&agrave</th></tr>";
		for (Ordine o: ordiniDaCompl) {
			s = s + "<br>" + o.toString();
		}
		s += "</table></html>";
		return s;
	}
	
	public String setTxtRiepilogoProdotti() throws SQLException {
		String s = "<html><p>&nbsp;&nbsp;&nbsp;PRODOTTI IN LISTINO: ";
		prodotti = model.getAllProdotti();
		s = s + prodotti.size() + "</p>"; 
		
		
		s += "<table cellpadding='10' cellspacing='25' ><tr color = 'red'><th>Id prodotto</th><th>Nome</th><th>Cliente</th><th>Descrizione</th><th>Prezzo</th></tr>";
		for (Prodotto p: prodotti) {
			s += p.toString();
		}
		
		s += "</table></html>";
		return s;
	}
	
	public String setTxtRiepilogoProdottiConRicerca(String inputString) throws SQLException {
		String s = "";
		List<Prodotto> prodottiRicerca = model.ricercaProdotti(inputString);
		if (prodottiRicerca.size() == 0) 
			s += "Nessun prodotto trovato";
		else {
			s = s + "<html><p>&nbsp;&nbsp;&nbsp;PRODOTTI TROVATI: " + prodottiRicerca.size() + "</p>";
			s += "<table cellpadding='10' cellspacing='25' ><tr color = 'red'><th>Id prodotto</th><th>Nome</th><th>Descrizione</th><th>Prezzo</th></tr>";
			for (Prodotto p: prodottiRicerca) {
				s += p.toString();
			}
			
			s += "</table></html>";
		}
		
		return s;
	}
	
	public String setTxtRiepilogoProdottiConFiltri(List<String> prodotti) {
		String s = "";
		if (prodotti.size() == 0) 
			s += "Nessun prodotto trovato";
		else {
			s = s + "<html><p>&nbsp;&nbsp;&nbsp;PRODOTTI TROVATI: " + prodotti.size() + "</p>";
			s += "<table cellpadding='10' cellspacing='25' ><tr color = 'red'><th>Id prodotto</th><th>Nome</th><th>Descrizione</th><th>Prezzo</th></tr>";
			for (String s1: prodotti) {
				s += s1;
			}
			s += "</table></html>";
		}
		return s;
	}
	
	//METODI PER APERTURA FINESTRE:
	
	public void openOrdiniView() throws SQLException {
		if (!ordiniView.isAperta())
			ordiniView.LoadInterface();
		ordiniView.setVisible(true);
	}
	
	public void openCompletaOrdineView() throws SQLException {
		completaOrdineView.LoadInterface();
		completaOrdineView.setVisible(true);
	}

	public void openAggiungiOrdineView() throws SQLException {
		if (!aggiungiOrdineView.isAperta())
			aggiungiOrdineView.LoadInterface();
		aggiungiOrdineView.pulisci();
		aggiungiOrdineView.setVisible(true);
	}
	
	public void apriAggiungiProdottoView() throws SQLException {
		if (!aggiungiProdottoView.isAperta())
			aggiungiProdottoView.LoadInterface();
		aggiungiProdottoView.pulisci();
		aggiungiProdottoView.setVisible(true);
	}
	
	public void openProdottiView() throws SQLException {
		if (!prodottoView.isAperta())
			prodottoView.LoadInterface();
		prodottoView.setVisible(true);
	}
	
	public void apriAggiungiClienteView() {
		if (!aggiungiClienteView.isAperta())
			aggiungiClienteView.LoadInterface();
		aggiungiClienteView.pulisci();
		aggiungiClienteView.setVisible(true);
	}
	
	public void apriAggiungiMateriaPrimaView() throws SQLException {
		if (!aggiungiMateriaPrimaView.isAperta())
			aggiungiMateriaPrimaView.LoadInterface();
		aggiungiMateriaPrimaView.pulisci();
		aggiungiMateriaPrimaView.setVisible(true);
	}
	
	public void aggiungiFornitoreView() {
		if (!aggiungiFornitoreView.isAperta()) 
			aggiungiFornitoreView.LoadInterface();
		aggiungiFornitoreView.pulisci();
		aggiungiFornitoreView.setVisible(true);
	}
	
	public void apriModificaProdottoView(String idProdotto) throws SQLException {
		Prodotto prod = null;
		if (idProdotto != null) 
			prod = this.aggiornaProdotto(idProdotto);
		if (modificaProdottoView.isAperta())
			modificaProdottoView.pulisci();
		modificaProdottoView.setId(prod);
		if (!modificaProdottoView.isAperta()) 
			modificaProdottoView.LoadInterface();
		modificaProdottoView.setVisible(true);
	}
	
	public void apriOrdineFornituraView(String idProdotto, int quantitaProdRichiesta) throws SQLException {
		Prodotto p = this.aggiornaProdotto(idProdotto);
		if (p instanceof ProdottoContoVendita) {
			ProdottoContoVendita pr = (ProdottoContoVendita) p;
			MateriaPrima mp = this.aggiornaMateriaPrima(pr.getIdMateriaPrima());
			
		}
	}

	//METODI PER AGGIUNTA DATI NEL DATABASE:
	
	public boolean aggiungiOrdine(String idOrdine, String idCliente, String idProdotto, Date oggi, Date scadenza, int quantita) throws SQLException {
		 return (model.aggiungiOrdine(idOrdine, idCliente, idProdotto, quantita, oggi, scadenza));
		 
	}
	
	public boolean aggiungiProdotto(String id, String nome, String descrizione, String prezzo, String tipoProd, String idMateriaPrima, double d, String idCliente) throws SQLException {
		try {
			if (descrizione.equals("")) 
				return (model.aggiungiProdotto(id, nome, null, Double.parseDouble(prezzo), tipoProd, idMateriaPrima, d, idCliente));
			else
				return (model.aggiungiProdotto(id, nome, descrizione, Double.parseDouble(prezzo), tipoProd, idMateriaPrima, d, idCliente));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(aggiungiProdottoView, "Attenzione! Il prezzo inserito non è valido");
			aggiungiProdottoView.dispose();
			return false;
		}
	}
	
	public boolean aggiungiCliente(String idC, String nomeC, String emailC, String telC, String indirizzoC) {
		if (telC.equals(""))
			return model.aggiungiCliente(idC, nomeC, emailC, null, indirizzoC);
		else
			return model.aggiungiCliente(idC, nomeC, emailC, telC, indirizzoC);
	}
	
	public boolean aggiungiMateriaPrima(String id, String nomeForn, String nome, String prezzo) throws SQLException {
		String idF = null;
		for(Fornitore f: model.getAllFornitori())
			if (f.getNome().equals(nomeForn))
				idF = f.getId();
		return model.aggiungiMateriaPrima(id, idF, nome, Double.parseDouble(prezzo));
	}
	
	public boolean aggiungiFornitore(String idF, String nomeF, String emailF, String telF) {
		if (telF.equals(""))
			return model.aggiungiFornitore(idF, nomeF, emailF, null);
		else
			return model.aggiungiFornitore(idF, nomeF, emailF, telF);
	}
	
	//METODI PER RICAVARE DATI DAL DATABASE: 
	
	public List<Cliente> getAllClienti() throws SQLException {
		return model.getAllClienti();
	}
	
	public List<Prodotto> getAllProdotti() throws SQLException {
		return model.getAllProdotti();
	}
	
	public List<MateriaPrima> getAllMateriePrime() throws SQLException {
		return model.getAllMateriePrime();
	}
	
	public List<Fornitore> getAllFornitori() throws SQLException {
		return model.getAllFornitori();
	}
	
	//METODI PER AGGIORNARE DATI DEL DATABASE: 
	
	public boolean aggiornaOrdine(String idOrdine, String idCliente, String idProdotto, Date dataOSQL, Date dataSSQL, Date dataC,
			int quantita) {
		return (model.aggiornaOrdine(idOrdine, idCliente, idProdotto, dataOSQL, dataSSQL, dataC, quantita));
	}
	
	public boolean modificaProdottoCL(String idProdotto, String nomeProdotto, String descrizioneProdotto, double prezzoProdotto, String idCliente) {
		return model.modificaProdottoCL(idProdotto, nomeProdotto, descrizioneProdotto, prezzoProdotto, idCliente);
	}
	
	public boolean modificaProdottoCV(String idProdotto, String nomeProdotto, String descrizioneProdotto, double prezzoProdotto, String materiaPrima, double quantitaMP, String idCliente) {
		return model.modificaProdottoCV(idProdotto, nomeProdotto, descrizioneProdotto, prezzoProdotto, materiaPrima, quantitaMP, idCliente);
	}
	
	public void eliminaProdotto() {
		Prodotto prod = null;
		String tipo = null;
		String risposta = JOptionPane.showInputDialog(prodottoView, "Indica l'id del prodotto che vuoi eliminare: ");
		Prodotto p = aggiornaProdotto(risposta);
		if (p instanceof ProdottoContoVendita)
			tipo = "prodotto_conto_vendita";
		else if (p instanceof ProdottoContoLavoro)
			tipo = "prodotto_conto_lavoro";
		
		if (prod == null && risposta != null)
			JOptionPane.showMessageDialog(prodottoView, "L'id che hai inserito non appartiene a nessun prodotto");
		else {
			boolean c = model.eliminaProdotto(risposta, tipo);
			if (c == true) 
				JOptionPane.showMessageDialog(prodottoView, "Prodotto eliminato con successo");
		}
			
	}
	
	//METODI PER CONTROLLARE GLI INPUT: 
	
	public boolean controlloEsistenzaOrdine(String id) throws SQLException {
		List<Ordine> ordini = model.getAllOrdini();
		for (Ordine o: ordini) 
			if (o.getId().equals(id))
				return true;
		return false;
	}
	
	public boolean controlloEsistenzaCliente(String idC) throws SQLException {
		for (Cliente c: model.getAllClienti()) 
			if (c.getId().equals(idC)) 
				return true;
		return false;
	}
	
	public boolean controlloEsistenzaProdotto(String idP) throws SQLException {
		for (Prodotto p: model.getAllProdotti())
			if (p.getId().equals(idP))
				return true;
		return false;
	}
	
	public boolean controlloEsistenzaMateriaPrima(String idMP) throws SQLException {
		for (MateriaPrima mp: model.getAllMateriePrime())
			if (mp.getId().equals(idMP))
				return true;
		return false;
	}
	
	public boolean controlloEsistenzaFornitore(String idF) throws SQLException {
		for (Fornitore f: model.getAllFornitori())
			if (f.getId().equals(idF))
				return true;
		return false;
	}
	
	public boolean controlloEmail(String email) {
		return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
	}
	
	public boolean controlloNumeri(String numero) {
		return numero.matches("[0-9]+");
	}
	
	public boolean controlloNumeriConVirgola(String numero) {
		return numero.matches("[0-9]+([.,][0-9]{1,3})?");
	}
	
	public boolean controlloDatiInseriti(String dato) {
		if (dato.equals("") || dato == null)
			return false;
		return true;
	}
	
	//METODI PER AGGIORNARE ELEMENTI NELLE VIEW:
	
	public int convertiMese(String mese) {
	    switch (mese.toLowerCase()) {
	        case "gennaio": return 1;
	        case "febbraio": return 2;
	        case "marzo": return 3;
	        case "aprile": return 4;
	        case "maggio": return 5;
	        case "giugno": return 6;
	        case "luglio": return 7;
	        case "agosto": return 8;
	        case "settembre": return 9;
	        case "ottobre": return 10;
	        case "novembre": return 11;
	        case "dicembre": return 12;
	        default: return 1; 
	    }
	}
	
	public void aggiornaData(JTextField campoData, String anno, String mese, String giorno) {
	    try {
	    	if (anno == null || mese == null || giorno == null || 
	                anno.isEmpty() || mese.isEmpty() || giorno.isEmpty()) {
	                return; // ASPETTO CHE TUTTI I CAMPI DELLA DATA SIANO SELEZIONATI
	            }
	        int a = Integer.parseInt(anno);
	        int m = convertiMese(mese);
	        int g = Integer.parseInt(giorno);
	        LocalDate data = LocalDate.of(a, m, g);
	        campoData.setText(data.toString());
	    } catch (Exception ex) {
	        campoData.setText("Data non valida");
	    }
	}
	
	public void aggiornaOpzione(JTextField opzione, String selezione) {
		opzione.setText(selezione);		
	}

	public void aggiornaClienti(String nuovoCliente) {
        aggiungiOrdineView.aggiungiClienteAllaBox(nuovoCliente);
	}
	
	public void aggiornaProdotti(String nuovoProdotto) {
        aggiungiOrdineView.aggiungiProdottoAllaBox(nuovoProdotto);
	}
	
	public void aggiornaMateriePrime(String nuovaMatPrima) {
		aggiungiProdottoView.aggiungiMateriaAllaBox(nuovaMatPrima);
	}
	
	public void aggiornaFornitori(String nuovoFornitore) {
		aggiungiMateriaPrimaView.aggiungiFornitoreAllaBox(nuovoFornitore);
	}
	
	public Prodotto aggiornaProdotto(String stringa) {
		for (Prodotto p: prodotti)
			if (p.getId().equals(stringa))
				return p;
		return null;
	}
	
	public MateriaPrima aggiornaMateriaPrima(String stringa) throws SQLException {
		for (MateriaPrima mp: model.getAllMateriePrime()) 
			if (mp.getId().equals(stringa))
				return mp;
		return null;
	}
	
	public Ordine aggiornaOrdini(String stringa) throws SQLException {
		for (Ordine o: model.getAllOrdini())
			if (o.getId().equals(stringa))
				return o;
		return null;
	}
	
	public List<String> ricercaProdottiFiltri(List<String> prodottiTrovati, String filtro) {
		for (Prodotto p: prodotti) { 
			if (p.getIdCliente().equals(filtro))
				 if (!prodottiTrovati.contains(p.toString())) {
		                prodottiTrovati.add(p.toString());
		            }
		}
		return prodottiTrovati;
	}
	
	public List<String> ricercaProdottiFiltriP(List<String> prodottiTrovati, String filtro) {
		for (Prodotto p: prodotti) { 
			if (p.getId().equals(filtro))
				 if (!prodottiTrovati.contains(p.toString())) {
		                prodottiTrovati.add(p.toString());
		            }
		}
		return prodottiTrovati;
	}
	
	// METODI PER CONTROLLO MAGAZZINO: 
	public boolean controlloQuantitaMateriaPrima(String idP, int quantita) throws SQLException {
		Prodotto p = this.aggiornaProdotto(idP);
		if (p != null && p instanceof ProdottoContoVendita) {
			ProdottoContoVendita pr = (ProdottoContoVendita) p;
			MateriaPrima mp = this.aggiornaMateriaPrima(pr.getIdMateriaPrima());
			if (mp.getQuantitaMagazzino() < pr.getQuantitaMateriaPrima() * quantita)
				return false;
		}
		return true;
	}

}
