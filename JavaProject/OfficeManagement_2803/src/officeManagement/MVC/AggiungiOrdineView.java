package officeManagement.MVC;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import officeManagement.model.*;


public class AggiungiOrdineView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelTitolo = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelIDC = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelIDO = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelIDP = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelDataSc = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelDataOrd = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelBTN = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelQ = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JComboBox<String> inIdCliente = new JComboBox<>();
	JComboBox<String> inProdotto = new JComboBox<>();
	private Controller controller;
	private boolean aperta = false;
	
	public AggiungiOrdineView() {
		setSize(500,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(8,1));
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
		
	public void LoadInterface() throws SQLException {
		aperta = true;
		JLabel titolo = new JLabel("<html><h1 color = 'red'>Aggiungi Ordine</h1></html>");
		panelTitolo.add(titolo);
		add(panelTitolo);
		
		JLabel txtIdOrdine = new JLabel("Inserisci l'id dell'ordine: ");
		JTextField txtInIdOrdine = new JTextField("", 20);
		panelIDO.add(txtIdOrdine);
		panelIDO.add(txtInIdOrdine);
		
		JLabel txtIdCliente = new JLabel("Inserisci l'id del cliente: ");
		JTextField txtInIdCliente = new JTextField("", 20);
		
		caricaClienti();
		inIdCliente.addActionListener(e ->  {
			String selezione = (String) inIdCliente.getSelectedItem();
	        if ("Aggiungi nuovo cliente".equals(selezione)) {
	        	int risposta = JOptionPane.showConfirmDialog(this, //COMPONENTE IN BASE A CUI VIENE CENTRATA LA FINESTRA 
						   "Sicuro di voler aggiungere un nuovo cliente?", //TESTO CHE VIENE MOSTRATO
				           "Opzione scelta: nuovo cliente", //TITOLO DELLA FINESTRA
				            JOptionPane.YES_NO_OPTION, //BOTTONI CHE COMPAIONO
				            JOptionPane.WARNING_MESSAGE //ICONA MOSTRATA COL MESSAGGIO 
				        );
					if (risposta == JOptionPane.YES_OPTION) {
						controller.apriAggiungiClienteView();
						
					}
					else {
						JOptionPane.showMessageDialog(this, "Ordine annullato.");
						dispose();
					}
	        }
	        controller.aggiornaOpzione(txtInIdCliente, selezione);
        });
		
		
		panelIDC.add(txtIdCliente);
		panelIDC.add(inIdCliente);
		
		JLabel txtIdProdotto = new JLabel("Inserisci l'id del prodotto: ");
		JTextField txtInIdProdotto = new JTextField("", 20);
		caricaProdotto();
		
		inProdotto.addActionListener(e -> {
			String selezione = (String) inProdotto.getSelectedItem();
			if ("Aggiungi nuovo prodotto".equals(selezione)) {
				int risposta = JOptionPane.showConfirmDialog(this, //COMPONENTE IN BASE A CUI VIENE CENTRATA LA FINESTRA 
						   "Sicuro di voler aggiungere un nuovo prodotto?", //TESTO CHE VIENE MOSTRATO
				           "Opzione scelta: nuovo prodotto", //TITOLO DELLA FINESTRA
				            JOptionPane.YES_NO_OPTION, //BOTTONI CHE COMPAIONO
				            JOptionPane.WARNING_MESSAGE //ICONA MOSTRATA COL MESSAGGIO 
				        );
					if (risposta == JOptionPane.YES_OPTION) {
						try {
							controller.apriAggiungiProdottoView();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						JOptionPane.showMessageDialog(this, "Ordine annullato.");
						dispose();
					}
			}		
			controller.aggiornaOpzione(txtInIdProdotto, selezione);
		});
		
		panelIDP.add(txtIdProdotto);
		panelIDP.add(inProdotto);
		//panelIDP.add(txtInIdProdotto);
		
		JLabel txtQuantita = new JLabel("Inserisci la quantità: ");
		JTextField txtInQuantita = new JTextField("", 20);
		panelQ.add(txtQuantita);
		panelQ.add(txtInQuantita);
		
		String[] giorni = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
		String[] mesi = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};
		String[] anni = {"2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035"};
		
			
		JTextField dataOrdine = new JTextField("", 10);
		dataOrdine.setEditable(false);
		JComboBox<String> sceltaGiornoOrdine = new JComboBox<>(giorni);
		JComboBox<String> sceltaMeseOrdine = new JComboBox<>(mesi);
		JComboBox<String> sceltaAnnoOrdine = new JComboBox<>(anni);
		
		panelDataOrd.add(new JLabel("Inserisci la data dell'ordine:"));
		panelDataOrd.add(dataOrdine);
		String[] anno = {""};
		String[] mese = {""};
		String[] giorno = {""};
		
		panelDataOrd.add(sceltaGiornoOrdine);
		panelDataOrd.add(sceltaMeseOrdine);
		panelDataOrd.add(sceltaAnnoOrdine);
				
		sceltaAnnoOrdine.addActionListener(e -> {
		    anno[0] = (String) sceltaAnnoOrdine.getSelectedItem();
		    controller.aggiornaData(dataOrdine, anno[0], mese[0], giorno[0]);
		});

		sceltaMeseOrdine.addActionListener(e -> {
		    mese[0] = (String) sceltaMeseOrdine.getSelectedItem();
		    controller.aggiornaData(dataOrdine, anno[0], mese[0], giorno[0]);
		});

		sceltaGiornoOrdine.addActionListener(e -> {
		    giorno[0] = (String) sceltaGiornoOrdine.getSelectedItem();
		    controller.aggiornaData(dataOrdine, anno[0], mese[0], giorno[0]);
		});
		
		JComboBox<String> sceltaGiornoOrdineS = new JComboBox<>(giorni);
		JComboBox<String> sceltaMeseOrdineS = new JComboBox<>(mesi);
		JComboBox<String> sceltaAnnoOrdineS = new JComboBox<>(anni);
		
		JLabel txtDataScad = new JLabel("Inserisci la data di scadenza: ");
		JTextField txtInDataScadenza = new JTextField("", 10);
		txtInDataScadenza.setEditable(false);
		panelDataSc.add(txtDataScad); 
		panelDataSc.add(txtInDataScadenza);
		String[] annoS = {""};
		String[] meseS = {""};
		String[] giornoS = {""};
		
		panelDataSc.add(sceltaGiornoOrdineS);
		panelDataSc.add(sceltaMeseOrdineS);
		panelDataSc.add(sceltaAnnoOrdineS);
				
		sceltaAnnoOrdineS.addActionListener(e -> {
		    annoS[0] = (String) sceltaAnnoOrdineS.getSelectedItem();
		    controller.aggiornaData(txtInDataScadenza, annoS[0], meseS[0], giornoS[0]);
		});

		sceltaMeseOrdineS.addActionListener(e -> {
		    meseS[0] = (String) sceltaMeseOrdineS.getSelectedItem();
		    controller.aggiornaData(txtInDataScadenza, annoS[0], meseS[0], giornoS[0]);
		});

		sceltaGiornoOrdineS.addActionListener(e -> {
		    giornoS[0] = (String) sceltaGiornoOrdineS.getSelectedItem();
		    controller.aggiornaData(txtInDataScadenza, annoS[0], meseS[0], giornoS[0]);
		});
		
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(e -> {
			String idOrdine = txtInIdOrdine.getText();
			String idCliente = txtInIdCliente.getText();
			String prodotto = txtInIdProdotto.getText();
			String[] campiProdotto = prodotto.split("-");
			String idProdotto = campiProdotto[0];
			String quantita = txtInQuantita.getText();
			String dataO = dataOrdine.getText();
			String dataS = txtInDataScadenza.getText();
			//System.out.println(idOrdine + "-" + idCliente + "-" + idProdotto + "-" + quantita + "-" + dataO + "-" + dataS);
			boolean controlloQuantita = true;
			char[] caratteri = quantita.toCharArray();
			for (Character c: caratteri) {
				if (!Character.isDigit(c))
					controlloQuantita = false;
			}
			try {
				boolean datiValidi = controller.controlloDatiInseriti(idOrdine)
					    && controller.controlloDatiInseriti(idCliente)
					    && controller.controlloDatiInseriti(idProdotto)
					    && controller.controlloDatiInseriti(quantita)
					    && controller.controlloDatiInseriti(dataS)
					    && controller.controlloDatiInseriti(dataO)
					    && controlloQuantita;

				if (!datiValidi) {
					int risposta = JOptionPane.showConfirmDialog(this, 
							"Attenzione, mancano dei dati! Vuoi aggiungerli?", 
							"DatiMancanti", 
							JOptionPane.YES_NO_OPTION, 
							JOptionPane.WARNING_MESSAGE
						);
					if (risposta != JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(this, "Ordine annullato.");
						this.dispose();
					}
				   return;
				}
				else {
					java.sql.Date dataOSQL = java.sql.Date.valueOf(dataO);
					java.sql.Date dataSSQL = java.sql.Date.valueOf(dataS);
					if (controller.controlloEsistenzaOrdine(idOrdine) == true) {
						int risposta = JOptionPane.showConfirmDialog(this,  
								"L'ordine con ID " + idOrdine + " è già presente nello storico. Vuoi modificarlo? ",
								"Ordine esistente", 
								JOptionPane.YES_NO_OPTION, 
								JOptionPane.WARNING_MESSAGE
							);
					if (risposta == JOptionPane.YES_OPTION) {
									
						boolean aO = controller.aggiornaOrdine(idOrdine, idCliente, idProdotto, dataOSQL, dataSSQL, null, Integer.parseInt(quantita));
						if (aO == true) {
							JOptionPane.showMessageDialog(this, "Ordine aggiornato con successo.");
							dispose();
						}
						}
						else {
							JOptionPane.showMessageDialog(this, "Ordine annullato.");
							dispose();
						}
					return;
					}
					
					boolean controlloMagazzinoMatPrima = controller.controlloQuantitaMateriaPrima(idProdotto, Integer.parseInt(quantita));
					if (controlloMagazzinoMatPrima == false) {
						int risposta = JOptionPane.showConfirmDialog(this,
								"Non hai abbastanza materia prima in magazzino per soddisfare questo ordine. Vuoi ordinarla?", 
								"Mancanza materia prima in magazzino",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE
							);
						if (risposta == JOptionPane.YES_OPTION) {
							controller.apriOrdineFornituraView(idProdotto, Integer.parseInt(quantita));
						}
					}
					boolean c = controller.aggiungiOrdine(idOrdine, idCliente, idProdotto, dataOSQL, dataSSQL, Integer.parseInt(quantita));
					if (c == true) {
						 JOptionPane.showMessageDialog(this, "Ordine aggiunto con successo!");
						 this.dispose();
					 }
					 /*else {
						 JOptionPane.showMessageDialog(this, "C'è stato un errore nell'inserimento, prova a controllare che i dati siano giusti!");
					 }*/
				
				}		
			} 
			catch (SQLException e1) {
					e1.printStackTrace();
			}
		});
		
		JButton btnChiudi = new JButton("Indietro");
		btnChiudi.addActionListener(e -> dispose());
		
		panelBTN.add(btnChiudi);
		panelBTN.add(btnAggiungi);
		
		add(panelIDO);
		add(panelIDC);
		add(panelIDP);
		add(panelQ);
		add(panelDataOrd);
		add(panelDataSc);
		add(panelBTN);
		
		revalidate();
		repaint();
		setVisible(true);
	}

	public boolean isAperta() {
		return aperta;
	}

	public void pulisci() {
		for (Component c: panelIDO.getComponents()) {
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		}
		
		for (Component c: panelIDC.getComponents()) {
			if (c instanceof JTextField)
				((JTextField) c).setText("");
			if (c instanceof JComboBox) 
				((JComboBox) c).setSelectedIndex(-1);
		}
		
		for (Component c: panelIDP.getComponents()) {
			if (c instanceof JTextField)
				((JTextField) c).setText("");
			if (c instanceof JComboBox) 
				((JComboBox) c).setSelectedIndex(-1);
		}
		
		for (Component c: panelQ.getComponents()) {
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		}
		
		for (Component c: panelDataOrd.getComponents()) {
			if (c instanceof JTextField)
				((JTextField) c).setText("");
			if (c instanceof JComboBox) 
				((JComboBox) c).setSelectedIndex(-1);
		}
		
		for (Component c: panelDataSc.getComponents()) {
			if (c instanceof JTextField)
				((JTextField) c).setText("");
			if (c instanceof JComboBox) 
				((JComboBox) c).setSelectedIndex(-1);
		}
	}
	
   public void caricaClienti() throws SQLException {
	   inIdCliente.removeAllItems();
        for (Cliente cliente : controller.getAllClienti()) {
        	inIdCliente.addItem(cliente.getNome());
        }
        inIdCliente.addItem("Aggiungi nuovo cliente");
   }
   
   public void caricaProdotto() throws SQLException {
	   inProdotto.removeAllItems();
	   for (Prodotto prodotto: controller.getAllProdotti()) {
		   inProdotto.addItem(prodotto.toStringNoTable());
	   }
	   inProdotto.addItem("Aggiungi nuovo prodotto");
   }
   
   public void aggiungiClienteAllaBox(String nuovoCliente) {
       inIdCliente.insertItemAt(nuovoCliente, inIdCliente.getItemCount() - 1);
       inIdCliente.setSelectedItem(nuovoCliente);
   }

   public void aggiungiProdottoAllaBox(String nuovoProdotto) {
	   inProdotto.insertItemAt(nuovoProdotto, inProdotto.getItemCount() - 1);
       inProdotto.setSelectedItem(nuovoProdotto);
   }

}
