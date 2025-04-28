package officeManagement.MVC;

import java.awt.FlowLayout;
import java.sql.SQLException;

import javax.swing.*;

import officeManagement.model.*;

public class CompletaOrdineView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private boolean aperta;
	private Ordine ordine;
	private JPanel panelTitolo = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelOrdine = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JComboBox<String> boxOrdini = new JComboBox<>();	
	private JTextField txtCliente = new JTextField();
	
	public CompletaOrdineView() {
		aperta = false;
		setSize(500,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void LoadInterface() throws SQLException {
		aperta = true;
		JLabel titolo = new JLabel("<html><h1 color='red'>Completa ordine</p></html>");
		panelTitolo.add(titolo);
		panelCliente.setVisible(false);
		
		JLabel txtO = new JLabel("Seleziona l'ordine da completare: ");
		caricaOrdini();
		panelOrdine.add(txtO);
		panelOrdine.add(boxOrdini);
		add(panelOrdine);
		boxOrdini.addActionListener(e -> {
			String selezione = (String) boxOrdini.getSelectedItem();
			try {
				ordine = controller.aggiornaOrdini(selezione.split(" ")[1]);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		JLabel txtC = new JLabel("Cliente: ");
		
		
		
	}
	
	private void caricaOrdini() throws SQLException {
		boxOrdini.removeAllItems();
		for (Ordine o: controller.getModel().getOrdiniDaCompletare()) 
			boxOrdini.addItem("Ordine " + o.getId() + " di " + o.getQuantita() + " " + o.getId_prodotto() + " (del " + o.getData_ordine() + ")");
	}
}
