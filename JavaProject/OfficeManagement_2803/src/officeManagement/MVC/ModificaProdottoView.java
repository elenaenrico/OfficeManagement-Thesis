package officeManagement.MVC;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import officeManagement.model.*;

/*FINIRE LA MODIFICA (NEL DAO, MODEL E CONTROLLER) E SISTEMARE LA MODIFICA NEL CONTO VENDITA*/
public class ModificaProdottoView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean aperta;
	private Controller controller;
	private Prodotto prodottoDaModificare;
	private JPanel panelTitolo = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelId = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelDescrizione = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelPrezzo = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JTextField prezzoE = new JTextField("", 3);
	private JTextField prezzoCent = new JTextField("", 2);
	private JLabel txtPrezzo = new JLabel("Vuoi modificare il prezzo del prodotto? ");
	private JLabel icona = new JLabel();
	private JLabel iconaQ = new JLabel();
	private JComboBox<String> idIn = new JComboBox<>();
	private JTextField idNIn = new JTextField("", 20);
	private JTextField idCIn = new JTextField("", 20);
	private JTextField idDIn = new JTextField("", 50);
	private JTextField idMPIn = new JTextField("", 20);
	private JTextField idQMPIn = new JTextField("", 10);
	private JPanel panelMatP = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelQMatP = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JComboBox<String> matP = new JComboBox<>();
	private String[] opzioni = {"Sì", "No"};
	private JComboBox<String> modificaN = new JComboBox<>(opzioni);
	private JComboBox<String> modificaC = new JComboBox<>(opzioni);
	private JComboBox<String> modificaD = new JComboBox<>(opzioni);
	private JComboBox<String> modificaP = new JComboBox<>(opzioni);
	private JComboBox<String> clienteBox = new JComboBox<>();
	private JButton btnAggiungi = new JButton("Modifica");
	
	public ModificaProdottoView() {
		this.aperta = false;
		this.prodottoDaModificare = null;
		setSize(800,800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(8,1));
		avvisoSuCampoDisabilitato(modificaN, "Prima seleziona un prodotto valido!");
		avvisoSuCampoDisabilitato(modificaD, "Prima seleziona un prodotto valido!");
		avvisoSuCampoDisabilitato(modificaP, "Prima seleziona un prodotto valido!");
	}
	
	public void setId(Prodotto Prodotto) {
		this.prodottoDaModificare = Prodotto;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;	
	}

	public boolean isAperta() {
		return aperta;
	}

	public void LoadInterface() throws SQLException {
		aperta = true;
		JLabel titolo = new JLabel("<html><h1 color = 'red'>Modifica prodotto</h1></html>");
		panelTitolo.add(titolo);
		modificaN.setEnabled(false);
		modificaC.setEnabled(false);
		modificaD.setEnabled(false);
		modificaP.setEnabled(false);
		btnAggiungi.setEnabled(false);
		
		JLabel txtId = new JLabel("Inserisci l'id del prodotto: ");
		panelId.add(txtId);
		JTextField inId = new JTextField("", 20);
		if (prodottoDaModificare != null) {
			inId.setText(prodottoDaModificare.getId());
			inId.setEditable(false);
			if (prodottoDaModificare instanceof ProdottoContoVendita) {
				modificaProdottoContoVendita();
				revalidate();
				repaint();
			}
		}
		else {
			this.caricaProd();
			panelId.add(idIn);
			idIn.addActionListener(e -> {
				String selezione = (String) idIn.getSelectedItem();
				if (selezione != null && selezione.contains("-")) {
					prodottoDaModificare = controller.aggiornaProdotto(selezione.split("-")[0]);
					prodottoDaModificare = controller.aggiornaProdotto(selezione.split("-")[0]);
					if (prodottoDaModificare != null) {
					    modificaN.setEnabled(true);
					    modificaC.setEnabled(true);
					    modificaD.setEnabled(true);
					    modificaP.setEnabled(true);
					    btnAggiungi.setEnabled(true);
					}

					if (prodottoDaModificare instanceof ProdottoContoVendita) {
						try {
							modificaProdottoContoVendita();
							revalidate();
							repaint();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
					controller.aggiornaOpzione(inId, prodottoDaModificare.getId());
					inId.setEditable(false);
				}
			});
		}		
		panelId.add(inId);
		
		
		JLabel idN = new JLabel("Vuoi modificare il nome del prodotto? ");
		modificaN.addActionListener(e -> {
			String selezione = (String) modificaN.getSelectedItem();
			if ("Sì".equals(selezione)) {
				idNIn.setText("");
				idNIn.setEditable(true);
			}
			else if ("No".equals(selezione)) {
				idNIn.setText(prodottoDaModificare.getNome());
				idNIn.setEditable(false);
			}
		});
		
		panelNome.add(idN);
		panelNome.add(modificaN);
		panelNome.add(idNIn);
		
		panelCliente.add(new JLabel("Vuoi modificare il cliente per cui produci il prodotto? "));
		panelCliente.add(modificaC);
		modificaC.addActionListener(e -> {
			panelCliente.add(clienteBox);
			clienteBox.setVisible(false);
			String selezione = (String) modificaC.getSelectedItem();
			if ("Sì".equals(selezione)) {
				idCIn.setText("");
				clienteBox.setVisible(true);
				try {
					caricaClienteBox();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				clienteBox.addActionListener(e1 -> {
					String selezione1 = (String) clienteBox.getSelectedItem();
			        if ("Aggiungi nuovo cliente".equals(selezione1)) {
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
			        controller.aggiornaOpzione(idCIn, selezione1);
			        idCIn.setEditable(false);
		        });
			}
			else if ("No".equals(selezione)) {
				clienteBox.setVisible(false);
				idCIn.setText(prodottoDaModificare.getIdCliente());
				idCIn.setEditable(false);
			}
		});
		panelCliente.add(idCIn);
		
		JLabel idD = new JLabel("Vuoi modificare la descrizione del prodotto?");
		modificaD.addActionListener(e -> {
			String selezione = (String) modificaD.getSelectedItem();
			if ("Sì".equals(selezione)) {
				idDIn.setText("");
				idDIn.setEditable(true);
			}
			else if ("No".equals(selezione)) {
				idDIn.setText(prodottoDaModificare.getDescrizione());
				idDIn.setEditable(false);
			}
		});
		panelDescrizione.add(idD);
		panelDescrizione.add(modificaD);
		panelDescrizione.add(idDIn);
		
		modificaP.addActionListener(e -> {
			String selezione = (String) modificaP.getSelectedItem();
			if ("Sì".equals(selezione)) {
				prezzoE.setText("");
				prezzoE.setEditable(true);
				prezzoCent.setText("");
				prezzoCent.setEditable(true);
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
				}
			else if ("No".equals(selezione)) {
				int parteIntera = (int) prodottoDaModificare.getPrezzo();
				int parteDecimale = (int) Math.round((prodottoDaModificare.getPrezzo() - parteIntera)*100);
				prezzoE.setText(String.valueOf(parteIntera));
				prezzoCent.setText(String.valueOf(parteDecimale));
				prezzoE.setEditable(false);
				prezzoCent.setEditable(false);
			}
		});
		panelPrezzo.add(txtPrezzo);
		panelPrezzo.add(modificaP);
		panelPrezzo.add(prezzoE);
		panelPrezzo.add(new JLabel("."));
		panelPrezzo.add(prezzoCent);
		panelPrezzo.add(new JLabel("€"));
		panelPrezzo.add(icona);
		
		
		
		JButton btnIndietro = new JButton("Annulla");
		btnAggiungi.addActionListener(e -> {
			if (prodottoDaModificare == null)
				JOptionPane.showMessageDialog(this, "Non hai selezionato nessun prodotto da modificare.");
			else if ("✖".equals(icona.getText())) 
				JOptionPane.showMessageDialog(this, "Hai inserito un prezzo non valido");
			else {
				if (prodottoDaModificare instanceof ProdottoContoVendita) {
					if ("✖".equals(iconaQ.getText()))
						JOptionPane.showMessageDialog(this, "Hai inserito una quantità non valida.");
					else {
						boolean c = controller.modificaProdottoCV(inId.getText(), idNIn.getText(), idDIn.getText(), Double.parseDouble(prezzoE.getText() + "." + prezzoCent.getText()), idMPIn.getText(), Double.parseDouble(idQMPIn.getText()), idCIn.getText());
						if (c) {
							JOptionPane.showMessageDialog(this, "Modifica prodotto avvenuta con successo.");
							dispose();
						}
					}
				}
				else {
					boolean c = controller.modificaProdottoCL(inId.getText(), idNIn.getText(), idDIn.getText(), Double.parseDouble(prezzoE.getText() + "." + prezzoCent.getText()), idCIn.getText());
					if (c) {
						JOptionPane.showMessageDialog(this, "Modifica prodotto avvenuta con successo");
						dispose();
					}
				}
			}
		});
		btnIndietro.addActionListener(e1 -> dispose());
		panelBtn.add(btnIndietro);
		panelBtn.add(btnAggiungi);
		
		add(panelTitolo);
		add(panelBtn);
		add(panelId);
		add(panelNome);
		add(panelCliente);
		add(panelDescrizione);
		add(panelPrezzo);
		
	}
	
	private void caricaClienteBox() throws SQLException {
		clienteBox.removeAll();
		for (Cliente c: controller.getAllClienti())
			clienteBox.addItem(c.getId());
		clienteBox.addItem("Aggiungi nuovo cliente");	
	}
	
	public void aggiungiClienteAllaBox(String nuovoCliente) {
		clienteBox.insertItemAt(nuovoCliente, clienteBox.getItemCount() - 1);
		clienteBox.setSelectedItem(nuovoCliente);
	}

	public void pulisci() {
		prodottoDaModificare = null;
		for (Component c: panelId.getComponents()) {
			if (c instanceof JComboBox) 
				((JComboBox) c).setSelectedIndex(0);
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		}
		
		for (Component c: panelNome.getComponents()) {
			if (c instanceof JComboBox)
				((JComboBox) c).setSelectedIndex(-1);
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		}
		
		for (Component c: panelDescrizione.getComponents()) {
			if (c instanceof JComboBox)
				((JComboBox) c).setSelectedIndex(-1);
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		}
		
		for (Component c: panelPrezzo.getComponents()) {
			if (c instanceof JComboBox)
				((JComboBox) c).setSelectedIndex(-1);
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		}
		
		for (Component c: panelCliente.getComponents()) {
			if (c instanceof JComboBox)
				((JComboBox) c).setSelectedIndex(-1);
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		}
		
		for (Component c: panelMatP.getComponents()) {
			if (c instanceof JComboBox)
				((JComboBox) c).setSelectedIndex(-1);
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		}
		
		for (Component c: panelQMatP.getComponents()) {
			if (c instanceof JComboBox)
				((JComboBox) c).setSelectedIndex(-1);
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		}
		modificaN.setEnabled(false);
		modificaC.setEnabled(false);
		modificaD.setEnabled(false);
		modificaP.setEnabled(false);
		btnAggiungi.setEnabled(false);
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
	
	public void caricaProd() throws SQLException {
		idIn.removeAllItems();
		idIn.addItem("");
		for (Prodotto p: controller.getAllProdotti()) 
			idIn.addItem(p.toStringNoTable());
	}
	
	public void caricaMateriaPrima() throws SQLException {
		matP.removeAllItems();
		for (MateriaPrima p: controller.getAllMateriePrime())
			matP.addItem(p.getId() + "-" + p.getNome());
		matP.addItem("Aggiungi nuova materia prima");
	}
	
	public void modificaProdottoContoVendita() throws SQLException {
		ProdottoContoVendita prodotto = (ProdottoContoVendita) prodottoDaModificare;
		String opzioni[] = {"Sì", "No"};
		JComboBox<String> modificaMatP = new JComboBox<>(opzioni);
		idMPIn.setEditable(false);
		modificaMatP.addActionListener(e -> {
			String selezione = (String) modificaMatP.getSelectedItem();
			if ("Sì".equals(selezione)) {
				try {
					panelMatP.add(matP);
					caricaMateriaPrima();
					panelMatP.add(matP);
					matP.addActionListener(e1 -> {
						String selezioneMP = (String) matP.getSelectedItem();
						if ("Aggiungi nuova materia prima".equals(selezioneMP)) {
							try {
								controller.apriAggiungiMateriaPrimaView();
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
						}
						String selezioneID = selezioneMP.split("-")[0];
						controller.aggiornaOpzione(idMPIn, selezioneID);
					});
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				panelMatP.remove(matP);
				idMPIn.setText(prodotto.getIdMateriaPrima());
			}
		});	
		panelMatP.add(new JLabel("Vuoi modificare la materia prima per il prodotto? "));
		panelMatP.add(modificaMatP);
		panelMatP.add(idMPIn);
		
		JComboBox<String> modificaQ = new JComboBox<>(opzioni);
		modificaQ.addActionListener(e1 -> {
			String selezione = (String) modificaQ.getSelectedItem();
			if ("Sì".equals(selezione)) {
				idQMPIn.setEditable(true);
				idQMPIn.setText("");
				idQMPIn.getDocument().addDocumentListener(new DocumentListener() {

					@Override
					public void insertUpdate(DocumentEvent e) {
						validaQuantita(idQMPIn.getText());
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						validaQuantita(idQMPIn.getText());
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
					}
					
				});
			}
			else if ("No".equals(selezione)) {
				idQMPIn.setEditable(false);
				idQMPIn.setText(String.valueOf(prodotto.getQuantitaMateriaPrima()));
			}
		});
		
		panelQMatP.add(new JLabel("Vuoi modificare la quantità di materia prima necessaria? "));
		panelQMatP.add(modificaQ);
		panelQMatP.add(idQMPIn);
		panelQMatP.add(iconaQ);
		
		add(panelMatP);
		add(panelQMatP);
	}
	
	public void aggiungiMateriaAllaBox(String nuovaMateria) {
	       matP.insertItemAt(nuovaMateria, matP.getItemCount() - 1);
	       matP.setSelectedItem(nuovaMateria);
	   }
	
	public void validaQuantita(String q) {
		if (controller.controlloNumeriConVirgola(q)) {
			iconaQ.setText("✔");
			iconaQ.setForeground(Color.GREEN);
			idQMPIn.setBorder(new LineBorder(Color.GRAY));
		}
		else {
			iconaQ.setText("✖");
			iconaQ.setForeground(Color.RED);
			idQMPIn.setBorder(new LineBorder(Color.RED));
		}
	}
	
	private void avvisoSuCampoDisabilitato(JComponent comp, String messaggio) {
	    comp.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            boolean bloccato = false;

	            if (comp instanceof JTextField && !((JTextField) comp).isEditable()) {
	                bloccato = true;
	            }
	            else if (comp instanceof JComboBox && !comp.isEnabled()) {
	                bloccato = true;
	            }

	            if (bloccato) {
	                JOptionPane.showMessageDialog(null, messaggio);
	            }
	        }
	    });
	}
}
