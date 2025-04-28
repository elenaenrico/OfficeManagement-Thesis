package officeManagement.MVC;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AggiungiFornitoreView extends JFrame{
	
	private Controller controller;
	private boolean aperta;
	private JPanel panelTitolo = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelId = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelN = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelE = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelT = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelBTN = new JPanel(new FlowLayout(FlowLayout.CENTER));	
	
	public AggiungiFornitoreView() {
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
	
	public void LoadInterface() {
		aperta = true;
		JLabel titolo = new JLabel("<html><h1 color = 'red'>Aggiungi fornitore</h1></html>");
		panelTitolo.add(titolo);
		
		JLabel txtId = new JLabel("Inserisci l'id del fornitore: ");
		JTextField idIn = new JTextField("", 10);
		panelId.add(txtId);
		panelId.add(idIn);
		
		JLabel txtN = new JLabel("Inserisci il nome del fornitore: ");
		JTextField nomeIn = new JTextField("", 20);
		panelN.add(txtN);
		panelN.add(nomeIn);
		
		JLabel txtE = new JLabel("Inserisci l'email del fornitore: ");
		JTextField eIn = new JTextField("", 20);
		JLabel icona = new JLabel();
		panelN.add(txtE);
		panelN.add(eIn);
		panelN.add(icona);
		
		eIn.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				validaEmail();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				validaEmail();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
			
			public void validaEmail() {
				if (!controller.controlloEmail(eIn.getText())) {
					eIn.setBorder(new LineBorder(Color.RED));
					icona.setText("✖");
					icona.setForeground(Color.RED);
				} 
				else {
					icona.setText("✔");
					icona.setForeground(Color.GREEN);
					eIn.setBorder(new LineBorder(Color.GREEN));
				}
			}
		});
		
		JLabel txtT = new JLabel("Inserisci il numero di telefono del fornitore (facoltativo): ");
		JTextField tIn = new JTextField("", 20);
		panelT.add(txtT);
		panelT.add(tIn);
		
		JButton btnAggiungi = new JButton("Aggiungi");
		JButton btnIndietro = new JButton("Indietro");
		panelBTN.add(btnIndietro);
		panelBTN.add(btnAggiungi);
		
		btnIndietro.addActionListener(e -> dispose());
		btnAggiungi.addActionListener(e -> {
			
			try {
				if (controller.controlloEsistenzaFornitore(idIn.getText()))
					JOptionPane.showMessageDialog(this, "Esiste già un fornitore con questo id, non va bene.");
				else if (!icona.getText().equals("✔"))
					JOptionPane.showMessageDialog(this, "Il formato dell'email non è valido");
				else {
					boolean c = controller.aggiungiFornitore(idIn.getText(), nomeIn.getText(), eIn.getText(), tIn.getText());
					if (c == true) {
						JOptionPane.showMessageDialog(this, "Fornitore aggiunto correttamente.");
						controller.aggiornaFornitori(idIn.getText());
						dispose();
					}
				}
			} catch (HeadlessException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
				
		add(panelTitolo);
		add(panelId);
		add(panelN);
		add(panelE);
		add(panelT);
		add(panelBTN);
	}
	
	public void pulisci() {
		for (Component c: panelId.getComponents()) 
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		
		for (Component c: panelN.getComponents()) 
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		
		for (Component c: panelE.getComponents()) 
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		
		for (Component c: panelBTN.getComponents()) 
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		
	}

}
