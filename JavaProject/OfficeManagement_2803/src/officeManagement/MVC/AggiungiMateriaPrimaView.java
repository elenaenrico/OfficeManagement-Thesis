package officeManagement.MVC;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import officeManagement.model.Cliente;
import officeManagement.model.Fornitore;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class AggiungiMateriaPrimaView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean aperta;
	private Controller controller;
	private JPanel panelTitolo = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelId = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelN = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelF = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelP = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelBTN = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JComboBox<String> fornitori = new JComboBox<>();
	private JTextField prezzoE = new JTextField("", 3);
	private JTextField prezzoCent = new JTextField("", 2);
	private JLabel icona = new JLabel();
	
	public AggiungiMateriaPrimaView() {
		this.aperta = false;
		setSize(500,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(6,1));
	}

	public void setController(Controller controller) {
		this.controller = controller;	
	}

	public boolean isAperta() {
		return aperta;
	}

	public void LoadInterface() throws SQLException {
		aperta = true;
		JLabel titolo = new JLabel("<html><h1 color='red'>Aggiungi materia prima</h1></html>");
		panelTitolo.add(titolo);
		
		JLabel txtId = new JLabel("Inserisci l'id della materia prima: ");
		JTextField idIn = new JTextField("", 10);
		panelId.add(txtId);
		panelId.add(idIn);
		
		JLabel txtN = new JLabel("Inserisci il nome della materia prima: ");
		JTextField nomeIn = new JTextField("", 20);
		panelN.add(txtN);
		panelN.add(nomeIn);
		
		JLabel txtF = new JLabel("Seleziona il fornitore da cui vuoi ordinarla: ");
		JTextField fornitoreIn = new JTextField("", 20);
		//fornitori = new JComboBox<>();
		caricaFornitori();
		panelF.add(txtF);
		panelF.add(fornitori);
		
		fornitori.addActionListener(e -> {
			String selezione = (String) fornitori.getSelectedItem();
			if ("Aggiungi nuovo fornitore".equals(selezione)) {
				int risposta = JOptionPane.showConfirmDialog(this, 
						"Sicuro di voler aggiungere un nuovo fornitore?", 
						"Opzione scelta: nuovo fornitore", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.WARNING_MESSAGE);
				
				if (risposta == JOptionPane.YES_OPTION)
					controller.aggiungiFornitoreView();
				else {
					JOptionPane.showMessageDialog(this, "Aggiunta materia prima annullata");
					dispose();
				}
			}
			controller.aggiornaOpzione(fornitoreIn, selezione);
		});
		
		panelP.add(new JLabel("Inserisci il prezzo al kg: "));
		panelP.add(prezzoE);
		panelP.add(new JLabel("."));
		panelP.add(prezzoCent);
		panelP.add(new JLabel("€"));
		panelP.add(icona);
		
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
		
		JButton aggiungi = new JButton("Aggiungi");
		JButton indietro = new JButton("Indietro");
		panelBTN.add(indietro);
		panelBTN.add(aggiungi);
		
		indietro.addActionListener(e -> dispose());
		aggiungi.addActionListener(e -> {
			try {
				if (controller.controlloEsistenzaMateriaPrima(idIn.getText()))
					JOptionPane.showMessageDialog(this, "È già presente una materia prima con questo id, non va bene");
				else if (!icona.getText().equals("✔"))
					JOptionPane.showMessageDialog(this, "Hai inserito un prezzo non valido.");
				else {
					boolean c = controller.aggiungiMateriaPrima(idIn.getText(), fornitoreIn.getText(), nomeIn.getText(), (String)(prezzoE.getText() + "." + prezzoCent.getText()));
					if (c == true) {
						JOptionPane.showMessageDialog(this, "Materia prima aggiunta correttamente.");
						controller.aggiornaMateriePrime(idIn.getText());
						dispose();
					}
				}
			} catch (HeadlessException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
		add(panelTitolo);
		add(panelBTN);
		add(panelId);
		add(panelN);
		add(panelF);
		add(panelP);
	}
	
	public void caricaFornitori() throws SQLException {
		   fornitori.removeAllItems();
	        for (Fornitore fornitore : controller.getAllFornitori()) {
	        	fornitori.addItem(fornitore.getNome());
	        }
	        fornitori.addItem("Aggiungi nuovo fornitore");
	   }
	
	public void pulisci() {
		for (Component c: panelId.getComponents()) 
			if (c instanceof JTextField) 
				((JTextComponent) c).setText("");
		
		for (Component c: panelN.getComponents())
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		
		for (Component c: panelF.getComponents()) {
			if (c instanceof JTextField)
				((JTextField) c).setText("");
			if (c instanceof JComboBox)
				((JComboBox) c).setSelectedIndex(-1);
		}
		
		for (Component c: panelP.getComponents()) 
			if (c instanceof JTextField)
				((JTextField) c).setText("");
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
	
	public void aggiungiFornitoreAllaBox(String nuovoFornitore) {
	       fornitori.insertItemAt(nuovoFornitore, fornitori.getItemCount() - 1);
	       fornitori.setSelectedItem(nuovoFornitore);
	   }
	
}
