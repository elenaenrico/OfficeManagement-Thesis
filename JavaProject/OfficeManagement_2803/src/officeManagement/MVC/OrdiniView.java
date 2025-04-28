package officeManagement.MVC;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;

import officeManagement.model.Ordine;

public class OrdiniView extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel txtRiepilogoOrdini = new JLabel("");
	private Controller controller = null;
	private JButton btnAggiungiOrdine = new JButton("Aggiungi ordine");
	private JButton btnCompletaOrdine = new JButton("Completa ordine");
	private JPanel panelBTN = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	private JPanel panelTXT = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private boolean aperta = false;
	
	public OrdiniView() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		setSize(screenSize.width, screenSize.height);
		setLocation(0, 0);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());		
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public void setTxtRiepilogoOrdini() throws SQLException {
		if (controller != null) {
			txtRiepilogoOrdini.setText(this.controller.setTxtRiepilogoOrdini());
		}
		else
			System.out.println("Errore, controller non impostato correttamente");
	}
	
	public boolean isAperta() {
		return aperta;
	}

	public void LoadInterface() throws SQLException {
		//JLabel titolo = new JLabel("Ordini");
		aperta = true;
		panelBTN.add(btnAggiungiOrdine);
		panelBTN.add(btnCompletaOrdine);
		add(panelBTN, BorderLayout.NORTH);
		
		btnAggiungiOrdine.addActionListener(e -> {
			try {
				controller.openAggiungiOrdineView();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		btnCompletaOrdine.addActionListener(e -> {
			try {
				controller.openCompletaOrdineView();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		this.setTxtRiepilogoOrdini();
		panelTXT.add(txtRiepilogoOrdini);
		add(panelTXT, BorderLayout.CENTER);
		setVisible(true);
	}

	public void updateOrdini() throws SQLException {
		this.setTxtRiepilogoOrdini();
		
	}	
	
}
