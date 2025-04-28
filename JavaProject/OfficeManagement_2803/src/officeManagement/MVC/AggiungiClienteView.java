package officeManagement.MVC;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AggiungiClienteView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private boolean aperta;
	private JPanel panelTitolo = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelId = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelEmail = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelTel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelIndirizzo = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelBTN = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	public AggiungiClienteView() {
		this.aperta = false;
		setSize(500,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(7,1));
	}

	public void setController(Controller controller) {
		this.controller = controller;	
	}

	public boolean isAperta() {
		return aperta;
	}

	public void LoadInterface() {
		aperta = true;
		// TODO Auto-generated method stub
		JLabel titolo = new JLabel("<html><h1 color = 'red'>Aggiungi cliente</h1></html>");
		panelTitolo.add(titolo);
		
		JLabel id = new JLabel("Inserisci l'id: ");
		JTextField idIn = new JTextField("", 20);
		panelId.add(id);
		panelId.add(idIn);
		
		JLabel nome = new JLabel("Inserisci il nome: ");
		JTextField nomeIn = new JTextField("", 20);
		panelNome.add(nome);
		panelNome.add(nomeIn);
		
		JLabel email = new JLabel("Inserisci l'email: ");
		JTextField emailIn = new JTextField("", 20);
		JLabel iconLabel = new JLabel();
		panelEmail.add(email);
		panelEmail.add(emailIn);
		panelEmail.add(iconLabel);
		//DocumentListener: SI ATTIVA A OGNI MODIFICA DEL CAMPO, SIA DA TASTIERA CHE DA MOUSE CHE DA CODICE (CON setText())
		emailIn.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		        validateEmail();
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		        validateEmail();
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {
		        //NON SERVE PER JTextField
		    }

		    private void validateEmail() {
		        String email = emailIn.getText();
		        if (controller.controlloEmail(email)) {
		            emailIn.setBorder(new LineBorder(Color.GRAY));
		            iconLabel.setText("✔");
		            iconLabel.setForeground(Color.GREEN);
		        } else {
		            emailIn.setBorder(new LineBorder(Color.RED));
		            iconLabel.setText("✖");
		            iconLabel.setForeground(Color.RED);
		        }
		    }
		});
		
		JLabel tel = new JLabel("Inserisci il numero di telefono (facoltativo): ");
		JTextField telIn = new JTextField("", 20);
		panelTel.add(tel);
		panelTel.add(telIn);
		
		JLabel indirizzo = new JLabel("Inserisci l'indirizzo");
		JTextField indIn = new JTextField("", 40);
		panelIndirizzo.add(indirizzo);
		panelIndirizzo.add(indIn);
		
		JButton aggiungi = new JButton("Aggiungi");
		JButton indietro = new JButton("Annulla");
		panelBTN.add(indietro);
		panelBTN.add(aggiungi);
		
		indietro.addActionListener(e -> dispose());
		aggiungi.addActionListener(e -> {
			String idC = (String) idIn.getText();
			String nomeC = (String) nomeIn.getText();
			String emailC = (String) emailIn.getText(); 
			String telC = (String) telIn.getText();
			
			try {
				boolean controlloId = controller.controlloEsistenzaCliente(idC);
				if (controlloId == true) 
					JOptionPane.showMessageDialog(this, "Questo id cliente esiste già, non va bene.");
				else if (iconLabel.getText() != "✔") 
					JOptionPane.showMessageDialog(this, "L'email che hai inserito non è valida.");
				else {
					boolean controllo = controller.aggiungiCliente(idC, nomeC, emailC, telC, indIn.getText());
					if (controllo) {
						JOptionPane.showMessageDialog(this, "Cliente aggiunto correttamente");
						controller.aggiornaClienti(nomeC);
						dispose();
					}
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		add(panelTitolo);
		add(panelBTN);
		add(panelId);
		add(panelNome);
		add(panelEmail);
		add(panelTel);
		add(panelIndirizzo);
	}
	
	public void pulisci() {
		for(Component c: panelId.getComponents()) 
			if (c instanceof JTextField) 
				((JTextField) c).setText("");
		
		for(Component c: panelNome.getComponents()) 
			if (c instanceof JTextField) 
				((JTextField) c).setText("");
		
		for(Component c: panelEmail.getComponents()) 
			if (c instanceof JTextField) 
				((JTextField) c).setText("");
		
		for(Component c: panelTel.getComponents()) 
			if (c instanceof JTextField) 
				((JTextField) c).setText("");
	}

}
