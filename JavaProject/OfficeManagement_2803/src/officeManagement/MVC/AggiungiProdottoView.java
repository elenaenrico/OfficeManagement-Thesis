package officeManagement.MVC;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.FlatLightLaf;

import officeManagement.model.Cliente;
import officeManagement.model.MateriaPrima;

//FINIRE LA PARTE SULLE MATERIE PRIME, CREANDO LA VIEW AGGIUNGI MATERIA PRIMA (DA CAPO, HO GIUSTO SOLO SCRITTO IL NOME) E CONTROLLANDO LA QUANTITA'

public class AggiungiProdottoView extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private boolean aperta;
	private JPanel panelTitolo = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelID = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelD = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelPrezzo = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelConto = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelContoV = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JTextField prezzoE = new JTextField("", 3);
	private JTextField prezzoCent = new JTextField("", 2);
	private JLabel txtPrezzo = new JLabel("Inserisci il prezzo del prodotto: ");
	private JLabel icona = new JLabel();
	private JComboBox<String> matPrima = new JComboBox<>();
	private JComboBox<String> clienteBox = new JComboBox<>();
	private JTextField nomMatPrima = new JTextField("", 20);
	private JTextField qMatPrima = new JTextField("", 10);
	private JLabel iconaQ = new JLabel();

	public AggiungiProdottoView() {
		this.aperta = false;
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (Exception ex) {
			System.err.println("Impossibile caricare FlatLaf");
		}
		setSize(500,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(9,1));
	}

	public void setController(Controller controller) {
		this.controller = controller;	
	}
	
	public void LoadInterface() throws SQLException {
		aperta = true;
		JLabel titolo = new JLabel("<html><h1 color = 'red'>Aggiungi prodotto</h1></html>");
		panelTitolo.add(titolo);
		
		JLabel idP = new JLabel("Inserisci l'id del prodotto: ");
		JTextField idPIn = new JTextField("", 10);
		panelID.add(idP);
		panelID.add(idPIn);
		
		JLabel idN = new JLabel("Inserisci il nome del prodotto: ");
		JTextField idNIn = new JTextField("", 20);
		panelNome.add(idN);
		panelNome.add(idNIn);
		
		caricaClienti();
		JTextField clienteIn = new JTextField("", 20);
		clienteBox.addActionListener(e -> {
			String selezione = (String) clienteBox.getSelectedItem();
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
	        controller.aggiornaOpzione(clienteIn, selezione);
        });
		panelCliente.add(new JLabel("Seleziona il cliente per cui produrrai questo prodotto: "));
		panelCliente.add(clienteBox);
		
		JLabel idD = new JLabel("Inserisci una breve descrizione del prodotto (facoltativa): ");
		JTextField idDIn = new JTextField("", 50);
		panelD.add(idD);
		panelD.add(idDIn);
		
		panelPrezzo.add(txtPrezzo);
		panelPrezzo.add(prezzoE);
		panelPrezzo.add(new JLabel("."));
		panelPrezzo.add(prezzoCent);
		panelPrezzo.add(new JLabel("€"));
		panelPrezzo.add(icona);
		
		prezzoE.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				validaPrezzo();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				validaPrezzo();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
			
		});
		
		prezzoCent.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				validaPrezzo();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				validaPrezzo();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
			
		});
		
		String[] opzioni = {"Prodotto per conto Lavoro", "Prodotto per conto Vendita"};
		JComboBox<String> contoIn = new JComboBox<>(opzioni);
		panelConto.add(new JLabel("Come classifichi questo nuovo prodotto? "));
		panelConto.add(contoIn);
		JTextField opzione = new JTextField("", 25);
		JTextField idM = new JTextField("", 10);
		JTextField q = new JTextField("", 10);
		contoIn.addActionListener(e -> {
			String selezione = (String) contoIn.getSelectedItem();
			controller.aggiornaOpzione(opzione, selezione);
			panelContoV.removeAll();
			if (selezione != null && selezione.equals("Prodotto per conto Vendita"))  {
				try {
					this.prodottoContoVendita();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				panelContoV.revalidate();
			    panelContoV.repaint();
			}
		});
		
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(e -> {
			try {
				if (opzione.getText().equals("Prodotto per conto Vendita")) {
					String idMP = (String)idM.getText();
					float qMP = Float.parseFloat(q.getText());
					
					if (controller.controlloEsistenzaProdotto((String) idPIn.getText())) 
						JOptionPane.showMessageDialog(this, "Questo id prodotto esiste già, non va bene.");
					else if (!icona.getText().equals("✔")) 
						JOptionPane.showMessageDialog(this, "Hai inserito un prezzo non valido.");
					else if (iconaQ.getText().equals("✖"))
						JOptionPane.showInternalMessageDialog(this, "Hai inserito una quantità non valida");
					else {
						boolean c = controller.aggiungiProdotto(idPIn.getText(), idNIn.getText(), idDIn.getText(), (String) (prezzoE.getText() + "." + prezzoCent.getText()), opzione.getText(), idMP, qMP, clienteIn.getText());
						if (c == true) {
							JOptionPane.showMessageDialog(this, "Prodotto aggiunto correttamente");
							dispose();
						}
					}
				}
				else {
					if (controller.controlloEsistenzaProdotto((String) idPIn.getText())) {
						int risposta = JOptionPane.showConfirmDialog(this, 
								"Il prodotto con questo id esiste già, vuoi modificarlo?", 
								"Prodotto con id esistente", 
								JOptionPane.YES_NO_OPTION, 
								JOptionPane.WARNING_MESSAGE);
						if (risposta == JOptionPane.YES_OPTION) 
							controller.apriModificaProdottoView(idPIn.getText());
						else {
							JOptionPane.showMessageDialog(this,"Ordine annullato.");
							dispose();
						}
					}
					else if (!icona.getText().equals("✔")) 
						JOptionPane.showMessageDialog(this, "Hai inserito un prezzo non valido.");
					else {
						boolean c = controller.aggiungiProdotto(idPIn.getText(), idNIn.getText(), idDIn.getText(), (String) (prezzoE.getText() + "." + prezzoCent.getText()), opzione.getText(), null, 0.0, clienteIn.getText());
						if (c == true) {
							JOptionPane.showMessageDialog(this, "Prodotto aggiunto correttamente");
							controller.aggiornaProdotti(idPIn.getText());
							dispose();
						}
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		JButton btnIndietro = new JButton("Annulla");
		btnIndietro.addActionListener(e1 -> dispose());
		panelBtn.add(btnIndietro);
		panelBtn.add(btnAggiungi);
		
		add(panelTitolo);
		add(panelBtn);
		add(panelID);
		add(panelNome);
		add(panelCliente);
		add(panelD);
		add(panelPrezzo);
		add(panelConto);
	}

	public boolean isAperta() {
		return aperta;
	}

	public void pulisci() {
		for(Component c: panelID.getComponents()) {
			if (c instanceof JTextField) {
				((JTextField) c).setText("");
			}
		}
		
		for(Component c: panelNome.getComponents()) {
			if (c instanceof JTextField) {
				((JTextField) c).setText("");
			}
		}
		
		for (Component c: panelCliente.getComponents()) {
			if (c instanceof JTextField)
				((JTextField) c).setText("");
			if (c instanceof JComboBox)
				((JComboBox) c).setSelectedIndex(-1);
		}
		for(Component c: panelD.getComponents()) {
			if (c instanceof JTextField) {
				((JTextField) c).setText("");
			}
		}
		
		for(Component c: panelPrezzo.getComponents()) {
			if (c instanceof JTextField) {
				((JTextField) c).setText("");
			}
		}
		
		for(Component c: panelConto.getComponents()) {
			if (c instanceof JTextField) {
				((JTextField) c).setText("");
			}
			
			if (c instanceof JComboBox) {
				((JComboBox) c).setSelectedIndex(-1);
			}
		}
		
		for(Component c: panelContoV.getComponents()) {
			if (c instanceof JTextField) {
				((JTextField) c).setText("");
			}
			if (c instanceof JComboBox) 
				((JComboBox) c).setSelectedIndex(-1);
		}
	}	 

	public void validaPrezzo() {
		String intera = prezzoE.getText();
		String decimali = prezzoCent.getText();

		boolean parteInteraValida = controller.controlloNumeri(intera);
		boolean parteDecimaleValida = controller.controlloNumeri(decimali) && decimali.length() <= 2;

		if (parteInteraValida && parteDecimaleValida) {
			prezzoE.setBorder(new LineBorder(Color.GRAY));
			prezzoCent.setBorder(new LineBorder(Color.GRAY));
			icona.setText("✔");
			icona.setForeground(Color.GREEN);
		} else {
			if (!parteInteraValida)
				prezzoE.setBorder(new LineBorder(Color.RED));
			else
				prezzoE.setBorder(new LineBorder(Color.GRAY));
			
			if (!parteDecimaleValida)
				prezzoCent.setBorder(new LineBorder(Color.RED));
			else
				prezzoCent.setBorder(new LineBorder(Color.GRAY));

			icona.setText("✖");
			icona.setForeground(Color.RED);
		}
	}
	
	public void validaQuantita(String q) {
		if (controller.controlloNumeriConVirgola(q)) {
			iconaQ.setText("✔");
			iconaQ.setForeground(Color.GREEN);
			qMatPrima.setBorder(new LineBorder(Color.GRAY));
		}
		else {
			iconaQ.setText("✖");
			iconaQ.setForeground(Color.RED);
			qMatPrima.setBorder(new LineBorder(Color.RED));
		}
	}
	
	public void prodottoContoVendita() throws SQLException {
	
	caricaMatPrime();
	panelContoV.add(new JLabel("Inserisci id materia prima: "));
	panelContoV.add(matPrima);
	matPrima.addActionListener(e -> {
		String selezione = (String) matPrima.getSelectedItem();
		if ("".equals(selezione)) {
			JOptionPane.showMessageDialog(this, "Seleziona una materia prima valida!");
		}
		if ("Aggiungi nuova materia prima".equals(selezione)) {
			int risposta = JOptionPane.showConfirmDialog(this, //COMPONENTE IN BASE A CUI VIENE CENTRATA LA FINESTRA 
					   "Sicuro di voler aggiungere un nuova nuova materia prima?", //TESTO CHE VIENE MOSTRATO
			           "Opzione scelta: nuova materia prima", //TITOLO DELLA FINESTRA
			            JOptionPane.YES_NO_OPTION, //BOTTONI CHE COMPAIONO
			            JOptionPane.WARNING_MESSAGE //ICONA MOSTRATA COL MESSAGGIO 
			        );
				if (risposta == JOptionPane.YES_OPTION) {
					try {
						controller.apriAggiungiMateriaPrimaView();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else {
					JOptionPane.showMessageDialog(this, "Aggiunta prodotto annullata.");
					dispose();
				}
		}
		controller.aggiornaOpzione(nomMatPrima, selezione);
	});
	panelContoV.add(new JLabel("Inserisci quantità materia prima: "));
	panelContoV.add(qMatPrima);
	panelContoV.add(iconaQ);
	qMatPrima.getDocument().addDocumentListener(new DocumentListener() {

		@Override
		public void insertUpdate(DocumentEvent e) {
			validaQuantita(qMatPrima.getText());
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			validaQuantita(qMatPrima.getText());
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
		}
		
	});

	add(panelContoV);
		
	}
	
	public void caricaMatPrime() throws SQLException {
		   matPrima.removeAllItems();
		   matPrima.addItem("");
	        for (MateriaPrima mp : controller.getAllMateriePrime()) {
	        	matPrima.addItem(mp.getNome());
	        }
	        matPrima.addItem("Aggiungi nuova materia prima");
	   }
	
	public void aggiungiMateriaAllaBox(String nuovaMateria) {
	       matPrima.insertItemAt(nuovaMateria, matPrima.getItemCount() - 1);
	       matPrima.setSelectedItem(nuovaMateria);
	   }
	
	public void caricaClienti() throws SQLException {
		clienteBox.removeAllItems();
		for (Cliente c: controller.getAllClienti()) 
			clienteBox.addItem(c.getId());
		clienteBox.addItem("Aggiungi nuovo cliente");
	}
	
	public void aggiungiClienteAllaBox(String nuovoCliente) {
		clienteBox.insertItemAt(nuovoCliente, clienteBox.getItemCount() - 1);
		clienteBox.setSelectedItem(nuovoCliente);
	}
	
}

